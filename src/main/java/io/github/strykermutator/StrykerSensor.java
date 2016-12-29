package io.github.strykermutator;

import lombok.extern.slf4j.Slf4j;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rule.RuleKey;
import org.sonar.api.rules.ActiveRule;

import static io.github.strykermutator.StrykerConstants.RULE_REPOSITORY_KEY;
import static io.github.strykermutator.StrykerConstants.SURVIVED_MUTANT_RULE_KEY;

/**
 *
 */
@Slf4j
public class StrykerSensor implements Sensor {
    private final FileSystem fileSystem;
    private final RulesProfile rulesProfile;

    public StrykerSensor(FileSystem fileSystem, RulesProfile rulesProfile) {
        this.fileSystem = fileSystem;
        this.rulesProfile = rulesProfile;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Stryker Sensor");
    }

    @Override
    public void execute(SensorContext context) {
        //check if rule is active
        ActiveRule activeRule = rulesProfile.getActiveRule(RULE_REPOSITORY_KEY, SURVIVED_MUTANT_RULE_KEY);

        if (activeRule != null) {
            InputFile file = locateSourceFile("src\\main\\java\\io\\github\\strykermutator\\StrykerPlugin.java");

            NewIssue issue = context.newIssue();

            NewIssueLocation location = issue.newLocation()
                .on(file)
                .at(file.selectLine(1))
                .message("learn2program");

            issue.at(location);
            issue.forRule(RuleKey.of(RULE_REPOSITORY_KEY, SURVIVED_MUTANT_RULE_KEY));
            issue.save();
        } else {
            log.warn("Rule {} is not active, cannot create issues.", SURVIVED_MUTANT_RULE_KEY);
        }
    }

    private InputFile locateSourceFile(String sourceFileRelativePath) {
        FilePredicate filePredicate = fileSystem
            .predicates()
            .and(fileSystem.predicates().hasType(InputFile.Type.MAIN),
                fileSystem.predicates().matchesPathPattern("**/" + sourceFileRelativePath));
        return fileSystem.inputFile(filePredicate);
    }
}
