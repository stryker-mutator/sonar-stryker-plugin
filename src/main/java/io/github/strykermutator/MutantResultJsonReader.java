package io.github.strykermutator;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.github.strykermutator.report.MutantResult;

import java.util.List;

class MutantResultJsonReader {

    private Gson gson = new Gson();

    List<MutantResult> readMutants(String reportContent) {
        return gson.fromJson(reportContent, new TypeToken<List<MutantResult>>() {
        }.getType());
    }
}

