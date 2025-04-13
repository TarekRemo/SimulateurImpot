pipeline {
    agent any

    tools {
        maven 'Maven 3.9.6'     // Nom exact dans "Global Tool Configuration"
        jdk 'JDK 17'            // Idem
    }

    triggers {
        // Scrutation toutes les 2 minutes
        pollSCM('H/2 * * * *')
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
                recordCoverage tools: [jacoco()], sourceFileResolver: sourceFiles('STORE_LAST_BUILD')
            }
        }

        stage('CheckStyle') {
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
