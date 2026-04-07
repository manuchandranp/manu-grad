plugins {
    id("com.jfrog.artifactory") version "5.+"
}

allprojects {
    repositories {
        maven {
            url "https://hts2.jfrog.io/artifactory/manu-libs-libs-release"
        }
    }
}

//version = currentVersion

apply plugin: 'com.jfrog.artifactory'
//apply plugin: 'com.android.library'
apply plugin: 'java'
apply plugin: 'maven-publish'
group='com.sonymobile.swtool'
version='1.0.4'


dependencies {
    
}
def deployer = hasProperty('deployer') ? "$deployer" : null
def deployerpw = hasProperty('deployerpw') ? "$deployerpw" : null
def gitUrl = 'git config --get remote.origin.url'.execute().text.trim()
def gitTag = 'git describe --tags'.execute().text.trim()
def gitSubDir = hasProperty('gitSubDir') ? "$gitSubDir" : "none"

publishing {
    publications {
        mavenJava(MavenPublication) {
            from components.java
        }
    }
}

artifactory {
    contextUrl = 'https://hts2.jfrog.io/artifactory'
    publish {
        repository {
            repoKey = 'manu-libs-libs-release' // The Artifactory repository key to publish to
            username = 'manuchandranp@jfrog.com' // The publisher user name
            password = 'Password@123' // The publisher password
        }
        defaults {

         publications('mavenArtifacts')
         properties = ['somcid': "$System.env.USER",
             'sdkVersion': "android-28",
             'giturl': "$gitUrl" ?: null,
             'gittag': "$gitTag" ?: null,
             'gitsubdir': "$gitSubDir" ]
             publications('mavenJava')
             publishArtifacts = true
             publishPom = true

    }
  }
        //defaults {
            // Reference to Gradle publications defined in the build script.
            // This is how we tell the Artifactory Plugin which artifacts should be
            // published to Artifactory.
           // publications('mavenJava')
            //publishArtifacts = true
            // Properties to be attached to the published artifacts.
            //properties = ['qa.level': 'basic', 'dev.team' : 'core']
            // Publish generated POM files to Artifactory (true by default)
           // publishPom = true
        //}
        clientConfig.proxy.host = ""

        clientConfig.proxy.port = 8080
    }
