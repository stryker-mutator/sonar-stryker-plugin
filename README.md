This repository contains a sonar plugin to report on Stryker, the JavaScript mutation testing framework.

## Getting started

As of now the sonar-stryker-plugin is not distributed via any artifact repository. 
However, it can be installed manually:

### Install plugin in sonar qube

1. Make sure you have git and maven installed locally. Make sure you have a running sonar qube server (or installed locally).
2. Clone this repository
    ```bash
    git clone git@github.com:stryker-mutator/sonar-stryker-plugin.git
    ```
3. Package the sonar plugin
    ```bash
    cd sonar-stryker-plugin
    mvn package
    ```
4. Copy the `target/stryker-sonar-plugin.jar` to the downloads folder of your sonar qube instance (for example: `/sonarqube-6.2/extensions/downloads`)
5. Restart sonar qube
6. Log on to the sonar qube web interface, go to 'rules'. Two new rules should be present under language "JavaScript". Enable these rules for the profile of choice:
    * Mutant not covered
    * Mutant survived

### Run stryker

Next, setup Stryker in your project. See [stryker-mutator.github.io](https://stryker-mutator.github.io). Make sure to enable the `'event-recorder'` reporter. 
This will record all reporter events and write them to disk (`reports/mutation/events` by default). 
Make sure that works before proceeding.

### Report survived mutants

Run sonar in your projects folder. This can be done in a number of ways:

1. Using the [sonar-scanner](http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) 
2. Using the [sonar-maven-plugin](http://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner+for+Maven)
3. (Other sonar plugins not further explained here)

Make sure you configure the `sonar.sources` correctly. This should point to the root directory of your source files.

For example (using the `sonar-scanner`):

```
# sonar-project.properties
sonar.sources=src
```

Or (using the `sonar-maven-plugin`)

```xml
<!-- pom.xml -->
<properties>
  <sonar.sources>src</sonar.sources>
</properties>
```
