package io.github.strykermutator.report;

import lombok.Value;

import java.util.List;

@Value
public class MutantResult {
    private String sourceFilePath;
    private String mutatorName;
    private MutantStatus status;
    private String replacement;
    private String originalLines;
    private String mutatedLines;
    private List<String> testsRan;
    private Location location;
    private List<Integer> range;
}
