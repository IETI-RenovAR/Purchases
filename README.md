# RenovAR - Backend

## Descripción del Proyecto
RenovAR es una aplicación que revoluciona la remodelación de espacios mediante Inteligencia Artificial y Realidad Aumentada. Este repositorio contiene el backend del proyecto, desarrollado con Spring Boot, que gestiona la autenticación, almacenamiento de datos y comunicación con la base de datos.

## Tecnologías Utilizadas
- **Lenguaje:** Java 17  
- **Framework:** Spring Boot 3.4.2  
- **Base de Datos:** MongoDB
- **Maven** 

## Dependencias
- `spring-boot-starter-web`  
- `spring-boot-starter-data-mongodb`  
- `spring-security-crypto`  
- `springdoc-openapi-starter-webmvc-ui`  
- `spring-boot-starter-test` (para pruebas)  

## Instalación y Ejecución

1. Clone el repositorio en su máquina local usando Git.

```
git clone https://github.com/IETI-PixelMinds/Back.git
```

2. Navegue hasta el directorio del proyecto.

```
cd Back
```

3. Compile el proyecto usando maven con el siguiente comando:

```
mvn clean install
```

4. Ejecute el proyecto con el siguiente comando:

```
mvn exec:java -Dexec.mainClass="org.adaschool.project.ProjectApplication"
```

5. Una vez el servidor se esté ejecutando ingrese a la siguiente URL usando un navegador web:

```
http://localhost:8080/health
```

## Demostración Endpoints

- **Sin** necesidad de configurar la variable de entorno.
![image](https://github.com/user-attachments/assets/e0f8ef96-6b97-4a10-8844-6edc61f72f81)

- **Con** necesidad de configurar la variable de entorno.
    - Petición Post
![image](https://github.com/user-attachments/assets/f13f9440-058d-43b7-a344-f0cf486f88ba)

    - Petición Get general.
![image](https://github.com/user-attachments/assets/d8f07ccc-a02a-4c44-a524-c8e065707753)

    - Petición Get de usuario específico.
![image](https://github.com/user-attachments/assets/bb545bcc-3675-4e53-8248-47f2f610ffdf)

## Enlace al Documento de Requerimientos
📌 https://annotion.notion.site/Proyecto-Semestre-MVP-198c7898f81980d88f5ac780630baf8b?pvs=4

## Enlace al Documento de Planeación
📌 https://dev.azure.com/IETIPixelMinds/Pixel%20Minds/_backlogs/backlog/Pixel%20Minds%20Team/Epics

## Authors
- Ana Maria Duran
- Johan Estrada
- Juan David Contreras
- Laura Natalia Rojas
- Mauricio Monroy
- Samuel Rojas
