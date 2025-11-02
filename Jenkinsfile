pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build and Test') {
            steps {
                script {
                    def isMaven = fileExists('pom.xml')
                    def isGradle = fileExists('build.gradle')
                    def isNpm = fileExists('package.json')

                    if (isMaven) {
                        echo 'Maven project detected. Running Maven build and tests.'
                        sh 'mvn clean install'
                        // Add Selenium test execution command for Maven if applicable
                    } else if (isGradle) {
                        echo 'Gradle project detected. Running Gradle build and tests.'
                        sh 'gradle clean build'
                        // Add Selenium test execution command for Gradle if applicable
                    } else if (isNpm) {
                        echo 'npm project detected. Installing dependencies and running tests.'
                        sh 'npm install'
                        sh 'npm test'
                        // Add Selenium test execution command for npm if applicable
                    } else {
                        echo 'No Maven, Gradle, or npm project detected. Skipping build and test stage.'
                    }
                }
            }
        }
        stage('Selenium Tests on PRs') {
            when {
                branch 'PR-*'
            }
            steps {
                echo 'Running Selenium tests for Pull Request.'
                // Placeholder for actual Selenium test commands
                // Example: sh 'npm run selenium-tests' or 'mvn test -Dgroups=selenium'
            }
        }
    }
    post {
        always {
            echo 'Pipeline finished.'
        }
    }
}