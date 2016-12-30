/*
 * Sonar Pitest Plugin
 * Copyright (C) 2009-2016 Alexandre Victoor
 * alexvictoor@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package io.github.strykermutator;

import org.sonar.api.rule.Severity;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.rule.RulesDefinition;

import static io.github.strykermutator.StrykerConstants.*;


public class StrykerRulesDefinition implements RulesDefinition {

    public static final String STRYKER_LOGO_HTML = "<p><img src=\"data:image/png;base64,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\" alt=\"Stryker logo\"></p>";

    public void define(Context context) {
        NewRepository repository = context
                .createRepository(RULE_REPOSITORY_KEY, JAVASCRIPT)
                .setName(REPOSITORY_NAME);

        repository.createRule(SURVIVED_MUTANT_RULE_KEY)
                .setName("Mutant survived")
                .setSeverity(Severity.MINOR)
                .addTags("mutant")
                .setType(RuleType.CODE_SMELL)
                .setHtmlDescription("<p>A mutant survived after running the tests. " +
                        "The source code could be mutated, thus introducing bugs, without any tests failing. " +
                        "For more info, see <a target=\"_blank\" href=\"https://stryker-mutator.github.io\">stryker-mutator.github.io</a></p>" +
                        STRYKER_LOGO_HTML);

        repository.createRule(NO_COVERAGE_MUTANT_RULE_KEY)
                .setName("Mutant not covered")
                .setSeverity(Severity.MINOR)
                .addTags("mutant")
                .setType(RuleType.CODE_SMELL)
                .setHtmlDescription("<p>A mutant was not covered by any of the tests. " +
                        "The source code could be mutated, thus introducing bugs. " +
                        "For more info, see <a target=\"_blank\" href=\"https://stryker-mutator.github.io\">stryker-mutator.github.io</a></p>" +
                        STRYKER_LOGO_HTML);

        repository.done();
    }
}
