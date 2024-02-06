# Docker Compose Spring Boot y PostgreSQL

### Proyecto CRUD con Spring Security, JWT, acceso por roles, conexión con base de datos PostgreSQL vía Docker utilizando docker-compose.

## Ejecutar
Podemos ejecutar el proyecto con un simple comando: 
```bash
docker compose up
```

Docker obtendra las imágenes del Docker Hub (sino se encuentran ya en su sistema operativo).

Podemos ejecutar los servicios en segundo plano con el comando: 
```bash
docker compose up -d
```

## Parar la ejecución
Parar todos los contenedores en ejecución.
```bash
docker compose down
```

Si necesitas parar y eliminar todos los contenedores, redes e imágenes utilizadas por <em>docker-compose.yml</em> file, usar el siguiente comando:
```bash
docker compose down --rmi all
```
