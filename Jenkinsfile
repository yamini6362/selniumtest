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
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${params.BRANCH}"]],
                    userRemoteConfigs: [[url: params.GIT_REPO]]
                ])
            }
        }
        stage('Start Selenium Grid') {
            steps {
                bat 'docker-compose up -d'
                bat 'ping -n 60 127.0.0.1 > nul'
            }
        }
        stage('Build & Test') {
            steps {
                bat "mvn clean test %MAVEN_OPTS%"
            }
        }
        stage('Publish Extent Reports') {
            steps {
                publishHTML(target: [
                    reportDir: 'test-output/ExtentReports',            // Update if your path differs
                    reportFiles: 'ExtentReport_chrome.html,ExtentReport_firefox.html',
                    reportName: 'Extent Reports',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])
            }
        }
        stage('Stop Selenium Grid') {
            steps {
                bat 'docker-compose down'
            }
        }
    }

// post {
//     always {
//         cleanWs()
//     }
 }

