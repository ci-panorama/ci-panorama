# CI-LOOK : a project CI information holder

*This project is currently work in progress.*

## TL;DR :
 * A tool to install in your environment
 * Merge data from various build / CI tools on your projects *whatever are your tools*
 * Provides Rest services on these datas
 * Provides many badges generated from these datas *whatever are your tools*
 * Includes a CLI, an UI, a REST API. No security (Work in progress ...)

## Examples of Badges :
 * Last build of a project : ![Build](docs/build-success.png?raw=true "Build") or ![Build](docs/build-failed.png?raw=true "Build")
 * Versions of a project : ![Specified version](docs/version-num.png?raw=true "Specified version"), ![Pending (unstable) version](docs/version-pending.png?raw=true "Pending version") or ![Released version](docs/version-released.png?raw=true "Released version")
 * **TODO : more to come, this project is currently work in progress**

## What it CI-LOOK ?
CI-LOOK is a basic stand-alone repository providing data about your projects and on every build, test, deployment, analysis you want to handle and share. It is currently built in Java.

All features are managed through a simple REST API, intended to be used from your existing CI / build tools, and can be queried for quick access on any important data. CI-LOOK includes a fixed data model for project definition, and process some basic data analysis to always manage only the most important and the up-to-date details on your project and everything around it.

The API can be used from any CURL like tool, from your CI tools using standard "REST call" plugins, or from dedicated / specialized integration plugins.

A badges generator is also included, allowing you to add in your project description page some badges like the one you can have on most Github project, whatever is your build / CI tool, or even if your tools are all used from a private network.

## Here some use cases :
 * Generate a project CI overview for a "big screen CI pannel" with project last builds, tests results, deployment status ...
 * Have a centralized access version installed on every environment for all of your project
 * Share a badge with last test result status, deployment results, build status ...

## Why a new tool ? With all the CI stuff I already need to use ...
Yep, to build nicely your project you may already have various different tools, some installed locally, if you manage your own environment, some cloud-based... Each of these tools may have needed various config, management ... You may have needed a lot of time to install everything ...

So, OK, CI-LOOK is supposed to be added to your CI stack, but ...

### It cover something missing in various CI environment.
I created CI-LOOK because I wanted to add fun badges to our projects README, but as we are using a private CI environment, it is very difficult to have something similar to a Github based project. And for each tool, you have to integrate the specific badges for all of your needs. 
With CI-LOOK, all badges came from one location only, on a application you can install in your own environment.

In the same time, I wanted to provide a centralised "mvn site" repository for our java projects : a server where the generated sites are located and easily acceded. But it seems that no one do that anymore, and there is no basic solution for that. I needed only basic test results (because we don't have Jenkins anymore, you have to manage yourself the test result agregate), a quick overview on each project teams, maybe a PMD analysis result. Actually, CI-LOOK can manage these datas for you : the teams details, the tests results, the analysis results are some of the various information handle by CI-LOOK. And because the aggregation model is able to "complete" any existing project information with any new data it process, the sources for these datas can be various : from your onw mvn instance, to a build server. 

And because the integration is done through simple rest services, data can be read / pushed from almost every existing or futur tools : just add Rest call, or use dedicated plugin, and that's it

### It is limited to one aspect of the CI, nothing more
CI-LOOK is not a build tool, nor a wiki or a SCM. It's only a manager of some data useful for providing quick overview on your CI process. It will never try to do more that.

The data model is optimized for these needs only, and even if some more kind of data may be added in further releases, the datas from the first v0 is already enough for most of the cases. 

### It open your imagination for your CI overview
You have seen some example of big screen with an overview on all the last build, test fealures, with the picture of the developer who have broken the build in big ? And you dream to add that in your small team, but have no time to code every data integration you need ?

Here, that's it, you have now already all the data management. You just need to install CI-LOOK, push data on it from your tools, and create your own UI as you wish. And for this UI, you can directly use the badges (some are not "standard" badges ...) and have most of the information you want in few minutes.

### It's easy to install. 
Find a small CPU, a few 100Mb of memories, maybe a db (maybe : you can use it without any db), launch it, that's it. If you use Docker you will have nothing to add to configuration.
 
### It will evolve

**The expected improvements to come are :**
 * The project is currently *work in progress*. The missing "core features" for now are : Badges on test, deploy, more build scope, manage Team / repository data, processing of custom payload in Result data, improved spring config, clean database management, Dockerfile
 * Create basic integration plugin to CI-LOOK for some standard tools. I can work on a maven plugin, a Jenkins plugin, and a Drone.io plugin. Every hep is welcome for these task or for other plugins
 * Add more badges. Badges are svg templates, and are very easy to add to the CI-LOOK core. A DSL will be added later to help create own customised badges from any required data
 * Add customization features on the UI and on the badge generation. The idea is to give you the ability to handle the "payload" data from Result and use it in badge generation. Some payload models will be already provided (for example, to count the number of failed test, the covertura coverage ...)
 * An Oauth2 authentication feature when you want to manage CI-LOOK in a public domain.
 * The technical *solution* may evolve also : Built with SpringBoot, CI-LOOK may migrate to another solution, possibly GO. 
 * Add GET-URI based update for some data, to allow basic webhook support (for example for the "release" result, which can be managed from Github or Bitbucket once a merge is done on master branch)
 
**These evolution objectives will try to avoid to include :**
 * A plugin system. You already handle enought plugins, CI-LOOK will handle a customization feature for UI and badges, but that's all. More features will add more complexity and it's not the idea here.
 * An advanced security system. The managed data are not critical, and CI-LOOK is mostly conceved for private environment. So, except the authentication system, no right, profile, or group management will be added


# Usage

## Datamodel

TODO 

### Content
The managed datamodel is :
 * Project information : basic data about a project. Identity is managed from code and version. All other values are optional
 * Associated project details : team, repositories ... (based on basic Maven POM)
 * Tool information : associated to project. Type is identified from name and / or vendorName. More than 15 tools types are currently supported
 * Result information : any result of build, deploy, test ... Associated to tool and project. A payload with any kind of json data is supported
 * Other to come 
 
Other remarks :
 * Specified Versions must follow semver (for now)
 * Uses a "data aggregation" process : data can be specified directly or through any service as an associated item. For example, you can create a project from dedicated service, and use its code + version when pushing associated build result, or you can simply push result with a code + version + any other project data in associated project data, which create immediately any missing project.
   
### Supported and identified tools
The tools bellow are identified automatically from provided values (tool name and / or vendor name)

**Production tools :** 
 * Maven
 * Gradle
 * Ant (without ivy)
 * Ant + Ivy 
 * Make
 * Buildr
 * Sbt

**CI tools :**
 * Bamboo
 * Jenkins
 * Teamcity
 * Travis-ci
 * circle-ci
 * CodeShip
 * drone.io (cloud or standalone)
 * bitbucket (from bitbucket pipeline)
 
A type "other" exists => Any tool can be used.
 
### Managed Result types
The project "result" data type can be : 
 * BUILD : a build / packaging / compilation was processed (on developer env, on CI env ...)
 * RELEASE : the project was released and is now available in repository or ready for install (mvn release, manual release in Jenkins, merge on master branch from SCM ...)
 * TEST : a test set was run (launched from CI tool, from maven ...)
 * DEPLOY : the project was deployed in a repository (deploy from CI tool in npm repo, maven repo ...)
 * INSTALL : the project was installed on an environment (integration environment by CI, manually launched installation in production from jenkins ...)
 * AUDIT : an analysis was ran on the project (covertura, checkstyle, PMD, nexus syntesis ...) 
 * OTHER : Whatever you want to specify as a "Result" on your project
 
### Result types payloads
The payload for each result type is actually freely defined, but for clean badge and report display, some dedicated models are specified and should be used. 

All properties are actually optional : Badge generation will try to use whatever is present

**For TEST result :**

    "payload": {
       "success": {{int nbr of tests in success}},
       "ignored": {{int nbr of tests ignored}},  
       "failed": {{int nbr of tests failed}},  
       "duration": {{int nbr of seconds for test set execution}}
    }
 
**For INSTALL result :**

    "payload": {
       "environment": "{{Freely specified environment name where the install was processed}}",
       "user": "{{Freely specified person name who launched the install - should be a developer email for automatic aggregation}}"
    }

**For DEPLOY result :**

    "payload": {
    	 "path": "{{location of deployed project files. Can be groupId/version path for maven projects}}",
       "repository": "{{repository name - should be a project Repository name for automatic aggregation}}",
       "files": ["{{an array of deployed filenames. Can be maven classifier for example}}"]
    }


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

The badge generator provides various useful badges to insert in your project README. Some are similar to the the standard ones you can put from Travis-CI, drone.io, or from shields.io, some other are more advanced and provides more stuff to illustrate your project.

All the badges are SVG files generated from a project identifier (using project code and version), cached locally. You can add your own cache but the existing caching system is enough for basic needs in a private network.

**Shared features for all badges :**
 * All badges are provided bellow url /badges/{projectCode}/{projectVersion}/{badgeName}.svg.
 * All badges handle the missing project / version => If none corresponding found, a default badge result is provided (for example, for "last build" badge, the value "pending" will be displayed)
 * The projectVersion can be substitued with the value "last" : this way, the "last" project (IE : the higher version value) is used.
 * The projectVersion can be substitued with the value "fresh" : this way, the most recently updated project (whatever the version is) is used. For example, if the project exist in version 1.2.1 and 1.2.14, and if the last test result is applied on version 1.2.1, then the "fresh" version is "1.2.1", and the "last" stay "1.2.14". 
 * For some badges, the projectVersion can also be "pending" (fresh version without any "release" specified) or "released" (fresh version with a release specified)

### Build - Last build result status
**get /badges/{projectCode}/{projectVersion}/build.svg** => most recent build result status for specified project version. "Pending" if none found, "success" if result was "success", "failed" else

**Results :** 
 * *Last build was successful* : ![Build](docs/build-success.png?raw=true "Build")
 * *Last build was failed* : ![Build](docs/build-failed.png?raw=true "Build")
 * *No build found for specified project* : ![Build](docs/build-pending.png?raw=true "Build")
 
### Version - Last version number
For these 3 badges variants, only the fixed semver compliant version is displayed. So if the last "released" if for example "1.3.2.RC", then "1.3.2" is displayed.

**get /badges/{projectCode}/{projectVersion}/version.svg** => Corresponding version for project, in a "version" badge. N/A if version is not found. 

**Results :** 
 * *Specified version exists* : ![Specified version](docs/version-num.png?raw=true "Specified version")
 * *Specified version doesn't exists* : same with "N/A" value on red background
 
**get /badges/{projectCode}/pending/version.svg** => Fresh pending version for the project, in a dedicated "pending" badge. N/A if version is not found.

**Results :** 
 * *A pending version exists* : ![Pending version](docs/version-pending.png?raw=true "Pending version")
 * *No pending version exists (for example, they are all released)* : same with "N/A" value on red background

**get /badges/{projectCode}/released/version.svg** => Fresh released version for the project, in a dedicated "released" badge. N/A if version is not found.

**Results :** 
 * *A released version exists* : ![Released version](docs/version-released.png?raw=true "Released version")
 * *No released version exists* : same with "N/A" value on red background

### Tool
TODO. Will display logo of used tools by result / project

### flowchart
TODO (but yep, it's expected to be one of the badges)

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
