#!/bin/bash

# key gen
AuthKey=`uuidgen`
Time=`date +%H%M%S`
Time=`echo $Time | base64`
AuthKey=${AuthKey//-/}$Time
AuthKey=${AuthKey//=/}
echo -n $AuthKey > /AuthKey

# service 
service mariadb start
service nginx start
mysql -uroot -pssafy  -t < home/conf/db/init.sql
mysql -uroot -p -e "set password for 'root'@'localhost' = PASSWORD('$AuthKey')"

java -jar home/conf/app.jar