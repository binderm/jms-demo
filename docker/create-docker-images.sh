#!/bin/bash
docker build -t jms-demo-master -f master/Dockerfile master/
docker build -t jms-demo-slave -f slave/Dockerfile slave/

