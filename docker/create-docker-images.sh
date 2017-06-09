#!/bin/bash
docker build -t jms-demo-master -f master/Dockerfile master/
docker build -t jms-demo-slave -f slave/Dockerfile slave/

docker network rm jms-demo
docker network create --subnet=172.18.0.0/16 jms-demo
