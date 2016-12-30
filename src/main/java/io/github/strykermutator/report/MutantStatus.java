package io.github.strykermutator.report;

import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;

@JsonAdapter(MutantStatusAdapter.class)
@Getter
public enum MutantStatus {
    NO_COVERAGE(0, "no coverage"),
    KILLED(1, "killed"),
    SURVIVED(2, "survived"),
    TIMED_OUT(3, "timed out"),
    ERROR(4, "error");

    private int value;
    private String stringRepresentation;

    MutantStatus(int value, String stringRepresentation) {
        this.value = value;
        this.stringRepresentation = stringRepresentation;
    }

    @Override
    public String toString() {
        return stringRepresentation;
    }
}
