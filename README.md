**Implementación de Karate Framework**

Más info : https://github.com/intuit/karate

Features:
- Ejecutar consultas a APIs rest
- Ejecutar quieries e inserts a base de datos
- Configurar Profiles para correr en diferentes ambientes
- Generar JWT tokens con una Key

Para correr los tests por consola [qa/uat]

```
    mvn -Dtest=RunKarate test

    mvn -Dtest=RunKarate -Denv=qa test

    mvn -Dtest=Run -Denv=qa -DreportPath=/tmp/report/prueba2 test
```

Si quieres configurar path de reporte y otras opciones de karate, el path seria algo asi :

```
    mvn -Dtest=RunKarate test -Dthreads=10 -DreportPath=$HOME/report -D"karate.options==--features /karate/feature --tags ~@Ignore"
```
Solo funciona en paralelo y con reporte de cucumber cuando se ejecuta desde la clase en intellij
