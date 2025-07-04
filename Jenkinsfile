pipeline {
    agent { label 'gopalam' }

    parameters {
        string(name: 'GIT_REPO', defaultValue: 'https://github.com/yamini6362/selniumtest.git', description: 'Git repository URL')
        
        extendedChoice(name: 'BRANCH',
                       type: 'PT_CHECKBOX',
                       description: 'Select branch to build',
                       multiSelectDelimiter: ',',
                       value: 'main,dev,feature-1,feature-2')
        
        extendedChoice(name: 'BROWSERS',
                       type: 'PT_CHECKBOX',
                       description: 'Select browsers to run tests on',
                       multiSelectDelimiter: ',',
                       value: 'chrome,firefox,edge')
    }

    environment {
        MAVEN_OPTS = "-Dbrowsers=${params.BROWSERS}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    def selectedBranches = params.BRANCH.split(',')
                    if (selectedBranches.size() > 1) {
                        error("Please select only one branch to build at a time.")
                    }
                }
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
                    reportDir: 'test-output',
                    reportFiles: "ExtentReport_${params.BROWSERS}.html",
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

    post {
        always {
            cleanWs()
        }
    }
}
