# Social Media Data Collectors
For the Food Sentiment Observatory.
This repository contains all software packages that pull data from social networks and open data from the Food Standards Agency.

## Installation
The dependencies are the twitter4j-core, twitter4j-stream, mssql-jdbc, hibernate and javax persistence package. These will automatically be pulled by Gradle.

To compile jar run:

`./gradlew build`

This creates a jar in build/libs.

## Running
To run the jar file the config file needs to be passed as an argument. Don't forget to fill out the Authentication infromtation in the config file.

To run the jar file:
`java -jar build/libs/foobs.jar <config.xml>`
