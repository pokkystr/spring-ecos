mvn clean install -DSkipTest=true
docker rmi $(docker images | grep 'spend2get/pull-blackup')
docker build . -t asia.gcr.io/krungthai-goveco-dev/spend2get/pull-blackup:$1
docker push  asia.gcr.io/krungthai-goveco-dev/spend2get/pull-blackup:$1
