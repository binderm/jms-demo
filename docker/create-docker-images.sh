#!/bin/bash
sudo docker build -t jms-demo-master -f master/Dockerfile master/
sudo docker build -t jms-demo-slave -f slave/Dockerfile slave/

