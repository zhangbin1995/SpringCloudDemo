#!/usr/bin/env bash

docker stop $(docker ps -a -q) #stop停止所有容器
docker rm $(docker ps -a -q) #remove删除所有容器