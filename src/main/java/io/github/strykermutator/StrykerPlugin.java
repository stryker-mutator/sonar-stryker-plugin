package io.github.strykermutator;

import lombok.extern.log4j.Log4j;
import org.sonar.api.Plugin;

public class StrykerPlugin implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtensions(StrykerRulesDefinition.class, StrykerSensor.class);
    }
}
