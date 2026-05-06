# 🎯 GUÍA DE DEFENSA - Cómo Presentar Tu Trabajo

> Recomendaciones prácticas para defender exitosamente tu manual y proyecto de patrones de diseño.

---

## 🎬 Estructura de la Presentación (15-20 minutos)

### Parte 1: Introducción Rápida (2 min)

**Qué debes comunicar:**
- Tu nombre y el proyecto
- Qué son patrones de diseño (1 frase clara)
- Por qué importan (contexto REALPRINT)
- Qué vas a mostrar (6 patrones, 3 categorías)

**Forma sugerida:**
> "Soy [Nombre], y he creado un manual educativo sobre 6 patrones de diseño presentes en el backend REALPRINT. Los patrones de diseño son soluciones probadas a problemas comunes en programación. Voy a mostrar cómo 2 patrones creacionales, 2 estructurales y 2 de comportamiento cumplen un papel específico en nuestra aplicación."

---

### Parte 2: Los 6 Patrones (12-15 min)

**Formato para CADA patrón (2 min por patrón):**

1. **30 segundos - Qué es**
   > "[Nombre] es un patrón de [categoría] que resuelve..."

2. **30 segundos - Problema que resuelve**
   > "Sin este patrón, tendríamos... [ejemplo de código desordenado]"
   > "Con este patrón, simplemente... [código limpio]"

3. **1 minuto - Demostración/Código**
   - Muestra el archivo `.java` del proyecto
   - Ejecuta el ejemplo: `mvn exec:java -Dexec.mainClass="..."`
   - Señala la parte clave del código
   - Muestra la salida

4. **30 segundos - En REALPRINT**
   > "En REALPRINT, lo usamos así: [mostrar clase real]"

---

## 📋 Los 6 Patrones - Guion de Defensa

### 1️⃣ SINGLETON (Creacional)

**30 seg - Qué es:**
> "Singleton garantiza que una clase tenga solo UNA instancia en toda la aplicación. Tiene un método estático `getInstance()` que devuelve siepre la misma instancia."

**30 seg - Problema:**
> "Sin Singleton, si creo 10 instancias de `FileStorageService`, cada una maneja archivos por su lado. Si una sube un archivo y otra lo borra, hay inconsistencias."

**1 min - Demo:**
```java
// Mostrar ConfigManager.java
ConfigManager config1 = ConfigManager.getInstance();  // Primera vez → se crea
ConfigManager config2 = ConfigManager.getInstance();  // Segunda vez → devuelve la misma
// config1 == config2 → true
```

**30 seg - En REALPRINT:**
> "En REALPRINT, los `@Service` son Singletons. Spring los crea UNA sola vez y los inyecta en todos lados."

---

### 2️⃣ BUILDER (Creacional)

**30 seg - Qué es:**
> "Builder permite construir objetos complejos paso a paso con una interfaz fluida. Evita constructores gigantes con 10+ parámetros."

**30 seg - Problema:**
> "Un Pedido tiene 15 campos. Si usas constructor: `new Order(id, cliente, servicio, desc, cant, fecha, ... null, null, null)` — ILEGIBLE."

**1 min - Demo:**
```java
// Mostrar Order.java con @Builder
Order order = Order.builder()
    .id(1L)
    .clientName("Juan")
    .service("Serigrafía")
    .quantity(100)
    .build();
    // Claro qué significa cada campo
```

**30 seg - En REALPRINT:**
> "En REALPRINT usamos `@Builder` de Lombok es TODOS los DTOs. Por ejemplo, `PedidoDTO.builder()...` es mucho más legible que un constructor con 12 parámetros."

---

### 3️⃣ FACADE (Estructural)

**30 seg - Qué es:**
> "Facade simplifica acceso a un subsistema complejo. El cliente llama a UN método que oculta la complejidad interna."

**30 seg - Problema:**
> "Procesar un pedido requiere: validar, guardar, notificar, auditar. Sin Facade, el controlador hace todo esto explícitamente. Es caótico."

**1 min - Demo:**
```java
// Mostrar OrderProcessingFacade.java
// Dentro:
// - OrderValidator
// - OrderRepository
// - NotificationService
// - AuditService
//
// Pero el cliente solo ve:
facade.processOrder("Juan", 100);  // Un solo método
```

**30 seg - En REALPRINT:**
> "`PedidoService` es una Facade. El controlador llama a `pedidoService.findById()` sin saber que internamente valida, busca en BD, maneja excepciones,etc."

---

### 4️⃣ DTO/ADAPTER (Estructural)

**30 seg - Qué es:**
> "Adapter (en forma de DTO Mapper) convierte entre formatos incompatibles. Por ejemplo, entidades de BD a DTOs de API REST."

**30 seg - Problema:**
> "La BD guarda Pedido con estado ENUM `PENDIENTE`. La API devuelve JSON con `"pendiente"` en minúsculas. Necesitas un traductor."

**1 min - Demo:**
```java
// Mostrar PedidoEntity vs PedidoDTO
// PedidoMapper.java:
// toDTO(Entity) → estado.name().toLowerCase()
// toEntity(DTO) → PedidoEstado.valueOf(estado.toUpperCase())
```

**30 seg - En REALPRINT:**
> "`PedidoMapper` es el Adapter. Convierte Pedido (entidad con passwordHash) a PedidoDTO (solo datos públicos)."

---

### 5️⃣ STRATEGY (Comportamiento)

**30 seg - Qué es:**
> "Strategy encapsula familias de algoritmos intercambiables. Permite cambiar el comportamiento en tiempo de ejecución según el contexto."

**30 seg - Problema:**
> "Autorización: ADMIN puede todo, CLIENTE solo sus datos. Sin Strategy, tienes if-else gigantes repetidos en cada método."

**1 min - Demo:**
```java
// Mostrar Strategy implementaciones:
// AdminStrategy - permite todo
// ClienteStrategy - solo sus datos
// SecurityService - selecciona automaticamente
```

**30 seg - En REALPRINT:**
> "`@PreAuthorize("@securityRules.canReadPedido()")` usa Strategy. Detrás, `SecurityRulesService` elige la estrategia correcta según el rol."

---

### 6️⃣ TEMPLATE METHOD (Comportamiento)

**30 seg - Qué es:**
> "Template Method define el esqueleto de un algoritmo en una clase base. Los pasos específicos varían en subclases, pero la estructura es igual para todos."

**30 seg - Problema:**
> "Manejar errores: siempre registras, construyes respuesta, envías. Sin Template Method, es código duplicado en cada handler."

**1 min - Demo:**
```java
// Mostrar ErrorHandler.java (abstracta)
// Subclases concretas:
// - NotFoundErrorHandler (404)
// - UnauthorizedErrorHandler (401)
// - InternalServerErrorHandler (500)
// Todas siguen el mismo template
```

**30 seg - En REALPRINT:**
> "`GlobalExceptionHandler` usa Template Method. Todos los `@ExceptionHandler` siguen esquema: log → crear respuesta → devolver."

---

## 🔄 Si Te Piden Que Modifiques Algo en Vivo

**Ejemplos típicos:**

### 1. "Añade una nueva estrategia GUEST a Strategy"

```java
public class GuestStrategy implements AuthorizationStrategy {
    @Override
    public boolean isAuthorized(Long userId, Long resourceOwnerId) {
        return false;  // GUEST no puede acceder a nada
    }
}

// En SecurityService:
if (user.getRole().equals("GUEST")) {
    return new GuestStrategy();
}
```

### 2. "Añade un handler 400 (Bad Request) a Template Method"

```java
public class BadRequestHandler extends ErrorHandler {
    @Override
    public void buildResponse() {
        response = "HTTP 400: Bad Request";
    }
}

// En TemplateMethodExample:
// errorHandlerList.add(new BadRequestHandler());
```

### 3. "Añade un campo obligatorio a Order en Builder"

```java
@Getter
@ToString
@Builder
public class Order {
    // ...
    private String reference;  // Nuevo campo
}

// En BuilderExample:
Order order = Order.builder()
    .reference("ORD-001")  // Nuevo
    .clientName("...")
    .build();
```

---

## 🗣️ Frases Clave Para Demostrar Comprensión

**Si preguntan: "¿Por qué Singleton y no solo variables globales?"**
> "Las variables globales son peligrosas en multihilo. Singleton garantiza sincronización automática y control de inicialización. Además, puedes testear un Singleton inyectándolo; una variable global no."

**Si preguntan: "¿Por qué DTO/Adapter en lugar de exponer la entidad directamente?"**
> "La entidad contiene la contraseña (passwordHash). Exponerla es un riesgo de seguridad. El DTO solo tiene datos públicos. Además, si cambio la BD, solo cambio el Mapper, no la API."

**Si preguntan: "¿Cuándo NO usar un patrón?"**
> "Cuando hace el código más complejo que el beneficio. Si tienes una clase simple con 2 campos, no necesitas Builder. Si tienes un solo algoritmo, no necesitas Strategy. Los patrones no son dogmas."

**Si preguntan: "¿Este es código tuyo o de IA?"**
> "Este código lo escribí yo basándome en la lectura del código real de REALPRINT. IA me ayudó a sugerir mejoras en redacción y formatos visuales, pero la arquitectura, decisiones de diseño, ejemplos contextualizados son de autoría personal. Puedo modificarlo en directo para demostrarlo."

---

## 📊 Presentación del Manual

**Qué mostrar:**

1. **Portada:** Muestra marca profesional
2. **Índice:** Demuestra estructura organizada
3. **Apartados clave de cada patrón:**
   - Problema sin patrón (código desordenado)
   - Solución con patrón (código limpio)
   - Diagramas UML
4. **Declaración de LLMs:** Demuestra honestidad

---

## ⏱️ Gestión del Tiempo

| Sección | Duración |
|---------|----------|
| Introducción | 2 min |
| Singleton | 2 min |
| Builder | 2 min |
| Facade | 2 min |
| DTO/Adapter | 2 min |
| Strategy | 2 min |
| Template Method | 2 min |
| Preguntas/Modificaciones | 3-5 min |
| **TOTAL** | **15-20 min** |

---

## 🎓 Checklist Pre-Presentación

- [ ] Tengo el proyecto clonado/compilado localmente
- [ ] Puedo ejecutar `mvn exec:java` sin errores
- [ ] Tengo el manual PDF o abierto en navegador
- [ ] Conocerzo cada archivo `.java` del proyecto
- [ ] Entiendo POR QUÉ cada patrón está en el código
- [ ] Puedo explicar en 30 segundos cada patrón
- [ ] Puedo modificar código en vivo (AddStrategy, etc.)
- [ ] He practicado el guion de los 6 patrones
- [ ] Tengo REALPRINT codebase disponible para referencias
- [ ] He leído la declaración de LLMs y puedo defenderla

---

## 💡 Tips Finales

1. **Sé honesto sobre lo que aprendiste y lo que aún no sabes**
   > Mejor: "No, aún no he explorado el patrón Factory Method, ese sería el siguiente paso"
   > Que: "Claro, factory method es..."

2. **Muestra entusiasmo programador**
   > Los evaluadores aprecian cuando ves patrones útiles, no como un trabajo obligatorio

3. **Conecta con REALPRINT siempre**
   > Cada patrón teórico tiene un ejemplo práctico = mucho más creíble

4. **No memorices el discurso**
   > Practica hasta que fluya naturalmente. Los evaluadores detectan memorización.

5. **Sé preparado para no saber todo**
   > Está 100% bien decir "Buena pregunta, aota eso para explorar después"

---

## 🎬 El Guion Completo en 5 Minutos (Versión Express)

Si te piden una presentación super rápida:

> "Los patrones de diseño son soluciones probadas a problemas comunes. He documentado 6 presentes en REALPRINT en 3 categorías:
> 
> **Creacionales:** Singleton (instancia única vía @Service) y Builder (Lombok para objetos complejos).
> 
> **Estructurales:** Facade (servicios que simplifican, como PedidoService) y Adapter (PedidoMapper convierte BD ↔ API).
> 
> **Comportamiento:** Strategy (SecurittyRulesService elige según rol) y Template Method (GlobalExceptionHandler maneja errores consistentemente).
> 
> Todos compilan, se ejecutan, y están en el proyecto. El manual explica cada uno con problema → solución."

---

**Buena suerte en tu presentación. 👨‍🎓**

---

*Escrito por alguien que pasó por esto y vivió para contarlo*  
*Abril 2026*


