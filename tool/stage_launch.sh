#!/bin/bash

set -eux

SCRIPT_DIR=$(cd $(dirname $0); pwd)
BACKEND_DIR=$(cd ${SCRIPT_DIR}/../; pwd)


LIB_DIR="/var/lib/sbt"
TARGET_DIR="/var/app/target"

ln -fsn ${LIB_DIR} ${TARGET_DIR}

STAGE_DIR=${TARGET_DIR}"/universal/stage"
PID=${STAGE_DIR}/RUNNING_PID
BIN=${STAGE_DIR}/bin/guild

if [ ! -d ${STAGE_DIR} ]
then
  cd ${BACKEND_DIR}
  # Since become out of memory at the time of the first is brought together to run the sbt command , performed separately
  # sbt clean stage
  sbt clean
  # re-created because the target directory from being deleted
  ln -fsn ${LIB_DIR} ${TARGET_DIR}
  sbt compile
  sbt stage
fi

trap 'kill $(cat ${PID:-/var/tmp/(・∀・)}); exit 0' EXIT

${BIN}
