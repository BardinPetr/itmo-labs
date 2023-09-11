FROM php:8-fpm-alpine

WORKDIR /var/www/html/
COPY server/ .
