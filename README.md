# Social Media Data Collectors
For the Food Sentiment Observatory.
This repository contains all software packages that pull data from social networks and open data from the Food Standards Agency.

## Installation
The dependencies are the twitter4j-core and twitter4j-stream package. These will automatically be pulled by Gradle.

To compile jar run:

`./gradlew build`

This creates a jar in build/libs.

## Running
To run the jar file the config file needs to be passed as an argument. Don't forget to fill out the Authentication infromtation in the config file.
You can also pass a ratings xml file as an argument to have a list of restaurants.

To run the jar file:
`java -jar build/libs/foobs.jar <config.xml> <ratings.xml>`
