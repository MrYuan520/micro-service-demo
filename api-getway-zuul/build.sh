#!/usr/bin/env bash

mvn package

docker build -t 192.168.5.155/micro-service-demo/api-getway-zuul:latest .

docker push 192.168.5.155/micro-service-demo/api-getway-zuul:latest