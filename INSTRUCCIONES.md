# Instrucciones de Ejecución - Diez Mil

Este documento detalla los pasos para compilar y ejecutar el proyecto Diez Mil con la arquitectura RMI-MVC utilizando la consola de comandos o los accesos rápidos provistos.

## Paso 1: Compilar el Proyecto

Antes de ejecutar el juego por primera vez o tras realizar modificaciones en el código fuente, debes compilar los archivos `.java`.

- **En Windows**: 
  Haz doble clic en el archivo **`compile.bat`** ubicado en la carpeta raíz del proyecto.
- **Manualmente (desde una terminal de comandos en la raíz del proyecto)**:
  ```powershell
  if (!(Test-Path out)) { New-Item -ItemType Directory -Path out }
  Get-ChildItem -Path src -Filter *.java -Recurse | Resolve-Path -Relative | Out-File -Encoding ASCII sources.txt
  cmd /c "javac -cp LibreriaRMIMVC.jar -d out @sources.txt"
  Remove-Item sources.txt
  ```

Este proceso generará las clases compiladas dentro de la carpeta `/out`.

---

## Paso 2: Levantar el Servidor

El servidor debe iniciarse primero para registrar los servicios remotos RMI y permitir la conexión de los jugadores.

- **En Windows**:
  Haz doble clic en **`run-servidor.bat`** en la carpeta raíz.
- **Manualmente (consola)**:
  ```cmd
  java -cp "out;LibreriaRMIMVC.jar" Servidor
  ```

*Nota: El servidor se levantará de forma predeterminada en la dirección local `127.0.0.1` en el puerto `40000`.*

---

## Paso 3: Iniciar los Clientes (Jugadores)

Una vez que el servidor esté activo, puedes iniciar los clientes para cada jugador. Puedes mezclar clientes gráficos y de consola en la misma partida.

### Opción A: Cliente con Interfaz Gráfica (Recomendado)
- **En Windows**:
  Haz doble clic en **`run-cliente-grafico.bat`**.
- **Manualmente (consola)**:
  ```cmd
  java -cp "out;LibreriaRMIMVC.jar" Cliente
  ```
  *(Se conecta desde el puerto `40001` local al puerto `40000` del servidor).*

### Opción B: Cliente en Consola
- **En Windows**:
  Haz doble clic en **`run-cliente-consola.bat`**.
- **Manualmente (consola)**:
  ```cmd
  java -cp "out;LibreriaRMIMVC.jar" Cliente2
  ```
  *(Se conecta desde el puerto `40002` local al puerto `40000` del servidor).*

---

## Flujo de Juego Típico (Multijugador Local)

1. Abre **`run-servidor.bat`** (mantenlo abierto en segundo plano).
2. Abre una ventana de **`run-cliente-grafico.bat`** e ingresa el nombre para el *Jugador 1*.
3. Abre una segunda ventana de **`run-cliente-grafico.bat`** (o de consola) e ingresa el nombre para el *Jugador 2*.
4. Desde el menú principal del *Jugador 1*, presiona **Nueva Partida**.
5. ¡Listo! El turno de juego comenzará alternadamente en las pantallas de ambos jugadores.
