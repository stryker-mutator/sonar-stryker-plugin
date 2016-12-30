package io.github.strykermutator;

import io.github.strykermutator.report.MutantResult;
import io.github.strykermutator.report.MutantStatus;
import lombok.extern.slf4j.Slf4j;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.batch.sensor.issue.NewIssue;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.config.Settings;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.rule.RuleKey;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static io.github.strykermutator.StrykerConstants.*;

/**
 *
 */
@Slf4j
public class StrykerSensor implements Sensor {
    private final FileSystem fileSystem;
    private final RulesProfile rulesProfile;
    private final Settings settings;

    public StrykerSensor(Settings settings, FileSystem fileSystem, RulesProfile rulesProfile) {
        this.fileSystem = fileSystem;
        this.rulesProfile = rulesProfile;
        this.settings = settings;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Stryker Mutation testing Sensor");
    }

    private boolean isRuleActive(String ruleKey) {
        return rulesProfile.getActiveRule(RULE_REPOSITORY_KEY, ruleKey) != null;
    }

    @Override
    public void execute(SensorContext context) {
        //check if rule is active
        List<String> rules = Arrays.asList(SURVIVED_MUTANT_RULE_KEY, NO_COVERAGE_MUTANT_RULE_KEY);
        if (rules.stream().anyMatch(this::isRuleActive)) {
            try {
                StrykerEventsDirectory strykerEvents = new StrykerEventsDirectory(settings, fileSystem);
                MutantResultJsonReader reader = new MutantResultJsonReader();
                Optional<String> allMutantTestedEventFileContent = strykerEvents.readOnAllMutantsTestedFile();
                if (allMutantTestedEventFileContent.isPresent()) {
                    List<MutantResult> mutantResults = reader.readMutants(allMutantTestedEventFileContent.get());
                    createIssues(mutantResults, context);
                } else {
                    log.warn("Could not find stryker report, not reporting issues.");
                }
            } catch (IOException e) {
                log.error("Could not read from Stryker event file.", e);
            } catch (RuntimeException runTimeEx) {
                log.error("Something went wrong.", runTimeEx);
            }
        } else {
            log.info("Rules {} were not active, cannot create issues.", rules);
        }
    }

    private void createIssues(List<MutantResult> mutantResults, SensorContext context) throws IOException {
        log.info("Processing {} mutant(s).", mutantResults.size());
        createIssuesForMutants(mutantResults, context, MutantStatus.SURVIVED, SURVIVED_MUTANT_RULE_KEY);
        createIssuesForMutants(mutantResults, context, MutantStatus.NO_COVERAGE, NO_COVERAGE_MUTANT_RULE_KEY);
    }

    private void createIssuesForMutants(List<MutantResult> mutantResults, SensorContext context, MutantStatus targetStatus, String ruleKey) throws IOException {
        if (isRuleActive(ruleKey)) {
            int count = 0;
            for (MutantResult mutantResult : mutantResults) {
                if (mutantResult.getStatus() == targetStatus) {
                    count++;
                    InputFile file = locateSourceFile(mutantResult.getSourceFilePath());

                    NewIssue issue = context.newIssue();
                    NewIssueLocation location = issue.newLocation()
                            .on(file)
                            .at(mutantResult.getLocation().getRange(file))
                            .message(formatIssueMessage(mutantResult));
                    issue.at(location);
                    issue.forRule(RuleKey.of(RULE_REPOSITORY_KEY, ruleKey));
                    issue.save();
                }
            }
            log.info("Reported {} issue(s) as {}.", count, targetStatus);
        } else {
            log.info("Skip reporting {} mutant(s), because rule {} is inactive", targetStatus, ruleKey);
        }
    }

    private String formatIssueMessage(MutantResult mutantResult) {
        return String.format("[%s]: %s",
                mutantResult.getMutatorName(),
                mutantResult.getReplacement()
                        .replace("\r\n", "").replace("\n", ""));
    }

    private InputFile locateSourceFile(String sourceFilePath) throws IOException {
        String relativeSourceFilePath = makeRelativePath(sourceFilePath, fileSystem.baseDir());
        FilePredicate filePredicate = fileSystem.predicates().matchesPathPattern("**/" + relativeSourceFilePath);
        InputFile inputFile = fileSystem.inputFile(filePredicate);
        if (inputFile == null) {
            log.warn("Could not find input file {} in {}", relativeSourceFilePath, fileSystem.baseDir().getAbsolutePath());
        }
        return inputFile;
    }

    private String makeRelativePath(String sourceFilePath, File baseDir) throws IOException {
        File sourceFile = new File(sourceFilePath);
        if (sourceFile.getCanonicalPath().startsWith(baseDir.getCanonicalPath())) {
            sourceFilePath = sourceFile.getCanonicalPath().substring(baseDir.getCanonicalPath().length() + 1);
        }
        return sourceFilePath;
    }
}
