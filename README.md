# CI-LOOK : a project CI information holder

*This project is currently work in progress.*

CI-LOOK is a basic stand-alone repository providing data about your projects and on every build, test, deployment, analysis you want to handle and share. It is currently built in Java.

All features are managed through a simple REST API, intended to be used from your existing CI / build tools, and can be queried for quick access on any important data. CI-LOOK includes a fixed data model for project definition, and process some basic data analysis to always manage only the most important and the up-to-date details on your project and everything around it.

The API can be used from any CURL like tool, from your CI tools using standard "REST call" plugins, or from dedicated / specialized integration plugins.

A badges generator is also included, allowing you to add in your project description page some badges like the one you can have on most Github project, whatever is your build / CI tool, or even if your tools are all used from a private network.

**Here some use cases** :
 * Generate a project CI overview for a "big screen CI pannel" with project last builds, tests results, deployment status ...
 * Have a centralized access version installed on every environment for all of your project
 * Share a badge with last test result status, deployement results, build status ...

# Usage

## Datamodel

TODO 

The managed datamodel is :
 * Project information : basic data about a project. Identity is managed from code and version. All other values are optional
 * Associated project details : team, repositories ... (based on basic Maven POM)
 * Tool information : associated to project. Type is identified from name and / or vendorName. More than 15 tools types are currently supported
 * Result information : any result of build, deploy, test ... Associated to tool and project. A payload with any kind of json data is supported
 * Other to come 
 
Other remarks :
 * Specified Versions must follow semver (for now)
 * Uses a "data aggregation" process : data can be specified directly or through any service as an associated item. For example, you can create a project from dedicated service, and use its code + version when pushing associated build result, or you can simply push result with a code + version + any other project data in associated project data, which create immediately any missing project.
   

## API

### Project - Add project data
put /services/projects 
body : Project record 

### Project - Get project groups
get /services/projects/groups  
Project grouped by code / version

### Project - Get projects versions
get /services/projects/{projectCodeName}  
All versions for a project code

### Build - Add a build result
put /services/results/build  
body : recordBuild 

### Build - Add a deploy result
put /services/results/deploy  
recordDeploy 

### Build - Add a test result
put /services/results/test  
recordTest 

### Build - Add a deploy result on uri specified project
put /services/results/{projectCodeName}/{projectVersion}/deploy  
recordDeploy 

### Build - Add a build result on uri specified project
put /services/results/{projectCodeName}/{projectVersion}/build  
recordBuild 

### Build - Add a test result on uri specified project
put /services/results/{projectCodeName}/{projectVersion}/test  
recordTest 

### Build - Get all results for an uri specified project
get /services/results/{projectCodeName}/{projectVersion}/all  
projectResults 

### Build - Get all build results for an uri specified project
get /services/results/{projectCodeName}/{projectVersion}/build  
projectBuilds 

### Build - Get all test results for an uri specified project
get /services/results/{projectCodeName}/{projectVersion}/test  
projectTests 

### Build - Get all deploy results for an uri specified project
get /services/results/{projectCodeName}/{projectVersion}/deploy  
projectDeploys 

### Tool - Get tool groups
get /services/tools/groups  
Grouped by tool type 

### Tool - Get tool instances for a type
get /services/tools/{anyType} 
All instances for a specified type

## Badges

TODO 

### Specified badges
Badges with pre-specified query

### Custom badges
Badges with a DSL 

## UI 

TODO

## Configuration options

TODO

# Installation

## Standalone

    java -jar ci-look-services-0.1.0.jar

## Docker

TODO

# Building

Standard Maven project. Java 8

    mvn package
    
# License

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)