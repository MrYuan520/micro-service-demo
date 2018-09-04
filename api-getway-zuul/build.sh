#!/usr/bin/env bash

mvn package

docker build -t api-getway-zuul:lastest .