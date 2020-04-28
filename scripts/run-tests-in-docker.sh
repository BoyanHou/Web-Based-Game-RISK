#!/bin/bash
rm -r -f coverage
mkdir coverage
docker run --rm -v `pwd`/coverage:/coverage-out  citest scripts/test.sh
