pipeline {
    agent any

    triggers {
        pollSCM('H/2 * * * *')  // Scrutation Git toutes les 2 minutes
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/TarekRemo/SimulateurImpot.git', branch: 'master'
            }
        }

        stage('Build and Site') {
            steps {
                bat 'mvn clean test verify site'
            }
        }

        stage('JUnit Report') {
            steps {
                junit '**/surefire-reports/TEST-*.xml'
            }
        }

        stage('Coverage Report (JaCoCo)') {
            steps {
                recordCoverage tools: [jacoco()]  // ✅ ligne corrigée
            }
        }

        stage('CheckStyle Report') {
            steps {
                recordIssues tools: [checkStyle(pattern: 'target/checkstyle-result.xml')]
            }
        }
    }

    post {
        always {
            echo "🎯 Build terminé. Vérifiez les rapports dans Jenkins."
        }
        success {
            echo '✅ Build terminé avec succès.'
        }
        failure {
            echo '❌ Échec du build.'
        }
    }
}
