#!/bin/bash

while true; do
    mysql -v -u root demo -e "insert into Item (id, payload) values ('`uuidgen`', '`cat /dev/urandom | base64 | head -n 1`')"
    sleep 1
done
