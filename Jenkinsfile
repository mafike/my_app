
pipeline {
  agent any

  stages {
      stage('Build m Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.war' //so tfhat they can be downloaded later
            }
        }   
    /*  stage('Unit Tests - JUnit and Jacoco') {
       steps {
        sh "mvn test"
        
       }
      } */
     stage('Mutation Tests - PIT') {
      steps {
        sh "mvn org.pitest:pitest-maven:mutationCoverage"
      }
    } 
    /*
     stage('SonarQube - SAST') {
      steps {
        withSonarQubeEnv('sonarqube') {
        sh "mvn clean verify sonar:sonar \
            -Dsonar.projectKey=numeric_app \
            -Dsonar.projectName='numeric_app' \
            -Dsonar.host.url=http://192.168.33.10:9000 "
      }
        timeout(time: 2, unit: 'MINUTES') {
          script {
            waitForQualityGate abortPipeline: true
          }
        }
      }   
     }  */

     stage('Vulnerability Scan - Docker ') {
      steps {
        parallel(
          "Dependency Scan": {
            sh "mvn dependency-check:check"
          },
          "Trivy Scan": {
            sh "bash trivy-docker-image-scan.sh"
          }
        )
      }
    }
       /* stage('Docker Build and Push') {
            steps {
                // Use withCredentials to access Docker credentials
                withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    script {
                        // Print environment variables for debugging
                        sh 'printenv'
                        
                        // Log in to Docker
                        sh "echo \$DOCKER_PASSWORD | docker login -u \$DOCKER_USERNAME --password-stdin"
                        
                        // Build the Docker image
                        sh "docker build -t mafike1/numeric-app:${GIT_COMMIT} ."
                        
                        // Push the Docker image
                        sh "docker push mafike1/numeric-app:${GIT_COMMIT}"
                    }
                }
            }
        } */
    } 
    post {
     always {
      junit '**/target/surefire-reports/*.xml'
      jacoco execPattern: '**/target/jacoco.exec'
     // archiveArtifacts artifacts: 'target/pit-reports/**', allowEmptyArchive: true
      publishHTML(target: [
                reportName: 'PIT Mutation Testing Report',
                reportDir: 'target/pit-reports',
                reportFiles: 'index.html',
                keepAll: true,
                alwaysLinkToLastBuild: true
            ])
    //  dependencyCheckPublisher pattern: 'target/dependency-check-report.xml'
    }


    // success {

    // }

    // failure {

    // }
   }
  
}
