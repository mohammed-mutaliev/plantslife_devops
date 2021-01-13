pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                sh "sudo chown root:jenkins /run/docker.sock"
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