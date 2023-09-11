FROM node:20-alpine as build

WORKDIR /app
COPY ./client/package.json .

RUN npm i

COPY client/ .

RUN npm run build


# FROM nginx

# COPY nginx.conf /etc/nginx/nginx.conf

# WORKDIR /usr/share/nginx/html



