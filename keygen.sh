#!/bin/bash

AuthKey=`uuidgen`
Time=`date +%H%M%S`
Time=`echo $Time | base64`
AuthKey=${AuthKey//-/}$Time
AuthKey=${AuthKey//=/}
echo $AuthKey > /AuthKey