#!/bin/bash

AuthKey=$(< /AuthKey)
echo $AuthKey
service mariadb start
mysql -uroot -pssafy  -t < home/conf/db/init.sql
mysql -uroot -p -e "set password for 'root'@'localhost' = PASSWORD('$AuthKey')"

java -jar home/conf/app.jar