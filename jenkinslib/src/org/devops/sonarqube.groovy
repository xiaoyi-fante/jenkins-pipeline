package org.devops

//scan

def SonarScan(sonarServer,projectName, projectDesc,projectPath){
    //定义服务器列表
    def servers=["test":"sonarqube-test", "prod":"sonarqube-prob"]
    
    withSonarQubeEnv("${servers[sonarServer]}"){
        def sonarHome = "/usr/local/sonar-scanner-4.4.0.2170-linux/bin"
        def sonarDate = sh  returnStdout: true, script: 'date +%Y%m%d%H%M%S'
        sonarDate = sonarDate - "\n"
        
        sh """
            ${sonarHome}/sonar-scanner -Dsonar.projectKey=${projectName} \
            -Dsonar.projectName=${projectName} \
            -Dsonar.projectVersion=${sonarDate} \
            -Dsonar.ws.timeout=30 \
            -Dsonar.projectDescription=${projectDesc} \
            -Dsonar.links.homepage=http://www.baidu.com \
            -Dsonar.sources=${projectPath} \
            -Dsonar.sourceEncoding=UTF-8 \
            -Dsonar.java.binaries=target/classes \
            -Dsonar.java.test.binaries=target/test-classes \
            -Dsonar.java.surefire.report=target/surefire-reports \
        """
    }
    //def qg=waitForQualityGate()
    //if (qg.status != 'OK') {
        //error "Pipeline aborted due to quality gate failure: ${qg.status}"
    //}
}