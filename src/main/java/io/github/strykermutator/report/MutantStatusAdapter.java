package io.github.strykermutator.report;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class MutantStatusAdapter extends TypeAdapter<MutantStatus> {
    public void write(JsonWriter out, MutantStatus mutantStatus) throws IOException {
        out.value(mutantStatus.getValue());
    }

    public MutantStatus read(JsonReader in) throws IOException {
        int value = in.nextInt();
        for (MutantStatus mutantStatus : MutantStatus.values()) {
            if (mutantStatus.getValue() == value){
                return mutantStatus;
            }
        }
        throw new JsonParseException(String.format("Cannot convert %s to a MutantStatus", value));
    }
}
