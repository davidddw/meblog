#!/bin/bash
mkdir -p /usr/local/letsEncrypt
openssl genrsa 4096 > /usr/local/letsEncrypt/account.key
mkdir -p /etc/nginx/cert
openssl genrsa 4096 > /etc/nginx/cert/d05660.key
openssl req -new -sha256 -key /etc/nginx/cert/d05660.key -subj "/CN=www.d05660.top" > /usr/local/letsEncrypt/wwwd05660.csr
openssl req -new -sha256 -key /etc/nginx/cert/d05660.key -subj "/CN=d05660.top" > /usr/local/letsEncrypt/d05660.csr
wget https://raw.githubusercontent.com/diafygi/acme-tiny/master/acme_tiny.py -O /usr/local/letsEncrypt/acme_tiny.py
mkdir -p /var/www/challenges
cat << 'EOF' > /usr/local/letsEncrypt/nossl.conf
server {
    listen 80;
    server_name _;
    location ^~ /.well-known/acme-challenge/ {
        alias /var/www/challenges/;
        try_files $uri =404;
    }
}
EOF
cat << 'EOF' > /usr/local/letsEncrypt/ssl-all-servers.conf
ssl   on;
ssl_session_timeout 5m;
ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_ciphers ECDHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES128-GCM-SHA256:DHE-RSA-AES256-GCM-SHA384:ECDHE-RSA-AES256-SHA384:ECDHE-RSA-AES128-SHA256:ECDHE-RSA-AES256-SHA:ECDHE-RSA-AES128-SHA:DHE-RSA-AES256-SHA:DHE-RSA-AES128-SHA;
ssl_prefer_server_ciphers on;
location / {
    proxy_pass http://127.0.0.1:8080;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
    proxy_set_header X-Forwarded-Port $server_port;
    proxy_set_header Host $http_host;
    proxy_redirect off;
}

error_page  404              /static/404.html;

error_page   500 502 503 504  /static/500.html;

location ~ ^/(WEB-INF)/ {
    deny all;
}

location ~ ^/(uploads|downloads|static)/ {
    root /var/www/webroot;
    expires 24h;
}

location ~ \.(apk|gif|jpg|jpeg|png|ico|rar|css|js|zip|txt|flv|swf|doc|ppt|xls|pdf)$ {
    root /var/www/webroot;
    access_log off;
    expires 24h;
}
EOF
cat << 'EOF' > /usr/local/letsEncrypt/ssl.conf
server {
    listen 443;
    server_name  www.d05660.top;
    include /etc/nginx/includes/ssl-all-servers.conf;
    ssl_certificate   cert/wwwd05660.pem;
    ssl_certificate_key  cert/d05660.key;
}

server {
    listen 443;
    server_name  d05660.top;
    include /etc/nginx/includes/ssl-all-servers.conf;
    ssl_certificate   cert/d05660.pem;
    ssl_certificate_key  cert/d05660.key;
}

server {
    listen 80 default_server;
    listen [::]:80 default_server;
    server_name _;
    location ^~ /.well-known/acme-challenge/ {
        alias /var/www/challenges/;
        try_files $uri =404;
    }
    return 301 https://$host$request_uri;
}
EOF

cat << EEF > /usr/local/letsEncrypt/renew_cert.sh
#!/bin/bash
mkdir -p /etc/nginx/includes/
/usr/bin/cp /usr/local/letsEncrypt/ssl-all-servers /etc/nginx/includes/ssl-all-servers
/usr/bin/cp /usr/local/letsEncrypt/nossl.conf /etc/nginx/conf.d/ssl.conf
systemctl reload nginx
python /usr/local/letsEncrypt/acme_tiny.py --account-key /usr/local/letsEncrypt/account.key \
    --csr /usr/local/letsEncrypt/wwwd05660.csr --acme-dir /var/www/challenges/ > /tmp/wwwsigned.crt || exit
python /usr/local/letsEncrypt/acme_tiny.py --account-key /usr/local/letsEncrypt/account.key \
    --csr /usr/local/letsEncrypt/d05660.csr --acme-dir /var/www/challenges/ > /tmp/signed.crt || exit
wget -O - https://letsencrypt.org/certs/lets-encrypt-x3-cross-signed.pem > /tmp/intermediate.pem
cat /tmp/wwwsigned.crt /tmp/intermediate.pem >/etc/nginx/cert/wwwd05660.pem
cat /tmp/signed.crt /tmp/intermediate.pem >/etc/nginx/cert/d05660.pem
/usr/bin/cp /usr/local/letsEncrypt/ssl.conf /etc/nginx/conf.d/ssl.conf
systemctl reload nginx
EEF

sed -i "/renew_cert/d" /etc/crontab
echo '0 0 1 * * root sh /usr/local/letsEncrypt/renew_cert.sh > /dev/null 2>&1' >>/etc/crontab

exit 0
