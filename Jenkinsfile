pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh 'bash ./gradlew assemble --stacktrace'
            }
        }
        stage('Test') {
            steps {
                sh 'bash ./gradlew test --stacktrace'
            }
        }
    }
}