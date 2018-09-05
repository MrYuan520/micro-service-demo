#!/usr/bin/env bash
docker build -t 192.168.5.155/micro-service-demo/message-service:latest .

docker push 192.168.5.155/micro-service-demo/message-service:latest