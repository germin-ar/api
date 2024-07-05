#!groovy
pipeline {
    agent none
  stages {
    stage('Gradle Install') {
      agent {
        docker {
          image 'gradle:8.7.0-jdk17'
        }
      }
      steps {
        sh 'gradle build'
      }
    }
  }
}
