# CRUD de estudiantes por terminal

Aplicacion Java del proyecto Maven crudEstudiantes. Tiene una unica entrada, Main.java,
y organiza el CRUD en cuatro paquetes: presentacion, logica, datos y modelo.

Los registros viven en memoria y se borran al cerrar el programa. La regla academica
impide eliminar estudiantes menores de 18 anos.

## Requisitos

- JDK 21
- Maven 3.9 o superior para usar los comandos Maven

Comprueba las herramientas desde PowerShell:

~~~powershell
java -version
javac -version
mvn -version
~~~

## Ubicar el proyecto en PowerShell

~~~powershell
Set-Location "C:\UNIVERSIDAD\IngenieriaSoftware\6TO SEMESTRE\G6_Analisis\ChuquiKerly_36838_G6_ADSW\PROYECTO\2. DISEÑO\1.0 Patron de diseño\crudEstudiantes"
~~~

## Ejecutar con Maven

1. Abre PowerShell en la carpeta crudEstudiantes.
2. Compila y abre la aplicacion:

~~~powershell
mvn clean compile exec:java
~~~

## Ejecutar solo con el JDK

Desde la carpeta crudEstudiantes, ejecuta:

~~~powershell
$fuentes = Get-ChildItem -Path .\src\main\java -Recurse -Filter *.java | Select-Object -ExpandProperty FullName
New-Item -ItemType Directory -Force -Path .\target\classes | Out-Null
javac -d .\target\classes $fuentes
java -cp .\target\classes ec.edu.espe.analisis.crudestudiantes.presentacion.Main
~~~

Si PowerShell indica que java o javac no se reconoce, instala el JDK 21 y vuelve
a abrir la terminal. Si mvn no se reconoce, instala Maven o usa la opcion solo JDK.

## Opciones del menu

- 1: registrar estudiante con ID, nombre y edad
- 2: actualizar un estudiante por ID
- 3: eliminar un estudiante por ID
- 4: listar todos los estudiantes
- 0: salir

Si se ingresa texto donde se espera un numero, la aplicacion vuelve a solicitar
un entero. Las validaciones y la regla de edad se aplican en ControlEstudiante.

## Capas

| Paquete | Responsabilidad | Clase principal |
| --- | --- | --- |
| presentacion | Lee y muestra datos en la terminal. | Main |
| logica | Valida entradas y reglas del CRUD. | ControlEstudiante |
| datos | Mantiene la coleccion de estudiantes en memoria. | RepositorioEstudiante |
| modelo | Representa los datos del estudiante. | Estudiante |

El diagrama actualizado esta en diagrama_tres_capas_crud_estudiante.puml.