pipeline {
    agent any
    tools {
        gradle "GRADLE_LATEST"
    }
    parameters {
        string(name: 'tags', defaultValue: 'consumirApiRestAndroid; ls /', description: 'Tag ejecucion de prueba')
      }
       stage('Git checkout') { // for display purposes
          git url: 'https://github.com/automatizaciontesting/pruebaAppgate.git', branch: 'master'
       }
    stage('Smoke') {
            try {
                sh 'gradle clean test -Dtags=${tags}'
            } catch (err) {

            } finally {
                publishHTML (target: [
                reportDir: 'target/site/serenity',
                reportFiles: 'index.html',
                reportName: "Smoke tests report"
                ])
            }
       }

}