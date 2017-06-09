#!/bin/bash
war="$(pwd)/../$1/build/libs/$1-$2.war"
config="$1.properties"

container_id=$(docker run -d -P --net jms-demo jms-demo-slave)
docker cp $config $container_id:/config.properties
docker cp $war $container_id:/opt/payara41/glassfish/domains/domain1/autodeploy/
sudo docker logs -f $container_id

