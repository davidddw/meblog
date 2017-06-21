#!/usr/bin/env bash

/etc/init.d/meblog stop
rpm -Uvh $1 --force
#mysql -e "drop database meblog"
#mysql <  /usr/local/meblog/bin/init_db_cmd.sql
sed -i "s#uploadPath=.*#uploadPath=/var/www/webroot#g" /etc/meblog/settings.properties
sed -i "s#urlPath=.*#urlPath=https://www.d05660.top#g" /etc/meblog/settings.properties
/bin/cp /usr/local/meblog/bin/meblog /etc/init.d/meblog
/bin/cp /usr/local/meblog/conf/ssl.conf /etc/nginx/conf.d/ssl.conf
/etc/init.d/nginx reload
/etc/init.d/meblog start