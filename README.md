## CHALLENGE GLOBAL-LOGIC

### DESCRIPCION DE LA API-REST:
Consta de una api desarrollada con versión de java 17, Spring-boot (2.6.13) y Gradle.
Puerto utilizado: 8080


El ejercicio tiene la función de administrar usuarios ("User") dentro de los cuales se dispone de una administración de sesiones.

Cada sesión tiene un tiempo de duración de 5 minutos, una vez pasado ese tiempo.
El usuario deberá reingresar sus credenciales y generar un nuevo Token.

______

## USUARIOS:

### NUEVO USUARIO:

Al momento de dar de alta un usuario se requiere de la siguiente información:

        - Nombre de usuario (Opcional)
        - Mail *
        - Password *
        - Teléfonos (Opcional)

El Teléfono está compuesto por los siguientes atributos:

        - Número de Teléfono (Campo Numérico) *
        - Código de área (Campo numérico) *
        - Código de País (País correspondiente al teléfono) *

Condiciones para el alta de usuario:

        - Mail debe contener "@" y el "." en el dominio.
        - Contraseña debe contener una Mayúscula y al menos dos campos numéricos.
        - Los campos "Nombre" y "telefonos" son optativos.

El Body deberá ser el siguiente:

        {
            "email": "string",
            "name": "string",
            "password": "string",
            "phones": [
                {
                    "city_code": 0,
                    "country_code": "string",
                    "number": 0
                }
            ]
        }

ENDPOINT:

      curl -X POST "http://localhost:8080/challenge/api/v1/user/create" -H "accept: */*" -H "Authorization: aw" -H "Content-Type: application/json" -d "{ \"email\": \"pedrogmail.com\", \"name\": \"Pedro\", \"password\": \"1234Asd\", \"phones\": [ { \"city_code\": 101, \"country_code\": \"MX\", \"number\": 123456789 } ]}"

RESPONSE:

    {
      "code": 200,
      "message": "OK",
      "timestamp": "2023-09-06T16:49:16.900957",
      "body": {
          "id": 2,
          "name": "Pedro",
          "mail": "pedro@gmail.com",
          "password": "1234Asd",
          "phones": [
              {
                "id": 2,
                "number": 123456789,
                "city_code": 101,
                "country_code": "MX"
              }
          ],
      "user_token": "4063aed2-da2a-4c16-a3ad-9e645d0c81b7",
      "sessions": null
      }
    }

EXCEPCIONES:

- USUARIO YA EXISTENTE: 400 - BAD REQUEST

      {
        "code": 400,
        "message": "User already registered with mail pedroa@gmail.com",
        "timestamp": "2023-09-06T16:54:09.226075"
      }

- MAIL INCORRECTO: 400 - BAD REQUEST

      {
        "code": 400,
        "message": "The mail format is incorrect",
        "timestamp": "2023-09-06T16:55:15.025913"
      }
  
- CONTRASEÑA INCORRECTA: 400 - BAD REQUEST

      {
        "code": 400,
        "message": "The password format is incorrect",
        "timestamp": "2023-09-06T16:56:42.025533"
      }

- 429: TOO MANY REQUEST

      {
        "code": 429,
        "message": "Too many requests (rate: 3 request/min)",
        "timestamp": "2023-08-17T11:02:52.611045"
      }

- 500: INTERNAL SERVER ERROR

      {
          "code": 500,
          "message": "Fatal Exception: {Error description}",
          "timestamp": "2023-08-17T07:26:20.321829"
      }



### CONSULTA DE USUARIOS EXISTENTES:

Para obtener las consultas de usuarios, se realizó un paginado cada 10 elementos obtenidos de la lista de Operaciones.

ENDPOINT: Consulta página 1:

    http://localhost:8080/challenge/api/v1/user?page=1

RESPONSE:

    {
      "code": 200,
      "message": "OK",
      "timestamp": "2023-09-06T17:58:01.16347",
      "body": {
        "content": [],
        "pageable": {
          "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
          },
          "offset": 10,
          "page_number": 1,
          "page_size": 10,
          "paged": true,
          "unpaged": false
        },
        "last": true,
        "total_pages": 1,
        "total_elements": 3,
        "first": false,
        "size": 10,
        "number": 1,
        "sort": {
          "empty": true,
          "sorted": false,
          "unsorted": true
        },
        "number_of_elements": 0,
        "empty": true
      }
    }


_____

## SESIONES:

Cada usuario podrá loguearse por un lapso de 5 minutos. Posterior a este tiempo, el "Session Token" expirará y deberá volver a loguearse.

En caso de que el usuario vuelva a loguearse se generará un nuevo token, y volverá a disponer del mismo timepo de sesión.

### LOGIN

Para el login se requiere de la siguiente información:

    - Mail
    - Contraseña

ENDPOINT:

    curl -X POST "http://localhost:8080/challenge/api/v1/session" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"email\": \"pedro@gmail.com\", \"password\": \"1234Asd\"}"

RESPONSE:

    {
      "code": 200,
      "message": "OK",
      "timestamp": "2023-09-07T14:44:44.967716",
      "body": {
        "id": 67,
        "created": "2023-09-07T14:44:44.959957",
        "token": "36e9650a-f938-4a0c-a210-b39c26b767ef",
        "name": "nacho20",
        "mail": "nacho20@gmail.com",
        "password": "xxxxxxxxx",
        "phones": [
          {
            "number": 1141932848,
            "city_code": 54,
            "country_code": "ARG"
          }
      ],
      "active": true
      }
    }

EXCEPCIONES:

- USUARIO O CONTRASEÑA ERRONEA -> 401 - NO AUTORIZADO:

      {
        "code": 401,
        "message": "User do not exist or password is incorrect",
        "timestamp": "2023-09-06T18:12:08.618436"
      }

- ERROR DESCONOCIDO -> 500:

      {
        "code": 401,
        "message": "Fatal Exception: {Descripción del error}",
        "timestamp": "2023-09-06T18:12:08.618436"
      }


### VALIDACION SI ESTA CONECTADO:

Como se mencionó, se definió la duración de la sesión en 5 minutos.

La misma puede ser modificada como una variable de entorno en el application.properties.

La expiración del token puede ocurrir por dos motivos:

1. Transcurridos el tiempo de duración del token.
2. Un nuevo login.


____

## UTILIZAR LA BD H2 EN EL BROWSER:

H2 consiste en una bd en memoria. 
Sin embargo, existe la posiblidad de generar la bd a través de un archivo (/data/challenge).
De esta forma, frente a un reinicio de la aplicación no se perderán los datos persistidos.

    http://localhost:8080/challenge/h2-console/login.jsp

Información para el logueo:

    - Usuario: "admin"
    - Password: "password"
    - JDBC URL: "challenge"

_____

## REQUEST RATE:
En caso de disponer de varias request por minuto, 
se planteó limitarlas a una tasa estimada en 3 request / min.
En caso de superar este límite se dispondrá de la siguiente respuesta:

- 429: TOO MANY REQUEST
      {
        "code": 429,
        "message": "Too many requests (rate: 3 request/min)",
        "timestamp": "2023-08-17T11:02:52.611045"
      }

_____

## SWAGGER:

    LocalHost: http://localhost:8080/challenge/swagger-ui.html

_____

## VERSIONADO DE LA BD
    
Se utilizó Flyway para versionar la BD.
La query inicial para la creación de las tablas se encuentra en la siguiente ruta:

    .../resources/db/migration