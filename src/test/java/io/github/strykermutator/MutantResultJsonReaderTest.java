package io.github.strykermutator;

import io.github.strykermutator.report.MutantResult;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MutantResultJsonReaderTest {

    private String REPORT_JSON = "[{\"sourceFilePath\":\"C:\\\\z\\\\github\\\\stryker-mutator\\\\stryker\\\\src\\\\ConfigReader.js\",\"mutatorName\":\"BlockStatement\",\"status\":0,\"replacement\":\"{\\n}\",\"location\":{\"start\":{\"line\":19,\"column\":12},\"end\":{\"line\":21,\"column\":9}},\"range\":[677,724],\"testsRan\":[],\"originalLines\":\"        try {\\r\\n            configModule(config);\\r\\n        }\",\"mutatedLines\":\"        try {\\n}\"},{\"sourceFilePath\":\"C:\\\\z\\\\github\\\\stryker-mutator\\\\stryker\\\\src\\\\ConfigReader.js\",\"mutatorName\":\"BlockStatement\",\"status\":1,\"replacement\":\"{\\n}\",\"location\":{\"start\":{\"line\":22,\"column\":18},\"end\":{\"line\":25,\"column\":9}},\"range\":[744,840],\"testsRan\":[],\"originalLines\":\"        catch (e) {\\r\\n            log.fatal('Error in config file!\\\\n', e);\\r\\n            process.exit(1);\\r\\n        }\",\"mutatedLines\":\"        catch (e) {\\n}\"}]";


    @Test
    public void read_withValidReport_shouldRead(){
        List<MutantResult> result = new MutantResultJsonReader().readMutants(REPORT_JSON);
        assertThat(result).hasSize(2);
    }
}
