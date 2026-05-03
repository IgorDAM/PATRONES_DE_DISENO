# Proyecto Patrones de Diseño

## Descripción

Este es un **proyecto Java independiente** que acompaña al manual didáctico sobre patrones de diseño. Contiene **6 ejemplos ejecutables** de patrones presentes en el backend REALPRINT, organizados en las tres categorías clásicas.

## Estructura

```
ProyectoPatronesDiseno/
├── src/main/java/com/patrones/
│   ├── creacionales/
│   │   ├── singleton/
│   │   │   ├── ConfigManager.java          (Clase Singleton)
│   │   │   └── SingletonExample.java       (Ejemplo ejecutable)
│   │   └── builder/
│   │       ├── Order.java                  (Clase con @Builder)
│   │       └── BuilderExample.java         (Ejemplo ejecutable)
│   ├── estructurales/
│   │   ├── facade/
│   │   │   ├── OrderProcessingFacade.java  (Facade + componentes)
│   │   │   └── FacadeExample.java          (Ejemplo ejecutable)
│   │   └── dto_adapter/
│   │       ├── PedidoAdapter.java          (Entity + DTO + Mapper)
│   │       └── DTOAdapterExample.java      (Ejemplo ejecutable)
│   ├── comportamiento/
│   │   ├── strategy/
│   │   │   ├── StrategyClasses.java        (Strategy + handlers)
│   │   │   └── StrategyExample.java        (Ejemplo ejecutable)
│   │   └── template_method/
│   │       ├── ErrorHandler.java           (Template Method base)
│   │       └── TemplateMethodExample.java  (Ejemplo ejecutable)
│   └── MainPrincipal.java                  (Ejecuta todos los ejemplos)
├── pom.xml                                 (Configuración Maven)
└── README.md                               (Este archivo)
```

## Patrones Incluidos

### 1. **Singleton** (Creacional)
**Ubicación:** `com.patrones.creacionales.singleton`

Garantiza una única instancia de `ConfigManager` en toda la aplicación.

```bash
mvn exec:java -Dexec.mainClass="com.patrones.creacionales.singleton.SingletonExample"
```

### 2. **Builder** (Creacional)
**Ubicación:** `com.patrones.creacionales.builder`

Construye objetos complejos (`Order`) paso a paso usando interfaz fluida.

```bash
mvn exec:java -Dexec.mainClass="com.patrones.creacionales.builder.BuilderExample"
```

### 3. **Facade** (Estructural)
**Ubicación:** `com.patrones.estructurales.facade`

Simplifica el acceso a un subsistema complejo (`OrderProcessingFacade`).

```bash
mvn exec:java -Dexec.mainClass="com.patrones.estructurales.facade.FacadeExample"
```

### 4. **DTO/Adapter** (Estructural)
**Ubicación:** `com.patrones.estructurales.dto_adapter`

Convierte entre `PedidoEntity` (BD) y `PedidoDTO` (API).

```bash
mvn exec:java -Dexec.mainClass="com.patrones.estructurales.dto_adapter.DTOAdapterExample"
```

### 5. **Strategy** (Comportamiento)
**Ubicación:** `com.patrones.comportamiento.strategy`

Implementa estrategias de autorización intercambiables (ADMIN vs. CLIENTE).

```bash
mvn exec:java -Dexec.mainClass="com.patrones.comportamiento.strategy.StrategyExample"
```

### 6. **Template Method** (Comportamiento)
**Ubicación:** `com.patrones.comportamiento.template_method`

Define un esqueleto consistente para manejar diferentes tipos de errores.

```bash
mvn exec:java -Dexec.mainClass="com.patrones.comportamiento.template_method.TemplateMethodExample"
```

## Cómo Ejecutar

### Opción 1: Ejecutar TODOS los ejemplos a la vez

```bash
mvn clean compile exec:java -Dexec.mainClass="com.patrones.MainPrincipal"
```

Este comando ejecutará los 6 ejemplos en secuencia con un resumen final.

### Opción 2: Ejecutar un ejemplo específico

Cambia la clase principal según el patrón:

```bash
# Singleton
mvn exec:java -Dexec.mainClass="com.patrones.creacionales.singleton.SingletonExample"

# Builder
mvn exec:java -Dexec.mainClass="com.patrones.creacionales.builder.BuilderExample"

# Facade
mvn exec:java -Dexec.mainClass="com.patrones.estructurales.facade.FacadeExample"

# DTO/Adapter
mvn exec:java -Dexec.mainClass="com.patrones.estructurales.dto_adapter.DTOAdapterExample"

# Strategy
mvn exec:java -Dexec.mainClass="com.patrones.comportamiento.strategy.StrategyExample"

# Template Method
mvn exec:java -Dexec.mainClass="com.patrones.comportamiento.template_method.TemplateMethodExample"
```

### Opción 3: Compilar y ejecutar con Java

```bash
# Compilar
mvn clean compile

# Ejecutar
java -cp target/classes com.patrones.MainPrincipal
```

## Requisitos

- **Java 11+**
- **Maven 3.6+**
- **Lombok** (ya incluido en `pom.xml`)

## Contenido de Cada Ejemplo

Cada ejemplo incluye:

1. **Clases del patrón** - Implementación didáctica
2. **Demostración en consola** - Muestra cómo funciona
3. **Explicaciones inline** - Comentarios en el código
4. **Casos de uso reales** - Basados en REALPRINT

### Ejemplo de Salida

```
=== PATRÓN SINGLETON ===

1. Primera llamada a getInstance():
[Singleton] ConfigManager inicializado (UNA SOLA VEZ)
[Singleton] Configuración del sistema...
  - App Name: PatronesApp
  - Version: 1.0.0
  - Database: localhost:5432

2. Segunda llamada a getInstance():
[Singleton] Se reutiliza la misma instancia (sin inicialización)
[Singleton] Configuración del sistema...
  - App Name: PatronesApp
  - Version: 1.0.0
  - Database: localhost:5432

3. Verificación de identidad:
¿config1 == config2? true
...

✅ Conclusión: Solo existe UNA instancia de ConfigManager
```

## Relación con REALPRINT

Estos patrones NO son ejemplos inventados. Están basados en el código real de REALPRINT:

| Patrón | Ejemplo Aquí | En REALPRINT |
|--------|-------------|-------------|
| **Singleton** | `ConfigManager` | `@Service` anotaciones (FileStorageService, PedidoService) |
| **Builder** | `Order` con Lombok | DTOs y entidades con `@Builder` (PedidoDTO, LoginResponse) |
| **Facade** | `OrderProcessingFacade` | `PedidoService`, `AuthService` |
| **DTO/Adapter** | `PedidoMapper` | `PedidoMapper.java` real del proyecto |
| **Strategy** | `AuthorizationStrategy` | `SecurityRulesService.java` |
| **Template Method** | `ErrorHandler` | `GlobalExceptionHandler.java` |

## Notas Importantes

- **Código didáctico:** Los ejemplos son simplificados para claridad. El código de REALPRINT es más complejo.
- **Sin dependencias externas:** Excepto Lombok, todos los ejemplos usan Java puro.
- **Ejecutables:** Todos los ejemplos producen output en consola para demostración.
- **Documentación:** Cada clase está comentada explicando el patrón.

## Próximos Pasos

1. Ejecuta los ejemplos
2. Lee el código y los comentarios
3. Consulta el Manual_Patrones_Diseño.md para la teoría completa
4. Modifica los ejemplos (ej: agrega nueva estrategia, handler, etc.)
5. Vincula con el código de REALPRINT para ver patrones en contexto real

## Autor

Proyecto educativo para la tarea optativa "Manual de Patrones de Diseño en Java"  
Curso: 2º DAM - Programación Orientada a Objetos  
Año: 2026

---

**¡Diviértete explorando patrones de diseño!**
