#!/usr/bin/env bash

docker run -d -p 3000:80/tcp --env-file ./secret.list veptechno/echobot:latest
