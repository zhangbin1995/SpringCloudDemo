#!/usr/bin/env bash

mvn package
docker build -t zuul-server:latest .