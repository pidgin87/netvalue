#!/bin/bash

java -jar \
  -Xlog:gc*:file=/tmp/gc-%t.log:tags,time,uptime,level:filecount=10,filesize=2M \
  -XX:+HeapDumpOnOutOfMemoryError \
  -XX:HeapDumpPath=/tmp/heap_oom.hprof \
  -Dspring.profiles.active=development \
  application.jar