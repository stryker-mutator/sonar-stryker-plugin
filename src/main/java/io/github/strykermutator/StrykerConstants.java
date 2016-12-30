package io.github.strykermutator;

public final class StrykerConstants {
    public static final String RULE_REPOSITORY_KEY = "stryker-mutator";
    public static final String REPOSITORY_NAME = "JS mutation testing Stryker";
    public static final String SURVIVED_MUTANT_RULE_KEY = "stryker.mutant.survived";
    public static final String NO_COVERAGE_MUTANT_RULE_KEY = "stryker.mutant.no-coverage";
    public static final String JAVASCRIPT = "js";
    public static final String REPORT_DIRECTORY_KEY = "sonar.stryker.report.dir";
    public static final String DEFAULT_REPORT_DIRECTORY = "reports/mutation/events";

    private StrykerConstants() {

    }
}
