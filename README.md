# Nisum
Java API spring security + JWT
## Características
Es un proyecto construido con Java version 8, Spring boot 2.7 y Spring Security 5. Consta de 3 Endpoints creación de usuario (POST), Actualización (POST) y Login(POST). El endpoint de creación de usuario y login no tienen seguridad por lo cual se pueden consumir sin autenticación, pero el endpoint de modificación de usuario requiere de una autenticación tipo Bearer en la cual se en via el token que es generado por login luego de validar el usuario.
## ¿Cómo probar?
Para la realización de las pruebas se agrega en la ruta /src/main/resources/ se agrega el archivo NisumApi.postman_collection.json para importar las peticiones en postman. Lo primero que se debe realizar es consumir el endpoint de creación de usuario teniendo en cuenta que los campos debe cumplir con los formatos adecuados, ya que se valida el correo y contraseña, entre otros. Luego de crear el usuario se puede proceder a logear al usuario para obtener el token de autenticacón el cual se recibe en el cuerpo de la respuesta, copiar el token. Finalmente, para probar el endpoint de actualización se debe ir a las configuraciones de Authentication en postman, elegir el tipo Bearer Token y pegar el valor del token copiado anteriormente en el campo token de la peticion de actualización.

## ¿Cómo acceder a la base de datos?
El proyecto se contruyó con una de base de datos en memoria para simplificar su uso, para acceder a la consola de administración se debe ingresar a la url http://localhost:10050/nisum-api/h2/ y usar los valores:
 - JDBC URL: jdbc:h2:mem:shared;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
 - Driver Class: org.h2.Driver
 - User Name:sa
 - Password:  (sin contraseña dear el espacio en blanco)

## ¿Cómo acceder documentación swagger?

Para accceder a swager acceder http://localhost:10050/nisum-api/ y esta url redirigira a la documentación swagger.

## Diagramas de la solución
En caso de que la imagen a continuación no se pueda ver por favor ir a la ruta /src/main/resources/ donde se puede consultar el archivo Diagramas.png con un diagrama de flujo de la autenticación y un resumido diagrama de clases.

![image](https://github.com/james077/Nisum/assets/28771204/54ff20bb-df45-4647-9c22-b05f5387c667)



