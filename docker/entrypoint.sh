#!/bin/sh
mkdir -p /usr/local/nginx/logs
cd /home/server
mkdir logs
npm run prod:server
while true
do
sleep 100
done
