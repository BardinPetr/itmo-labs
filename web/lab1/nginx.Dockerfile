FROM node:20-alpine as build
WORKDIR /app

COPY ./client/package.json .
RUN npm i

COPY client/ .

RUN npm run build


FROM nginx:alpine
WORKDIR /app

COPY nginx/default.conf /etc/nginx/conf.d/

COPY --from=build /app/dist ./
