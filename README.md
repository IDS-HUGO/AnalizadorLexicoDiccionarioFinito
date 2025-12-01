# ANALIZADOR LÉXICO - PRÁCTICA 1

**Universidad Politécnica de Chiapas**  
Lenguajes y Autómatas - Cuatrimestre 7  
Proyecto Final: Analizador Léxico para un Diccionario Finito

---

## 📋 DESCRIPCIÓN

Este proyecto implementa la **primera fase de un compilador**: un **Analizador Léxico (Scanner)** que procesa un texto de entrada y lo divide en tokens, clasificándolos en:

- ✅ **Palabras Clave** (KW): Palabras reservadas del lenguaje (si, sino, mientras, etc.)
- ✅ **Identificadores**: Nombres válidos de variables según un patrón regex
- ❌ **Errores Léxicos**: Cadenas que no cumplen ninguna regla válida

---

## 📁 ESTRUCTURA DEL PROYECTO

```
Practica1/
├── Token.java                  # Clase para representar tokens
├── DFA.java                    # Autómata Finito Determinista (TRIE)
├── AnalizadorLexico.java       # Coordinador del análisis léxico
├── Main.java                   # Punto de entrada del programa
├── diccionario.txt             # Base de datos de palabras clave
├── texto_entrada.txt           # Archivo de prueba
├── tokens_salida.txt           # Resultado del análisis (generado)
├── REPORTE_PRACTICA.md         # Reporte completo de la práctica
├── DIAGRAMA_DFA.txt            # Diagrama visual del DFA
├── FLUJO_PROGRAMA.md           # Documentación del flujo y arquitectura
└── README.md                   # Este archivo
```

---

## 🚀 COMPILACIÓN Y EJECUCIÓN

### Requisitos

- **Java Development Kit (JDK)** 8 o superior
- Sistema operativo: Windows, Linux o macOS

### Compilar

```cmd
cd c:\Users\Hugo8\Desktop\Practica1
javac -encoding UTF-8 *.java
```

### Ejecutar

```cmd
java Main
```

### Salida Esperada

```
===============================================================
        ANALIZADOR LEXICO - PRACTICA 1
        Universidad Politecnica de Chiapas
        Lenguajes y Automatas
===============================================================

=== Cargando Diccionario ===
Diccionario cargado: 8 palabras clave

=== Analizando Texto de Entrada ===
Análisis completado: 36 tokens identificados

=== Archivo de Salida Generado ===
Archivo creado: tokens_salida.txt

=== Estadísticas del Análisis ===
Total de tokens: 36
  Palabras Clave: 16
  Identificadores: 15
  Errores Léxicos: 5
```

---

## 📖 DOCUMENTACIÓN

### 1. **REPORTE_PRACTICA.md**
Reporte completo que incluye:
- Código fuente y arquitectura
- Diseño del DFA con diagramas
- Descripción detallada de expresiones regulares
- Archivo de salida con análisis
- Informe técnico (justificación de HashMap y simulación del DFA)
- Conclusiones

### 2. **DIAGRAMA_DFA.txt**
Diagrama visual completo del Autómata Finito Determinista:
- Representación gráfica de todos los estados y transiciones
- Tabla de transiciones completa
- Ejemplos paso a paso de reconocimiento
- Propiedades del DFA

### 3. **FLUJO_PROGRAMA.md**
Documentación técnica detallada:
- Arquitectura del sistema
- Componentes principales
- Flujo de ejecución completo
- Fundamentos teóricos
- Análisis de complejidad

---

## 🔍 PALABRAS CLAVE RECONOCIDAS

| Palabra Clave | Token          |
|---------------|----------------|
| `si`          | KW_SI          |
| `sino`        | KW_SINO        |
| `mientras`    | KW_MIENTRAS    |
| `repite`      | KW_REPITE      |
| `hasta`       | KW_HASTA       |
| `inicio`      | KW_INICIO      |
| `fin`         | KW_FIN         |
| `imprimir`    | KW_IMPRIMIR    |

---

## ✏️ EXPRESIÓN REGULAR PARA IDENTIFICADORES

```regex
^[a-z][a-z0-9_]*$
```

**Descripción:**
- Debe comenzar con letra minúscula `[a-z]`
- Puede continuar con letras, números o guiones bajos `[a-z0-9_]*`

**Ejemplos válidos:**
- `contador`, `suma1`, `valor_total`, `x`, `area2_temp`

**Ejemplos inválidos:**
- `Contador` (mayúscula), `1suma` (empieza con número), `_valor` (empieza con guion bajo)

---

## 🧪 EJEMPLO DE USO

### Entrada (texto_entrada.txt):
```
si contador es_mayor Que diez inicio mientras repite imprimir saludo hasta contador fin
```

### Salida (tokens_salida.txt):
```
Token                Lexema
========================================
KW_SI                si
IDENTIFICADOR        contador
IDENTIFICADOR        es_mayor
ERROR_LEXICO         Que
IDENTIFICADOR        diez
KW_INICIO            inicio
KW_MIENTRAS          mientras
KW_REPITE            repite
KW_IMPRIMIR          imprimir
IDENTIFICADOR        saludo
KW_HASTA             hasta
IDENTIFICADOR        contador
KW_FIN               fin
```

---

## 🏗️ ARQUITECTURA

### Componentes Principales

1. **Token**: Representa un par (tipo, lexema)
2. **DFA**: Autómata Finito para reconocer palabras clave
3. **AnalizadorLexico**: Coordina el análisis y clasificación
4. **Main**: Punto de entrada y flujo de ejecución

### Flujo de Clasificación

```
Palabra de entrada
       ↓
   DFA.reconocer()
       ↓
   ¿Es palabra clave?
    /          \
   SÍ          NO
   ↓            ↓
KW_XXX    ¿Cumple regex?
            /        \
           SÍ        NO
           ↓          ↓
     IDENTIFICADOR  ERROR_LEXICO
```

---

## 📊 COMPLEJIDAD COMPUTACIONAL

| Operación               | Complejidad | Explicación                       |
|-------------------------|-------------|-----------------------------------|
| Cargar diccionario      | O(n×k)      | n palabras, k = long. promedio    |
| Construir DFA           | O(n×k)      | Insertar n palabras en TRIE       |
| Reconocer palabra (DFA) | O(k)        | k = longitud de la palabra        |
| Validar identificador   | O(k)        | Regex matcher                     |
| Analizar texto completo | O(m×k)      | m palabras en el texto            |

**Total:** O((n+m)×k) - Eficiente para textos largos

---

## 🎯 FUNDAMENTOS TEÓRICOS

### Autómata Finito Determinista (DFA)

**Definición Formal:**
```
M = (Q, Σ, δ, q₀, F)
```

- **Q**: Conjunto de estados
- **Σ**: Alfabeto {a, c, e, f, h, i, m, n, o, p, r, s, t}
- **δ**: Función de transición Q × Σ → Q
- **q₀**: Estado inicial
- **F**: Estados finales

### Implementación como TRIE

El DFA está implementado como un **árbol de prefijos (TRIE)** para:
- ✅ Compartir prefijos comunes (ej: "si" y "sino")
- ✅ Eficiencia espacial y temporal
- ✅ Búsqueda en O(k)

---

## 📚 TECNOLOGÍAS Y CONCEPTOS

### Lenguajes y Autómatas
- Autómatas Finitos Deterministas
- Lenguajes Regulares
- Expresiones Regulares
- Análisis Léxico

### Estructuras de Datos
- HashMap (búsqueda O(1))
- TRIE (árbol de prefijos)
- Listas dinámicas

### Programación Orientada a Objetos
- Encapsulación
- Modularidad
- Abstracción de datos

---

## 👥 EQUIPO DE DESARROLLO

**Integrantes:**
- [NOMBRE COMPLETO - MATRÍCULA]
- [NOMBRE COMPLETO - MATRÍCULA]
- [NOMBRE COMPLETO - MATRÍCULA]

**Instructor:**
- [NOMBRE DEL PROFESOR]

**Fecha de Entrega:**
- 5 de Diciembre de 2025

---

## 📝 LICENCIA Y NOTAS

Este proyecto es parte del curso de **Lenguajes y Autómatas** en la **Universidad Politécnica de Chiapas**.

Desarrollado con fines educativos como implementación práctica de conceptos teóricos de compiladores.

---

## 📞 CONTACTO Y SOPORTE

Para preguntas o comentarios sobre este proyecto:
- Revisar la documentación completa en `REPORTE_PRACTICA.md`
- Consultar el diagrama del DFA en `DIAGRAMA_DFA.txt`
- Ver el flujo detallado en `FLUJO_PROGRAMA.md`

---

**Universidad Politécnica de Chiapas**  
Ingeniería en Desarrollo de Software  
Lenguajes y Autómatas - Cuatrimestre 7  
Diciembre 2025
