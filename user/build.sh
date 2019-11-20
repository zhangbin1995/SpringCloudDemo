#!/usr/bin/env bash

mvn package

docker build -t user-server:latest .
#docker build -t hub.zhangbin.com:8080/services/user-server:latest .

#docker push hub.zhangbin.com:8080/services/user-server:latest