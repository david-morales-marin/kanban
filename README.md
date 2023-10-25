# Sistema Kanban

Sistema Kanban basado en la web para la gestión de proyectos. El sistema debe permitir a los usuarios gestionar proyectos, organizar tareas en diferentes etapas y realizar seguimiento del progreso de las tareas.

![Imagen tablero kanban](/doc/kanban.png)

## Tecnología
- Java 17
- Spring boot 3.0.5
- JPA, H2
- Gradle 8.2.1
- Swagger 3
- Database MySql
- JWT , Spring Security
- JUNIT 5 
- Testing Mockito

## Diagrama de clases
![Imagen tablero kanban](/doc/pry.png)

## Diagrama de base de datos

![Imagen tablero kanban](/doc/modelo.png)

## Entitys

Project es la entidad principal donde se desprende todo el sistema, de la cual se derivan la entidad task, la cual no puede existir sin un proyecto. El proyecto maneja diversos estados los cuales se representan por un ENUM (Project status) así mismo la entidad task contiene dos ENUM el primero define el estado de la tarea (task status) el segundo define el tipo de tarea (task type).

Para desarrollar el sistema se parte bajo la relación de uno a muchos, es decir que para un proyecto se puede tener muchas tareas, pero para una tarea no se puede tener varios proyectos.

![Imagen tablero kanban](/doc/entitys.png)

## Controllers

En los controladores, se desarrollo toda la documentación de Swagger teniendo controlada los tipo de respuestas que se pueden obtener para cada ejecución de los edponints, También 
es el lugar donde se encuentra la definición de cada endpoint realizado y por ende es la parte que interactua con la parte del front ya que es donde se encuentra los metodos.

![Imagen tablero kanban](/doc/controllers.png)

Un ejemplo de un edpoint dentro de los controladores es:

![Imagen tablero kanban](/doc/ejeControllers.png)

## Servis

Sí bien cada una de las partes es fundamental dentro del proceso, los servicios, son la parte más importante, dado que acá es el lugar donde 
se encuentra toda la logica que se manejo para el desarrollo de la aplicación. Además que se crea un servicio para la generación del Token que sirve para la autenticación 
de nuestro proyecto a desarrollar. 

![Imagen tablero kanban](/doc/services.png)

## Endpoints
El sistema fue desplegado en la siguiente url https://kanban-production-d917.up.railway.app/ para 
obtener información acerca de los endpoints expuestos por este servicio, leer documentación de swagger
This is [swagger](https://kanban-production-d917.up.railway.app/swagger-ui/index.html "Swagger").

