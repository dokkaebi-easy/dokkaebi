#!/bin/bash

CONTAINER=$1
docker rm -f $CONTAINER 2> /dev/null || true