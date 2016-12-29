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

import org.sonar.api.server.rule.RulesDefinition;

import static io.github.strykermutator.StrykerConstants.*;


public class StrykerRulesDefinition implements RulesDefinition {

    public void define(Context context) {
        NewRepository repository = context
            .createRepository(RULE_REPOSITORY_KEY, JAVASCRIPT)
            .setName(REPOSITORY_NAME);

        repository.createRule(SURVIVED_MUTANT_RULE_KEY)
            .setHtmlDescription("<p>A mutant survived.</p>")
            .setName("Survived mutant");

    /*repository.createRule(INSUFFICIENT_MUTATION_COVERAGE_RULE_KEY)
      .setHtmlDescription("An issue is created on a file as soon as the mutation coverage on this file is less than the required threshold. It gives the number of mutations to be covered in order to reach the required threshold.")
      .setName("Insufficient mutation coverage")
      .createParam(COVERAGE_RATIO_PARAM)
      .setDefaultValue("65")
      .setDescription("The minimum required mutation coverage ratio");*/

        repository.done();
    }
}
