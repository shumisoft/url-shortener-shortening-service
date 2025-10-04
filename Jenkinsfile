pipeline {
    agent any
    
    tools {
        jdk 'jdk21'
        maven 'maven3'
        nodejs 'node'
    }

    environment {
        SCANNER_HOME= tool 'sonar-scanner'
    }

    stages {
        stage('Git Pull') {
            steps {
                git branch: 'development', credentialsId: 'git-cred-d', url: 'https://github.com/shumisoft/url-shortener-shortening-service.git'
            }
        }
        stage('Maven Compile') {
            steps {
               sh 'mvn compile'
            }
        }
        stage('Run Test') {
            steps {
                // sh 'mvn test'
                echo 'Todo'
                echo 'Skipping...'
            }
        }
        stage('Trivy Vulnarability Scan') {
            steps {
                // echo 'todo'
                sh 'trivy fs -f table -o trivy-usss-report.html . && cat trivy-usss-report.html'
            }
        }
        stage('SonarQube Scan') {
            steps {
                withSonarQubeEnv('SonarQubeOC-DS') {
                    sh ''' $SCANNER_HOME/bin/sonar-scanner -Dsonar.projectName="URL-Shortener-Shortening-Service" -Dsonar.projectKey="URL-Shortener-Shortening-Servic" \
                            -Dsonar.java.binaries=. '''
                }
            }
        }
        stage('Sonar Quality Gate') {
            steps {
                script {
                  waitForQualityGate abortPipeline: false, credentialsId: 'sonarqube-cred-d' 
                }
            }
        }
        stage('Build') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        stage('Build & Push Multi-Arch Docker Image') {
            steps {
               script {
                   withDockerRegistry(credentialsId: 'dockerhub-cred-d') {
                      sh "docker buildx create --name mybuilder --use"
                       sh """docker buildx build \
                       --platform linux/amd64,linux/arm64 \
                       -t dipanshushukla/url-shortener-shortening-service:latest-dev \
                       --push ."""
                   }
               }
            }
        }


        stage('Trivy Docker Image Scan') {
            steps {
                sh 'trivy image -f table -o trivy-usss-container-image-report.html dipanshushukla/url-shortener-shortening-service:latest-dev && cat trivy-usss-container-image-report.html'
            }
        }
    }
}