#!/usr/bin/env bash

mvn package
docker build -t bank2-server:latest .