#!/usr/bin/env bash

mvn package
docker build -t auth-server:latest .