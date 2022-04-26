#!/bin/bash

AuthKey=$(<AuthKey)

service mariadb start
mysql -uroot -pssafy  -t < DB/init.sql
mysql -uroot -p -e "set password for 'root'@'localhost' = PASSWORD('$AuthKey')"

java -jar ./app.jar