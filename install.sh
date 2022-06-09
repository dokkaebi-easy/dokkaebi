#!/bin/bash

# key gen
AuthKey=`uuidgen`
Time=`date +%H%M%S`
Time=`echo $Time | base64`
AuthKey=${AuthKey//-/}$Time
AuthKey=${AuthKey//=/}
echo $AuthKey > /AuthKey
echo -n $AuthKey > /home/conf/AuthKey

# service 
service mariadb start
service nginx start
mysql -uroot -pdokkaebi  -t < home/conf/db/init.sql
mysql -uroot -p -e "set password for 'root'@'localhost' = PASSWORD('$AuthKey')"
java -jar -Duser.timezone=Asia/Seoul home/conf/app.jar
#java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar home/conf/app.jar