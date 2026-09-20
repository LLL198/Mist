FROM nginx:1.27-alpine

COPY frontend/dist/ /usr/share/nginx/html/
COPY frontend/ui/ /usr/share/nginx/html/mist-ui/
COPY frontend/nginx/default.conf /etc/nginx/conf.d/default.conf
