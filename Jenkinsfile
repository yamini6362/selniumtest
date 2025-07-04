pipeline {
    agent { label 'gopalam' }

    environment {
        MAVEN_OPTS = "-Dbrowsers=${params.BROWSERS}"
    }

    stages {
        stage('Setup Parameters') {
            steps {
                script {
                    properties([
                        parameters([
                            [$class: 'StringParameterDefinition',
                                name: 'GIT_REPO',
                                defaultValue: 'https://github.com/yamini6362/selniumtest.git',
                                description: 'Git repository URL'
                            ],
                            [$class: 'ExtendedChoiceParameterDefinition',
                                name: 'BRANCH',
                                description: 'Select branch to build (only one)',
                                multiSelectDelimiter: ',',
                                type: 'PT_CHECKBOX',
                                value: 'main,dev,feature-1,feature-2'
                            ],
                            [$class: 'ExtendedChoiceParameterDefinition',
                                name: 'BROWSERS',
                                description: 'Select browsers to run tests on',
                                multiSelectDelimiter: ',',
                                type: 'PT_CHECKBOX',
                                value: 'chrome,firefox,edge'
                            ]
                        ])
                    ])

                    def selectedBranches = params.BRANCH?.split(',')
                    if (selectedBranches == null || selectedBranches.size() != 1) {
                        error("Please select exactly one branch to build.")
                    }

                    env.SELECTED_BRANCH = selectedBranches[0]
                }
            }
        }

        stage('Checkout Code') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: "*/${env.SELECTED_BRANCH}"]],
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
                    reportFiles: 'ExtentReport_chrome.html,ExtentReport_firefox.html,ExtentReport_edge.html',
                    reportName: 'Extent Reports',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: true
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
