#!/bin/bash

docker network rm jms-demo
docker network create --subnet=172.18.0.0/16 jms-demo
