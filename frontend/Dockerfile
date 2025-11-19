# Etapa 1: Build Angular
FROM node:18 AS builder
WORKDIR /app

# Copiar package.json primero para aprovechar la cache
COPY package*.json ./
RUN npm install

# Copiar el resto del código Angular
COPY . .

# Compilar Angular
RUN npm run build --prod

# Etapa 2: Servir con Nginx
FROM nginx:alpine
COPY --from=builder /app/dist/ /usr/share/nginx/html

# Abrir puerto
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
