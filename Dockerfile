pipeline {
    agent any
    
    environment {
        timestamp = "${System.currentTimeMillis() / 1000L}"
    }
    
    stages {
        stage('Prepare') {
            steps {
                script {
                    // Get the ID of the market:latest image
                    def oldImageId = sh(script: "docker images market:latest -q", returnStdout: true).trim()
                    env.oldImageId = oldImageId
                }

                git branch: 'main',
                    url: 'https://github.com/kimhyeji/cod_market'
            }
            
            post {
                success { 
                    sh 'echo "Successfully Cloned Repository"'
                }
                failure {
                    sh 'echo "Fail Cloned Repository"'
                }
            }  
        }
    
        stage('Build Gradle') {
            steps {
                dir('.') {
                    sh """
                    cat <<EOF > src/main/resources/application-secret.yml
                        spring:
                          security:
                            oauth2:
                              client:
                                registration:
                                  kakao:
                                    clientId: d1d06081df927024faf4a60b723dc5c2
                        custom:
                          paymentSecretKey: "test_sk_Gv6LjeKD8aKlR55gDQ54VwYxAdXy:"
                    """
                }
                dir('.') {
                    sh """
                    chmod +x gradlew
                    """
                }
                
                dir('.') {
                    sh """
                    ./gradlew clean build -x test
                    """
                }
            }
            
            post {
                success { 
                    sh 'echo "Successfully Build Gradle Test"'
                }
                 failure {
                    sh 'echo "Fail Build Gradle Test"'
                }
            }    
        }
        
        stage('Build Docker Image') {
            steps {
                script {
                    sh "docker build -t market:${timestamp} ."
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                script {
                    // Check if the container is already running
                    def isRunning = sh(script: "docker ps -q -f name=market_1", returnStdout: true).trim()

                    if (isRunning) {
                        sh "docker rm -f market_1"
                    }
                    
                    // Run the new container
                    try {
                        sh """
                        docker run \
                          --name=market_1 \
                          -p 8080:8080 \
                          -v /docker_projects/market_1/volumes/gen:/gen \
                          --restart unless-stopped \
                          -e TZ=Asia/Seoul \
                          -d \
                          market:${timestamp}
                        """
                    } catch (Exception e) {
                        // If the container failed to run, remove it and the image
                        isRunning = sh(script: "docker ps -q -f name=market_1", returnStdout: true).trim()
                        
                        if (isRunning) {
                            sh "docker rm -f market_1"
                        }
                        
                        def imageExists = sh(script: "docker images -q market:${timestamp}", returnStdout: true).trim()
                        
                        if (imageExists) {
                            sh "docker rmi market:${timestamp}"
                        }
                        
                        error("Failed to run the Docker container.")
                    }

                    // If there's an existing 'latest' image, remove it
                    def latestExists = sh(script: "docker images -q market:latest", returnStdout: true).trim()
                    
                    if (latestExists) {
                        sh "docker rmi market:latest"
                        
                        if(!oldImageId.isEmpty()) {
                        	sh "docker rmi ${oldImageId}"
                        }
                    }

                    // Tag the new image as 'latest'
                    sh "docker tag market:${env.timestamp} market:latest"
                }
            }
        }
    }
}