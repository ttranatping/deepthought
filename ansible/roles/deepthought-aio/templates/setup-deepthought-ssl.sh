#!/bin/bash

set -x

if test -f "/root/.deepthoughtssl"; then
	echo "Deep Thought SSL Initialisation has already been complete, exiting"
	exit 0
fi

HOSTNAME=$(curl -s {{ cdr_zone_dns_mapper_endpoint }})

echo "Setting up for $HOSTNAME, sleeping 120 seconds for DNS propogation"
sleep 120

if ! certbot --nginx -n certonly --agree-tos --email dev@null.com -d "$HOSTNAME"
then
	echo "Certbot failed retrieval, halting progress"
	exit 1
fi

echo "Linking fullchain.pem"
rm -f /etc/letsencrypt/fullchain.pem
ln -s /etc/letsencrypt/live/"$HOSTNAME"/fullchain.pem /etc/letsencrypt/fullchain.pem
echo "Linking privkey.pem"
rm -f /etc/letsencrypt/privkey.pem
ln -s /etc/letsencrypt/live/"$HOSTNAME"/privkey.pem /etc/letsencrypt/privkey.pem

echo "Restarting nginx server"
/bin/systemctl restart nginx

echo "Performing search and replace for hostnames"
sed -i "s/http:\/\/localhost\:8081\/dio-au/https:\/\/$HOSTNAME\/dio-au/g" /var/www/html/assets/config.json
sed -i "s/http:\/\/localhost\:8080/https:\/\/$HOSTNAME/g" /var/www/html/assets/config.json
sed -i "s/http:\/\/localhost\:8080/https:\/\/$HOSTNAME/g" /etc/deepthought-product-api.yml
sed -i "s/http:\/\/localhost\:8080/https:\/\/$HOSTNAME/g" /etc/deepthought-admin-service.yml
sed -i "s/https:\/\/localhost/https:\/\/$HOSTNAME/g" /etc/update-motd.d/999-deepthought

echo "Restarting deepthought-admin-service"
/bin/systemctl restart deepthought-admin-service

echo "Restarting deepthought-product-api"
/bin/systemctl restart deepthought-product-api

echo "Touching initialisation success"
touch /root/.deepthoughtssl
