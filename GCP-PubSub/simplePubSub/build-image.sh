mvn clean install -DSkipTest=true
docker rmi $(docker images | grep 'spend2get/register-processor')
docker build . -t asia.gcr.io/krungthai-goveco-dev/spend2get/register-processor:$1
docker push  asia.gcr.io/krungthai-goveco-dev/spend2get/register-processor:$1
