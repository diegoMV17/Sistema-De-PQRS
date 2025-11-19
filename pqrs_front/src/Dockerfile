FROM nginx:alpine

# Carpeta donde Nginx sirve los archivos
WORKDIR /usr/share/nginx/html

# Copia todo el contenido del frontend
COPY . .

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
