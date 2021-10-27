#!/bin/bash

# A "full" build, including coverage and jar assembly.
# Adjust this according to which stges are relevant for your build

sbt \
  clean \
  coverage \
  test \
  IntegrationTest/test \
  coverageAggregate \
  coverageOff \
  assembly
