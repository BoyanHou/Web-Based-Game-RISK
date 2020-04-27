#!/bin/bash
mkdir coverage
xvfb-run -a --server-args='-screen 0, 1024x768x24' docker run --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
