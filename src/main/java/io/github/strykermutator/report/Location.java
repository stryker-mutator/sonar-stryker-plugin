package io.github.strykermutator.report;

import lombok.Value;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.TextRange;

@Value
public class Location {
    private Position start;
    private Position end;

    public TextRange getRange(InputFile file) {
        return file.newRange(getStart().getTextPointer(), getEnd().getTextPointer());
    }
}
