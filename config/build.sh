#!/usr/bin/env bash

mvn package
docker build -t config-server:latest .