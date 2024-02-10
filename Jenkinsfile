pipeline {
    environment {
       registry = 'docker.io/migra/jenkins.sxb.koffi-accademy.fr'
       dockerImage = '' 
       DOCKERHUB_CREDENTIALS= credentials('dockerHuBID')     
    }
    
   
    agent any
    
    tools {
     maven 'MAVEN_HOME'   
     
        
    }

    stages {
        stage('step - 1: clone repository') {
            steps {
                git branch: 'main', url: 'https://github.com/migra-share/CI-CD.git'
                   }
                  }
                  
        stage('Step - 2 Build -package') { 
            steps {
                sh 'mvn clean package' 
            }
        }
        
       stage("Step - 3 SonarQuebe analyser") {
                steps {
                 script {
                   withSonarQubeEnv(installationName : 'Sonarqube_server', credentialsId : 'jenkins-sonar-tocken') {
                 sh 'mvn sonar:sonar'
              }
              
              timeout(time: 6, unit: 'SECONDS') {
               def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
              }
           }
          }
        }
       }
       
       
           stage('Step - 4- Check security of code') { 
            agent any
            steps {
                snykSecurity(

                        snykInstallation: 'snyk',
                        snykTokenId: 'snyk_tocken',
                        failOnIssues: false,
                        monitorProjectOnBuild: false,
                        targetFile: '/var/lib/jenkins/workspace/Pipeline-CI-CD/target/ci-cd-pipeline-0.0.1-SNAPSHOT.jar'

                )
            }
        }
       
       
       
       
    stage('Step - 5 Build -Image') { 
            steps {
                script {
                    dockerImage = docker.build(registry + ":${BUILD_NUMBER}")
                }
            }
        }
        
        
    stage('Step 6 - Login to Docker Hub') {      	
        steps{  
                                	
	        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'                		
         	echo 'Login Completed'   

         }    
    }
    
  stage('Step 7 - Push Image to Docker Hub') {         
    steps{    
        sh ' docker push $registry:$BUILD_NUMBER'           
        echo 'Push Image Completed'       
    }            
}  

  stage('Step 8 - clean  Image after push') {         
    steps{    
        sh ' docker rmi $registry:$BUILD_NUMBER'           
        echo 'prune Image Completed'      
    }            
}  

   stage('Step 9 - Deploy playbook') {         
    steps{    
         ansiblePlaybook disableHostKeyChecking: true, installation: 'ANSIBLE', inventory: 'hosts.ini', playbook: 'playbook.yml', sudoUser: null, vaultCredentialsId: 'vault-password', vaultTmpPath: '/usr/local/sbin/',extras: '-e tag=${BUILD_NUMBER}'       
         echo 'playbook Completed'      
    }            
} 
  
    }

  
}
