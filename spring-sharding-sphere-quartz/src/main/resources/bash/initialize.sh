#!/bin/bash

# docker部署
apt-get install -y docker.io

# docker配置
cat << EOF > /etc/docker/daemon.json
{
  "registry-mirrors": [
    "https://hub-mirror.c.163.com",
    "https://docker.mirrors.ustc.edu.cn/"
  ],
  "debug": true,
  "experimental": true
}
EOF

sudo systemctl daemon-reload
sudo systemctl restart docker

# mysql部署
docker run -d --name mysql \
--restart always \
-v /etc/localtime:/etc/localtime \
-v /opt/mysql:/var/lib/mysql \
-e MYSQL_ROOT_PASSWORD=FN5HW5Y1XQJ742I7 \
-p 3306:3306 mysql:5.7

# 初始化数据库
wget -c https://raw.githubusercontent.com/quartz-scheduler/quartz/master/quartz-core/src/main/resources/org/quartz/impl/jdbcjobstore/tables_mysql_innodb.sql

docker exec -it mysql mysql -uroot -pFN5HW5Y1XQJ742I7 -e "create database if not exists quartz default character set utf8mb4;"
docker cp tables_mysql_innodb.sql mysql:/opt/
docker exec -it mysql bash -c "mysql -uroot -pFN5HW5Y1XQJ742I7 -Dquartz < /opt/tables_mysql_innodb.sql"

