David Blog Study Demo
=====================
meblog

nginx配置
--------

    server {
        listen 80;
        listen 443 ssl;
        server_name localhost;
     
        ssl_certificate server.crt;
        ssl_certificate_key server.key;
     
        location / {
            proxy_pass http://127.0.0.1:8080;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-Forwarded-Port $server_port;
            proxy_set_header Host $http_host;

            proxy_redirect off;
        }
    }
    
OpenSSL生成 SSL Key 和 CSR 文件
-----------------------------
    	
    openssl req -new -newkey rsa:2048 -sha256 -nodes -out d05660.csr \
        -keyout d05660_top.key \
        -subj "/C=CN/ST=Beijing/L=Beijing/O=D05660 Inc./OU=Web Security/CN=d05660.top"

    settings.properties
    urlPath=https://www.d05660.top
    uploadPath=/var/www/wwwroot

Mac重启nginx
-----------------------------

    uploadPath=/Users/d05660ddw/workshop/uploads
    for i in `ps -ef| grep nginx| grep -v 'grep' | awk '{print $2}'`; do sudo kill -9 $i; done
    sudo nginx
    