# Etapa 1: Construcción del frontend Angular
FROM node:18 AS frontend-builder

WORKDIR /app

# Copiamos SOLO los archivos necesarios para instalar las dependencias
COPY package.json package-lock.json ./

# Instalamos dependencias
RUN npm install

# Ahora sí copiamos el resto del proyecto
COPY . .

# Compilamos Angular
RUN npm run build --prod

# Etapa 2: Servir con Nginx
FROM nginx:alpine

# Copiamos el build generado al servidor Nginx
COPY --from=frontend-builder /app/dist /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
