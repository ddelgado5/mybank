### Instalar dependencias del back

se elaboran microservicios  para simular movimientos transaccionales de un banco
1 cuentas
2 personas

Configuración de la base de datos
Se configuro la base de datos y los 2 microservicios en un archivo docker-compose.yml que se encuentra en la raíz del repositorio

./docker-compose.yml

Ejecutar Docker compose: 
docker compose up --build
o
sudo docker compose up --build

MySql: Puerto 3306
Microservicio personas: Puerto 8082
Microservicio cuentas: Puerto 8083
2.Acceso a la API
• Accede a la API mediante las direcciones http://localhost:8082 y http://localhost:8083 usando cualquier cliente(preferiblemente postman)

3.Probar los endpoits
Para probar los endpoints de tu API utilizando Postman debes importar la colección que esta en la raíz del repositorio de git:

./postDiana.postman_collection.json
Importar la colección postman.
4.Endpoints
CLIENTES
POST Create Cliente joseLema
http://localhost:8082/clientes
 {
  "nombre": "Jose lema",
  "genero": "MASCULINO",
  "edad": 18,
  "identificacion": "12345678900",
  "direccion": "Otavalo sn y principal",
  "telefono": "098254785",
  "contrasena":"1234",
  "estado":true
}
POST Create Cliente c
http://localhost:8082/clientes
{
 "nombre": "Marianela Montalvo",
 "genero": "FEMENINO",
 "edad": 32,
 "identificacion": "12345678901",
 "direccion": "Amazonas y NNUU",
 "telefono": "097548965",
 "contrasena":"5678",
 "estado":true
}
GET Find all

http://localhost:8082/clientes
GET Find by ClienteId

http://localhost:8082/clientes/{id}
PUT Update

http://localhost:8082/clientes
PATCH Patch

http://localhost:8082/clientes/1
DELETE Delete

http://localhost:8082/clientes/1
CUENTA
POST Create account type ahorros for Jose lema

http://localhost:8083/cuentas
POST Create account type corriente for Mairanela Montalvo

http://localhost:8083/cuentas
POST Create account type corriente

http://localhost:8083/cuentas
GET Find all

http://localhost:8083/cuentas
GET Find By Id

http://localhost:8082/cuentas/{id}
PUT Update

http://localhost:8082/cuentas
PATCH Patch

http://localhost:8082/cuentas/1
DELETE Delete

http://localhost:8082/cuentas/2
Movimiento
POST Create Credito Jose Lema

http://localhost:8083/movimientos
POST Create Débito Jose Lema

http://localhost:8083/movimientos
POST Create Credito Mairanela Montalvo

http://localhost:8083/movimientos
POST Create Débito Mairanela Montalvo

http://localhost:8083/movimientos
GET Find all

http://localhost:8082/movimientos
GET Find by Id

http://localhost:8082/movimientos/1
PUT Update

http://localhost:8082/movimientos
DELETE Delete

http://localhost:8082/movimientos/1
Reporte
GET Generate report by cliente id

http://localhost:8083/reportes?clienteId=1&fechaInicial=2024-06-24&fechaFinal=2024-06-24
Query Params:

clienteId: 1
fechaInicial: 2024-06-24
fechaFinal: 2024-06-24
