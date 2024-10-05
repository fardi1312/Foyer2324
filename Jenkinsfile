pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code from GitHub...'
                git 'https://github.com/fardi1312/Foyer2324.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh 'mvn clean package -DskipTests' 
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test' 
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the project...'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished!'
        }
    }
}
