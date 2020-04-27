#!/bin/bash
mkdir coverage
export DISPLAY=:0
Xvfb :0 -screen 0 1024x768x24 &
docker run --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
