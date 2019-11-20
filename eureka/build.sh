#!/usr/bin/env bash

mvn package
docker build -t eureka-server:latest .