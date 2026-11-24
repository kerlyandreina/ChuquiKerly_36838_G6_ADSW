# CRUD de estudiantes con patrones integrados

Este proyecto reúne Iterator, Mediator y State en una sola aplicación Java. Los tres patrones forman parte del mismo flujo del CRUD; no son tres programas separados.

La carpeta crudEstudiantes original se conserva sin modificaciones. Este proyecto integrado está en una carpeta nueva para poder compararlo con los ejemplos anteriores.

## Ejecutar

Requiere JDK 21 y Maven. Desde esta carpeta, ejecuta:

    mvn clean compile exec:java

Los estudiantes se guardan en memoria mientras la aplicación está abierta. Al salir, los datos se pierden.

## Cómo se conectan los patrones

- Main presenta el menú y solicita las operaciones a través de la interfaz MediadorCrud.
- MediadorCrudEstudiantes recibe esas solicitudes y las coordina con ControlEstudiante, que aplica las reglas del CRUD.
- ControlEstudiante usa el contrato RepositorioEstudiante; RepositorioEstudianteMemoria lo implementa y almacena datos en un LinkedHashMap.
- Para listar, el repositorio crea un IteradorEstudiante; Main lo recorre mediante la interfaz Iterador sin conocer cómo se almacenan los estudiantes.
- Cada Estudiante mantiene un EstadoEstudiante. Su estado decide si permite actualizar sus datos.
- FabricaCrudEstudiantes es el punto de composición que conecta las implementaciones concretas. Main usa el contrato MediadorCrud durante las operaciones.

## Principios SOLID elegidos

Se eligieron **SRP (principio de responsabilidad única)** y **DIP (principio de inversión de dependencias)** porque este CRUD reúne presentación, reglas, almacenamiento y tres patrones. Estos principios ayudan a mantener esas responsabilidades separadas y a evitar que la lógica dependa de una sola forma de persistencia.

### SRP: una responsabilidad principal por clase

En este diseño, Main se ocupa de entrada y salida; MediadorCrudEstudiantes media las solicitudes; ControlEstudiante aplica los casos de uso y las reglas; RepositorioEstudianteMemoria guarda los datos; IteradorEstudiante recorre la colección; y las clases EstadoActivo y EstadoInactivo contienen el comportamiento de cada estado.

**Qué cambia:** ControlEstudiante ya no implementa también el mediador ni crea el repositorio. Cambios en el menú, las reglas del CRUD o la forma de guardar estudiantes se localizan en clases distintas.

**Ventajas en este CRUD:** se entiende mejor qué clase debe cambiar, el mediador no acumula validaciones y la persistencia no se mezcla con las reglas de negocio. Iterator y State también conservan una tarea concreta cada uno.

**Desventajas:** hay más clases y delegaciones. Para un CRUD pequeño, separar mediador, controlador, contrato de repositorio y almacenamiento en memoria aumenta el código inicial.

### DIP: depender de contratos, no de implementaciones

ControlEstudiante recibe por constructor la interfaz RepositorioEstudiante. La implementación RepositorioEstudianteMemoria contiene el LinkedHashMap. Main usa MediadorCrud para las operaciones y Estudiante utiliza la interfaz EstadoEstudiante. FabricaCrudEstudiantes conecta las clases concretas en un solo lugar.

**Qué cambia:** ControlEstudiante ya no ejecuta new RepositorioEstudiante(); por eso, las reglas del CRUD no dependen del almacenamiento en memoria. Se puede agregar otra implementación del repositorio sin cambiar esas reglas, siempre que cumpla el mismo contrato.

**Ventajas en este CRUD:** permite reemplazar el almacenamiento en memoria por archivos o una base de datos con menos cambios; además, las reglas pueden trabajar con cualquier implementación del contrato.

**Desventajas:** los contratos y la inyección por constructor agregan configuración. Si solo se usa una implementación y el proyecto no va a crecer, puede sentirse como complejidad adicional.

La fábrica contiene la selección de las implementaciones de datos y el mediador. ControlEstudiante depende de la abstracción del repositorio, y Main solicita operaciones mediante MediadorCrud. En esta versión, MediadorCrudEstudiantes todavía recibe ControlEstudiante como clase concreta; una abstracción adicional para ese servicio reduciría más el acoplamiento, a costa de otra interfaz. El DIP se aplica aquí en los límites de presentación, almacenamiento y estado, no elimina todas las dependencias concretas del programa.

## Dónde se aplica cada patrón

### Iterator

Se encuentra en logica/Iterador.java, datos/IteradorEstudiante.java y datos/RepositorioEstudianteMemoria.java.

El repositorio crea un recorrido sobre una copia de los estudiantes. La pantalla usa haySiguiente() y siguiente() para mostrarlos. La lista conserva el orden en que se registraron.

**Efecto en el código:** la presentación ya no recibe ni recorre directamente una List; depende de la interfaz del iterador. El iterador guarda una instantánea de los elementos: altas o eliminaciones posteriores no cambian ese recorrido, aunque los cambios a los campos de un estudiante que ya estaba en la instantánea sí pueden verse.

**Ventajas:** oculta la estructura interna del repositorio, separa el recorrido de la presentación y permite cambiar la colección sin cambiar el menú.

**Desventajas:** agrega una interfaz y una clase para una colección sencilla; crear la instantánea requiere memoria y tiempo proporcionales al número de estudiantes.

### Mediator

Se encuentra en logica/MediadorCrud.java y logica/MediadorCrudEstudiantes.java. Los comentarios PATRÓN MEDIATOR señalan sus responsabilidades.

Main usa el contrato MediadorCrud para solicitar operaciones. MediadorCrudEstudiantes recibe esas solicitudes y las coordina con ControlEstudiante. El controlador aplica reglas y usa el contrato RepositorioEstudiante; así, la pantalla no accede directamente al repositorio ni ejecuta las reglas del controlador. Para mostrar el listado, Main sí conoce el tipo Estudiante que recibe a través del iterador.

**Efecto en el código:** la interfaz de usuario queda desacoplada del controlador concreto. Se pueden sustituir o adaptar las operaciones del mediador sin reescribir el menú. La construcción de los componentes queda centralizada en FabricaCrudEstudiantes.

**Ventajas:** concentra el acceso al CRUD, reduce dependencias directas entre presentación y datos, y facilita reemplazar la implementación o crear una alternativa para la interfaz.

**Desventajas:** el mediador puede crecer demasiado si se agregan muchas reglas; además, para una aplicación tan pequeña, el contrato y la capa adicional aumentan la cantidad de código.

### State

Se encuentra en modelo/EstadoEstudiante.java, modelo/EstadoActivo.java, modelo/EstadoInactivo.java y modelo/Estudiante.java. El menú permite cambiar el estado de cada estudiante.

Los estudiantes empiezan como activos. Un estudiante activo puede actualizarse; uno inactivo rechaza la actualización. Al cambiarlo nuevamente a activo, vuelve a permitirla. El estado también aparece al listar estudiantes.

**Efecto en el código:** la regla de actualización vive en cada estado, en lugar de acumular condicionales sobre el estado dentro del controlador. El controlador cambia el objeto de estado cuando el usuario lo solicita.

**Ventajas:** cada estado mantiene su propio comportamiento, hace explícitas las transiciones y permite agregar estados con reglas distintas.

**Desventajas:** requiere más clases y una transición explícita; para solo dos estados y una regla, puede ser más código que un condicional simple. En esta versión, el estado existe en memoria y se pierde al cerrar el programa.

## Flujo resumido

1. Main recibe una opción y la envía mediante MediadorCrud.
2. MediadorCrudEstudiantes la dirige a ControlEstudiante.
3. ControlEstudiante valida la operación y usa el contrato RepositorioEstudiante o el estado del estudiante.
4. Para listar, RepositorioEstudianteMemoria entrega un iterador y Main recorre sus elementos.
5. Para actualizar, Estudiante delega la decisión en su estado activo o inactivo.
