# Social Media Data Collectors
For the Food Sentiment Observatory.
This repository contains all software packages that pull data from social networks and open data from the Food Standards Agency.

## Installation
The dependencies are the twitter4j-core, twitter4j-stream, mssql-jdbc, hibernate and javax persistence package. These will automatically be pulled by Gradle.

To compile jar run:

`./gradlew build`

This creates a jar in build/libs.

## Running
To run the jar file the config file needs to be passed as an argument. Don't forget to fill out the Authentication information in the config file.

To run the jar file:
`java -jar build/libs/foobs.jar <config.xml>`

To run from Eclipse:
* Open the project in Eclipse
* Find Main.java 
* Select Run As Java Application 

**Note:** If it's the first run of a series of consecutive runs, run as normally and then follow the instructions bellow. If it's a one-off run, run as normally.

## Consecutive runs 
After the first run, a file with cashed results of that search will be created, it contains the IDs of the 
first tweets that have been pulled for each search definition. Open the config.xml file and in each search 
tag, create a `FirstTweetIDsPreviousSearch` tag. 
Within this tag create as many `ID` elements as searches have been done with that search definition.

Example:

```
Search definition Scotland - general food hygiene discourse had four separate searches 

(i.e the keyword list has been split in four when connecting to the twitter API because 

of its limitation of characters for each query). This means that the search definition will 

need four ID elements. Each element will contain the value of the first tweet for that search.
```

After those steps have been done, run the script as normal. Repeat the same process before every run.