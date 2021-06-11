@Library('jenkins-shared-library') _

stage('Build Push'){
  buildPush(
    service: "$SERVICE",
    giturl: "$GITURL",
    branch: "$GITBRANCH",
    dockerfile: "$DOCKERFILE",
    imageTag: "$BUILD_ID",
    buildArg: ["PYTHON_BASE_IMAGE=python:3.7-slim-buster",
    "AIRFLOW_PRE_CACHED_PIP_PACKAGES=false","ADDITIONAL_PYTHON_DEPS=pytest","AIRFLOW_EXTRAS=all",
    "AIRFLOW_CONSTRAINTS_REFERENCE=contraints-main"]
  )
}

stage('Helm Deploy'){
  helmDeploy(
    service: "$SERVICE",
    giturl: "$GITURL",
    branch: "$GITBRANCH",
    imageTag: "$BUILD_ID",
    valuesFile: "airflow-helm.yaml"
  )
}