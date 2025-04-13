pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'     // Le nom Maven configuré dans Jenkins (Global Tool Configuration)
        jdk 'JDK 17'            // Le nom du JDK (doit être exactement ce que tu as mis dans Jenkins)
    }

    triggers {
        // Scrutation Git toutes les 2 minutes
        pollSCM('H/2 * * * *')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/TarekRemo/SimulateurImpot.git', branch: 'master'
            }
        }

        stage('Build and Site Generation') {
            steps {
                // Batch Windows : lance mvn avec les options demandées
                bat 'mvn clean test verify site'
            }
        }

        stage('Publish JUnit Test Results') {
            steps {
                junit '**/surefire-reports/TEST-*.xml'
            }
        }

        stage('Publish JaCoCo Coverage') {
            steps {
                jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java'
            }
        }

        stage('Enforce Coverage Threshold') {
            steps {
                jacoco(
                    execPattern: 'target/jacoco.exec',
                    classPattern: 'target/classes',
                    sourcePattern: 'src/main/java',
                    changeBuildStatus: true,
                    minimumLineCoverage: '90'
                )
            }
        }

        stage('CheckStyle Report') {
            steps {
                recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml')]
            }
        }
    }

    post {
        success {
            echo '✅ Build terminé avec succès.'
        }
        failure {
            echo '❌ Build échoué. Vérifie les tests ou la qualité de code.'
        }
    }
}
