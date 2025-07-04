pipeline {
        agent { label 'gopalam' }

    parameters {
        string(name: 'GIT_REPO', defaultValue: 'https://github.com/yamini6362/selniumtest.git', description: 'Git repository URL')
        string(name: 'BRANCH', defaultValue: 'main', description: 'Branch to build')
        string(name: 'BROWSERS', defaultValue: 'chrome,firefox', description: 'Comma-separated list of browsers')
    }

    environment {
        MAVEN_OPTS = "-Dbrowsers=${params.BROWSERS}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: params.BRANCH]], userRemoteConfigs: [[url: params.GIT_REPO]]])
            }
        }
        stage('Start Selenium Grid') {
            steps {
                sh 'docker-compose up -d'
                sh 'sleep 15'
            }
        }
        stage('Build & Test') {
            steps {
                sh "mvn clean test ${env.MAVEN_OPTS}"
            }
        }
        stage('Publish Extent Reports') {
            steps {
                publishHTML([
                    reportDir: 'test-output/ExtentReports',
                    reportFiles: 'ExtentReport_chrome.html,ExtentReport_firefox.html',
                    reportName: 'Extent Reports'
                ])
            }
        }
        stage('Stop Selenium Grid') {
            steps {
                sh 'docker-compose down'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
