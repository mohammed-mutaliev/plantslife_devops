pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh 'bash ./gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                sh 'bash ./gradlew test'
            }
        }
        stage('Build Docker image') {
            steps {
                sh 'bash ./gradlew docker'
            }
        }
    }
}