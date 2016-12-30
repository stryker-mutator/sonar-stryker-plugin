package io.github.strykermutator.report;

import lombok.Value;
import org.sonar.api.batch.fs.TextPointer;
import org.sonar.api.batch.fs.internal.DefaultTextPointer;

@Value
public class Position {
    private int line;
    private int column;

    public TextPointer getTextPointer() {
        return new DefaultTextPointer(line, column);
    }
}
