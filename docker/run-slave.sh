#!/bin/bash
war="../$1/build/libs/$1-$2.war"
config="$1.properties"

docker run -it --net jms-demo jms-demo-slave
bg
docker cp $config $container_id:/config.properties
docker cp $war $container_id:/opt/payara41/glassfish/domains/domain1/autodeploy/
fg
#sudo docker logs -f $container_id

