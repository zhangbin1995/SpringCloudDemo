#!/usr/bin/env bash

mvn package
docker build -t bank1-server:latest .