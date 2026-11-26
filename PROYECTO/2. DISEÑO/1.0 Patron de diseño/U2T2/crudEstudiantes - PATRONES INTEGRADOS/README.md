# CRUD de estudiantes con Mediator, State e Iterator

Aplicación de escritorio Java Swing para registrar, actualizar, eliminar y listar estudiantes, además de cambiar su estado entre activo e inactivo. La tabla inicia vacía y los registros se guardan solo en memoria durante la ejecución; al cerrar la ventana, se pierden. El botón **Cargar datos de ejemplo** agrega tres registros predefinidos cuando se necesita probar la interfaz.

## Ejecutar

Requiere JDK 21 y Maven. Desde esta carpeta:

    mvn compile exec:java

## Patrones aplicados

- **Mediator:** `VentanaCrudEstudiantes` envía las solicitudes mediante `MediadorCrud`. `MediadorCrudEstudiantes` las recibe y las delega a `ControlEstudiante`, que ejecuta las reglas del CRUD.
- **State:** `Estudiante` conserva un `EstadoEstudiante`. `EstadoActivo` permite actualizar nombre y edad; `EstadoInactivo` rechaza la actualización. `ControlEstudiante.cambiarEstado` selecciona el estado solicitado.
- **Iterator:** `ControlEstudiante.crearIterador` crea `IteradorEstudiante`. `VentanaCrudEstudiantes` recorre los resultados con `haySiguiente()` y `siguiente()` para llenar la tabla sin acceder al mapa que conserva los registros.

## Interfaz

La ventana presenta campos de ID, nombre y edad, acciones para el CRUD, cambio de estado y una tabla seleccionable. Seleccionar un estudiante copia sus datos al formulario. La tabla permanece vacía al abrir la aplicación; se puede registrar información manualmente o cargar datos de ejemplo con el botón correspondiente. El estado de los registros no se guarda en disco ni en una base de datos.

## Reglas del CRUD

- El ID y la edad deben ser mayores que cero.
- El nombre no puede estar vacío y el ID debe ser único.
- Solo se puede actualizar un estudiante activo.
- No se permite eliminar estudiantes menores de 18 años.
- La colección conserva el orden de inserción y el iterador trabaja sobre una copia superficial de sus elementos.

## Diagramas

Los diagramas PlantUML que corresponden a las clases y relaciones actuales están en `diagramas/`:

- `01_Iterator_Integrado.puml`
- `02_Mediator_Integrado.puml`
- `03_State_Integrado.puml`
