# Proyecto 5: Laberinto Aleatorio y Camino Mínimo
Generador y resolutor de laberintos en Java utilizando estructuras de datos propias (lista enlazada, pila, cola, árbol binario y tabla hash). 
El programa encuentra el camino más corto entre el inicio y la salida usando BFS y compara con DFS.

# Integrantes
1. Melany Lesmes Yotengo
2. Yency Yuliana España
3. Liney Tatiana Anacona
4. Carlos Mario Bucheli 
5. Karen Daniela Ramirez

# Objetivo del proyecto 
El objetivo es aplicar principios de programación orientada a objetos y estructuras de datos para resolver un problema de búsqueda en un entorno de laberinto. Se evalúan la lógica, completitud, uso de Clean Code y la implementación sin librerías externas.

# Instrucciones para ejecutar el programa
1. Ejecutar el programa desde `VistaConsola.java`.
2. Ingresar dimensiones del laberinto (mínimo 5x5, impares).
3. Ingresar una semilla (Long) o `0` para generar una aleatoria.
4. El programa mostrará:
   - Laberinto generado
   - Camino óptimo (BFS)
   - Comparación con DFS
   - Ranking de mejores tiempos
5. Repetir partidas o finalizar el programa.

#  Estructura del programa 
LABERINTOJUEGO/
│
├── build/                     # Archivos generados automáticamente por NetBeans
├── nbproject/                 # Configuración del proyecto (NetBeans)
├── src/                       # Código fuente principal
│   │
│   ├── configuracion/         # Capa de control y coordinación (Controlador)
│   │   ├── configuracionPrincipal.java   # Orquesta la lógica entre vista y modelo
│   │   └── ResultadoDTO.java              # Objeto de transferencia de datos (ruta + tiempo)
│   │
│   ├── modelo/                # Capa lógica y estructuras de datos (Modelo)
│   │   ├── ArbolBinario.java              # Ranking de mejores soluciones (Top 5)
│   │   ├── Celda.java                     # Representa cada posición del laberinto
│   │   ├── Cola.java                      # Implementación manual para BFS
│   │   ├── Laberinto.java                 # Genera, resuelve y marca caminos
│   │   ├── ListaEnlazada.java             # Guarda la secuencia del camino
│   │   ├── Nodo.java                      # Nodo genérico para lista, pila y cola
│   │   ├── Pila.java                      # Implementación manual para DFS
│   │   └── TablaHash.java                 # Cachea laberintos por semilla
│   │
│   └── vista/                # Capa de presentación (Vista)
│       └── VistaConsola.java              # Interfaz por consola, entrada/salida de datos
│
├── .gitignore                # Exclusión de archivos para control de versiones
└── build.xml                 # Script de compilación (Ant)

