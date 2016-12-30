package io.github.strykermutator;

import org.sonar.api.Plugin;

public class StrykerPlugin implements Plugin {
    @Override
    public void define(Context context) {
        context.addExtensions(
                StrykerRulesDefinition.class,
                StrykerSensor.class);
    }
}
