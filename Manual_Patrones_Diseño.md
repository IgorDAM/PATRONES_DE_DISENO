<div align="center">

---

# Manual de Patrones de Diseño en Java
## Análisis e implementación en el proyecto REALPRINT

**Autor:** Igor Sánchez  
**Fecha:** Abril 2026  
**Curso:** 2º DAM  
**Módulo:** Patrones de Diseño (Tarea Optativa)

![Logo o imagen decorativa](descargar.jpg)
---
</div>

---

---

## 📑 Índice

1. [Introducción a los Patrones de Diseño](#introducción)
2. [Las Tres Categorías Clásicas](#categorías)
3. [Patrones Creacionales](#creacionales)
   - 3.1 [Singleton](#singleton)
   - 3.2 [Builder](#builder)
4. [Patrones Estructurales](#estructurales)
   - 4.1 [Facade](#facade)
   - 4.2 [Data Transfer Object (DTO) / Adapter](#dto-adapter)
5. [Patrones de Comportamiento](#comportamiento)
   - 5.1 [Strategy](#strategy)
   - 5.2 [Template Method](#template-method)
6. [Conclusión y Síntesis](#conclusión)
7. [Reflexión Personal - Mis Aprendizajes](#reflexion-personal)
8. [Declaración de Uso de LLMs/IA](#llms)

---

<a id="introducción"></a>
## 1. Introducción a los Patrones de Diseño

### ¿Qué es un Patrón de Diseño?

Un **patrón de diseño** es una solución reutilizable a un problema común que aparece una y otra vez en el diseño de software. No es código listo para copiar-pegar, sino más bien una **plantilla o esquema conceptual** que describe cómo resolver un problema de forma elegante y eficiente.

Los patrones de diseño fueron documentados sistemáticamente por el grupo de autores conocidos como "Gang of Four" (GoF) en el libro *"Design Patterns: Elements of Reusable Object-Oriented Software"* (1994). Desde entonces, se han convertido en una herramienta fundamental en el desarrollo orientado a objetos.

### ¿Por Qué Son Útiles?

Los patrones de diseño aportan varios beneficios al desarrollo de software:

- **Reutilización de conocimiento:** En lugar de reinventar la rueda, usamos soluciones probadas que otros desarrolladores ya han validado.
- **Comunicación clara:** Cuando un desarrollador menciona "usamos el patrón Singleton aquí", otros entienden inmediatamente la intención y estructura del código.
- **Mantenibilidad:** El código basado en patrones es más fácil de entender, modificar y extender.
- **Flexibilidad:** Los patrones facilitan cambios futuros sin tocar el código existente (principio Abierto/Cerrado).
- **Calidad del diseño:** Aplicar patrones adecuadamente reduce acoplamiento y aumenta cohesión, dos pilares de buen diseño OOP.

### Patrones en REALPRINT

REALPRINT es una aplicación **Spring Boot** para gestión de pedidos de impresión. En su arquitectura, encontramos patrones de diseño aplicados de forma natural y efectiva:

- **Singleton:** Los servicios Spring (`@Service`) son instancias únicas manejadas por el contenedor.
- **Builder:** Se usa extensivamente vía Lombok para construir objetos complejos de forma fluida.
- **Facade:** Los servicios (`PedidoService`, `AuthService`) abstraen la complejidad de repositorios y lógica de negocio.
- **Data Transfer Object (DTO):** El `PedidoMapper` convierte entre entidades de base de datos(BD) y DTOs para la API REST.
- **Strategy:** La clase `SecurityRulesService` implementa diferentes estrategias de autorización según el rol del usuario.
- **Template Method:** El `GlobalExceptionHandler` define un flujo estándar para manejar excepciones.

---

<a id="categorías"></a>
## 2. Las Tres Categorías Clásicas

Los 23 patrones de GoF se dividen en **tres categorías** según el tipo de problema que resuelven:

### 2.1 Patrones Creacionales

**Propósito:** Abstraer el proceso de creación de objetos, permitiendo que el sistema sea independiente de cómo se crean, componen y representan los objetos.

**Problemas que resuelven:**
- ¿Cómo crear objetos sin especificar sus clases concretas?
- ¿Cómo asegurar que solo existe una instancia de una clase?
- ¿Cómo construir objetos complejos paso a paso?

**Patrones en esta categoría:**
- Singleton
- Factory Method
- Abstract Factory
- Builder
- Prototype

En REALPRINT usamos: **Singleton** y **Builder**.

### 2.2 Patrones Estructurales

**Propósito:** Componer objetos en estructuras más grandes, facilitando la construcción de relaciones entre entidades de forma flexible y eficiente.

**Problemas que resuelven:**
- ¿Cómo combinar objetos y clases en estructuras más complejas?
- ¿Cómo ocultar la complejidad detrás de una interfaz simple?
- ¿Cómo convertir interfaces incompatibles?

**Patrones en esta categoría:**
- Adapter
- Bridge
- Composite
- Decorator
- Facade
- Flyweight
- Proxy

En REALPRINT usamos: **Facade** y **Data Transfer Object (Adapter)**.

### 2.3 Patrones de Comportamiento

**Propósito:** Definir formas de comunicación entre objetos, asignando responsabilidades de forma que se mantenga bajo acoplamiento.

**Problemas que resuelven:**
- ¿Cómo repartir responsabilidades entre objetos?
- ¿Cómo encapsular peticiones como objetos?
- ¿Cómo implementar distintas estrategias intercambiables?

**Patrones en esta categoría:**
- Observer
- Mediator
- Command
- State
- Strategy
- Template Method
- Visitor
- Iterator
- Interpreter
- Chain of Responsibility
- Memento

En REALPRINT usamos: **Strategy** y **Template Method**.

---

<a id="creacionales"></a>
## 3. Patrones Creacionales

<a id="singleton"></a>
### 3.1 Singleton

#### 3.1.1 Nombre y Categoría

**Patrón:** Singleton  
**Categoría:** Creacional  
**Propósito:** Garantizar que una clase tenga una única instancia en toda la aplicación y proporcionar un punto de acceso global a esa instancia.

#### 3.1.2 Intención / Propósito

El patrón Singleton resuelve un problema muy común: **¿Cómo asegurar que solo existe UNA instancia de una clase en toda la aplicación?**

En muchas situaciones, tener múltiples instancias de una clase crea problemas:
- Si tienes 10 instancias de un servicio que gestiona un recurso compartido (como la carpeta de uploads), cada una tendría su propia copia del estado → inconsistencias.
- Las instancias podrían consumir memoria innecesariamente.
- El sincronismo y coordinación entre múltiples instancias se vuelve complejo.

Singleton **garantiza que solo exista una instancia**, eliminando estos problemas.

#### 3.1.3 Motivación: Problema sin Patrón

Imagina que necesitas un servicio para gestionar archivos subidos en REALPRINT. Sin el patrón Singleton, podrías hacer algo así:

```java
// ❌ SIN PATRÓN SINGLETON — PROBLEMA
public class FileStorageService {
    private Path rootPath;
    
    public FileStorageService() {
        this.rootPath = Paths.get("uploads").toAbsolutePath().normalize();
    }
    
    public String store(MultipartFile file) {
        // Guardar archivo...
        return "archivo_guardado.pdf";
    }
}
```

**¿Dónde está el problema?**

En tu controlador:

```java
@RestController
public class FileController {
    
    @PostMapping("/upload")
    public void upload(MultipartFile file) {
        FileStorageService fs1 = new FileStorageService();  // Instancia 1
        fs1.store(file);
    }
}

@RestController
public class OtroController {
    
    @PostMapping("/otro")
    public void otra() {
        FileStorageService fs2 = new FileStorageService();  // Instancia 2
        fs2.store(file);
    }
}
```

**Consecuencias:**
- Crearías una instancia diferente cada vez → desperdicio de memoria
- Si el servicio mantuviera estado (caché, configuración), cada instancia tendría la suya → inconsistencias
- Difícil de probar y mantener (inyectar la misma instancia en todos lados es tedioso)

#### 3.1.4 Estructura: Diagrama UML

```
┌─────────────────────────────────────────┐
│         Singleton                       │
├─────────────────────────────────────────┤
│ - instance: Singleton (estática)        │
│ - data: String                          │
├─────────────────────────────────────────┤
│ - Singleton() [privado]                 │
│ + getInstance(): Singleton (estática)   │
│ + getData(): String                     │
│ + setData(String)                       │
└─────────────────────────────────────────┘

Acceso:
Singleton.getInstance() → retorna siempre la MISMA instancia
```

#### 3.1.5 Implementación en Java: Singleton Manual

Primero, aquí está la implementación **clásica** de Singleton (sin frameworks):

```java
// ✅ SINGLETON MANUAL — IMPLEMENTACIÓN CLÁSICA
public class FileStorageServiceManual {
    
    // La instancia estática única
    private static FileStorageServiceManual instance;
    
    // Constructor privado — previene new FileStorageServiceManual()
    private FileStorageServiceManual() {
        System.out.println("Inicializando FileStorageService (UNA SOLA VEZ)");
    }
    
    // Método estático para obtener la instancia
    public static synchronized FileStorageServiceManual getInstance() {
        if (instance == null) {
            instance = new FileStorageServiceManual();
        }
        return instance;
    }
    
    public String store(MultipartFile file) {
        System.out.println("Guardando archivo con la instancia única...");
        return "archivo_guardado.pdf";
    }
}
```

**¿Cómo se usa?**

```java
// Obtienes la instancia única
FileStorageServiceManual fs1 = FileStorageServiceManual.getInstance();
fs1.store(file1);  // Guarda archivo 1

// Segunda llamada — retorna LA MISMA instancia
FileStorageServiceManual fs2 = FileStorageServiceManual.getInstance();
fs2.store(file2);  // Guarda archivo 2

// fs1 == fs2 → TRUE (mismo objeto en memoria)
assert fs1 == fs2;  // ✅ Pasa
```

#### 3.1.6 Singleton en REALPRINT: Spring @Service

En REALPRINT usamos **Spring Framework**, que implementa Singleton automáticamente vía la anotación `@Service`:

```java
// ✅ SINGLETON EN SPRING — AUTOMÁTICO
@Service
public class FileStorageService {
    
    private static final long MAX_FILE_SIZE_BYTES = 10 * 1024 * 1024; // 10 MB
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "jpg", "jpeg", "png");
    
    @Value("${app.upload.dir:uploads}")
    private String uploadDir;
    
    private Path rootPath;
    
    @PostConstruct
    void init() {
        try {
            rootPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(rootPath);
        } catch (IOException ex) {
            throw new ResponseStatusException(INTERNAL_SERVER_ERROR, 
                "No se pudo inicializar el directorio de archivos", ex);
        }
    }
    
    public String store(MultipartFile file) {
        // ... lógica de almacenamiento ...
        return storedName;
    }
    
    public StoredFile load(String fileName) {
        // ... lógica de lectura ...
        return storedFile;
    }
}
```

**¿Por qué es Singleton?**

1. **`@Service`** es una anotación de Spring que dice "crea una instancia única de esta clase en el contenedor"
2. Cuando un controlador necesita `FileStorageService`, Spring **inyecta la misma instancia** siempre:

```java
@RestController
@RequiredArgsConstructor  // Lombok genera el constructor
public class FileController {
    
    private final FileStorageService fileStorageService;  // Spring inyecta la única instancia
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String storedName = fileStorageService.store(file);  // Usa la misma instancia
        // ...
    }
}
```

3. En otro controlador:

```java
@RestController
@RequiredArgsConstructor
public class OtroController {
    
    private final FileStorageService fileStorageService;  // Spring inyecta LA MISMA instancia
    
    @GetMapping("/files/{fileName}")
    public ResponseEntity<?> download(@PathVariable String fileName) {
        FileStorageService.StoredFile storedFile = fileStorageService.load(fileName);
        // Usa la misma instancia que FileController
        // ...
    }
}
```

**Conclusión:** Spring garantiza automáticamente que `FileStorageService` tenga una única instancia en toda la aplicación. No necesitas escribir el código manual del patrón Singleton; Spring lo hace por ti.

#### 3.1.7 Ventajas

1. **Control de instancias:** Garantiza que solo existe una instancia → sin duplicados en memoria
2. **Acceso global:** Se puede acceder desde cualquier parte del código (aunque esto puede ser controversial)
3. **Lazy initialization:** La instancia puede crearse solo cuando sea necesaria
4. **Thread-safe (en Spring):** El contenedor de Spring maneja sincronización automáticamente
5. **Facilita pruebas:** Con Spring, puedes mockear la dependencia en tests de forma sencilla

#### 3.1.8 Desventajas

1. **Testing complicado:** En el patrón manual, es difícil reemplazar la instancia durante las pruebas (aunque Spring lo facilita)
2. **Acoplamiento global:** El código queda acoplado al Singleton, lo que dificulta los cambios futuros
3. **Dependencias ocultas:** No es evidente qué componentes dependen de cuales (especialmente con getters estáticos)
4. **Sincronización:** En versiones manuales, la sincronización puede afectar el rendimiento
5. **Limitaciones de escalabilidad:** En arquitecturas distribuidas, un Singleton por máquina puede no ser suficiente

#### 3.1.9 Cuándo Usarlo / Cuándo No

**✅ USAR Singleton cuando:**
- Necesitas exactamente una instancia de una clase (ej: gestor de BD, servicio de logging)
- La instancia gestiona un recurso compartido costoso (conexiones de red, archivos, memoria)
- Quieres un punto de acceso centralizado a ese recurso
- Trabajas con Spring y necesitas un servicio inyectable

**❌ NO USAR cuando:**
- Necesitas múltiples instancias por razones de negocio
- Trabajas con programación concurrente compleja sin sincronización adecuada
- Quieres facilitar pruebas unitarias sin complicaciones
- La clase tiene estado mutable que cambia frecuentemente

#### 3.1.10 Patrones Relacionados

- **Factory Method:** A menudo se usa en combinación con Singleton para controlar cómo se crea la instancia
- **Abstract Factory:** Puede usar Singleton internamente
- **Facade:** Suele ser Singleton (un punto de acceso único a un subsistema)
- **Borg Pattern / Monostate:** El patrón Borg es específico de Python. El equivalente en Java es el **Monostate Pattern**, que comparte estado entre múltiples instancias mediante campos estáticos, en lugar de forzar una sola instancia.

<a id="builder"></a>
### 3.2 Builder

#### 3.2.1 Nombre y Categoría

**Patrón:** Builder  
**Categoría:** Creacional  
**Propósito:** Separar la construcción de un objeto complejo de su representación, permitiendo crear diferentes representaciones del mismo usando el mismo proceso de construcción.

#### 3.2.2 Intención / Propósito

El patrón Builder resuelve este problema: **¿Cómo construir objetos complejos con muchos parámetros opcionales de forma clara y mantenible?**

Imagina que necesitas crear un objeto `Pedido` con 15 propiedades, pero muchas son opcionales. Sin el patrón Builder, tendrías dos opciones horribles:

1. **Constructor con todos los parámetros:**
   ```java
   Pedido p = new Pedido(id, cliente, servicio, desc, cant, fecha, fechaEntrega, 
                          ancho, alto, estado, total, null, null, null, null);
   ```
   → ¡Ilegible! ¿Qué significan esos nulls?

2. **Múltiples constructores sobrecargados:**
   ```java
   Pedido(Long id, Usuario cliente, String servicio)
   Pedido(Long id, Usuario cliente, String servicio, Integer cantidad)
   Pedido(Long id, Usuario cliente, String servicio, Integer cantidad, String descripcion)
   // ... y así sucesivamente
   ```
   → ¡Explosión de constructores! ("Telescoping Constructor Pattern" — antipatrón)

Builder ofrece una solución elegante: **fluent interface** para construir objetos paso a paso.

#### 3.2.3 Motivación: Problema sin Patrón

Sin Builder, crear un `PedidoDTO` es engorroso:

```java
// ❌ SIN BUILDER — PROBLEMA
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private String descripcion;
    private Integer cantidad;
    private LocalDate fecha;
    private LocalDate fechaEntrega;
    private Integer measurementWidthCm;
    private Integer measurementHeightCm;
    private String estado;
    private BigDecimal total;
    
    // Constructor con todo — ilegible
    public PedidoDTO(Long id, Long clienteId, String clienteNombre, 
                     String servicio, String descripcion, Integer cantidad, 
                     LocalDate fecha, LocalDate fechaEntrega, 
                     Integer widthCm, Integer heightCm, 
                     String estado, BigDecimal total) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        // ... etc
    }
}

// Crearlo es un infierno:
PedidoDTO pedido = new PedidoDTO(
    1L, 
    100L, 
    "Juan", 
    "Serigrafía", 
    "Imprimir logo",
    50,
    LocalDate.now(),
    LocalDate.now().plusDays(5),
    20,
    30,
    "pendiente",
    new BigDecimal("250.00")
);
// ¿Qué representa cada número? Hay que contar parámetros...
```

#### 3.2.4 Estructura: Diagrama UML

```
┌─────────────────────────────────────────┐
│         PedidoDTO (Producto)            │
├─────────────────────────────────────────┤
│ - id: Long                              │
│ - clienteId: Long                       │
│ - servicio: String                      │
│ - descripcion: String                   │
│ - cantidad: Integer                     │
│ - estado: String                        │
│ - total: BigDecimal                     │
│ ... (otros atributos)                   │
├─────────────────────────────────────────┤
│ - PedidoDTO()  [privado]                │
└─────────────────────────────────────────┘
         ▲
         │ construye
         │
┌─────────────────────────────────────────┐
│      PedidoDTO.Builder                  │
├─────────────────────────────────────────┤
│ - id: Long                              │
│ - clienteId: Long                       │
│ - servicio: String                      │
│ ... (todos los atributos)               │
├─────────────────────────────────────────┤
│ + Builder id(Long): Builder             │
│ + Builder clienteId(Long): Builder      │
│ + Builder servicio(String): Builder     │
│ ... (un método por atributo)            │
│ + build(): PedidoDTO                    │
└─────────────────────────────────────────┘

Uso (interfaz fluida):
PedidoDTO pedido = new PedidoDTO.Builder()
    .id(1L)
    .clienteId(100L)
    .servicio("Serigrafía")
    .cantidad(50)
    .build();
```

#### 3.2.5 Implementación Manual en Java

Aquí está la implementación **clásica** de Builder (sin Lombok):

```java
// ✅ BUILDER MANUAL — IMPLEMENTACIÓN CLÁSICA
public class PedidoDTOManual {
    
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private String descripcion;
    private Integer cantidad;
    private LocalDate fecha;
    private BigDecimal total;
    
    // Constructor privado — solo accesible desde Builder
    private PedidoDTOManual(Builder builder) {
        this.id = builder.id;
        this.clienteId = builder.clienteId;
        this.clienteNombre = builder.clienteNombre;
        this.servicio = builder.servicio;
        this.descripcion = builder.descripcion;
        this.cantidad = builder.cantidad;
        this.fecha = builder.fecha;
        this.total = builder.total;
    }
    
    // Getters estándar
    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getServicio() { return servicio; }
    // ... resto de getters
    
    // ✅ CLASE BUILDER INTERNA
    public static class Builder {
        
        private Long id;
        private Long clienteId;
        private String clienteNombre;
        private String servicio;
        private String descripcion;
        private Integer cantidad;
        private LocalDate fecha;
        private BigDecimal total;
        
        // Métodos fluidos (devuelven 'this' para encadenar)
        public Builder id(Long id) {
            this.id = id;
            return this;  // ← Permite encadenamiento
        }
        
        public Builder clienteId(Long clienteId) {
            this.clienteId = clienteId;
            return this;
        }
        
        public Builder clienteNombre(String clienteNombre) {
            this.clienteNombre = clienteNombre;
            return this;
        }
        
        public Builder servicio(String servicio) {
            this.servicio = servicio;
            return this;
        }
        
        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }
        
        public Builder cantidad(Integer cantidad) {
            this.cantidad = cantidad;
            return this;
        }
        
        public Builder fecha(LocalDate fecha) {
            this.fecha = fecha;
            return this;
        }
        
        public Builder total(BigDecimal total) {
            this.total = total;
            return this;
        }
        
        // Método final que construye el objeto
        public PedidoDTOManual build() {
            return new PedidoDTOManual(this);
        }
    }
}

// ✅ CÓMO SE USA — Mucho más legible
PedidoDTOManual pedido = new PedidoDTOManual.Builder()
    .id(1L)
    .clienteId(100L)
    .servicio("Serigrafía")
    .descripcion("Imprimir logo")
    .cantidad(50)
    .fecha(LocalDate.now())
    .total(new BigDecimal("250.00"))
    .build();  // ← Crea el objeto

// Si solo necesitas algunos campos, solo los especificas:
PedidoDTOManual pedidoSimple = new PedidoDTOManual.Builder()
    .servicio("Planchado")
    .cantidad(10)
    .build();  // Los demás campos quedan con valores por defecto (null o 0)
```

#### 3.2.6 Builder en REALPRINT: Lombok @Builder

En REALPRINT usamos **Lombok**, que genera el código del Builder automáticamente:

```java
// ✅ BUILDER EN REALPRINT — CON LOMBOK
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder  // ← Lombok genera la clase Builder automáticamente
public class PedidoDTO {
    
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private String descripcion;
    private Integer cantidad;
    private LocalDate fecha;
    private LocalDate fechaEntrega;
    private Integer measurementWidthCm;
    private Integer measurementHeightCm;
    private String estado;
    private BigDecimal total;
}
```

**¿Qué hace Lombok aquí?**

Durante la compilación, Lombok genera el equivalente a nuestra clase `Builder` manual. El resultado es que puedes usar:

```java
// Créalo en el mapper, por ejemplo:
PedidoDTO pedidoDto = PedidoDTO.builder()
    .id(pedido.getId())
    .clienteId(pedido.getCliente().getId())
    .clienteNombre(pedido.getCliente().getNombre())
    .servicio(pedido.getServicio())
    .descripcion(pedido.getDescripcion())
    .cantidad(pedido.getCantidad())
    .fecha(pedido.getFecha())
    .fechaEntrega(pedido.getFechaEntrega())
    .measurementWidthCm(pedido.getMeasurementWidthCm())
    .measurementHeightCm(pedido.getMeasurementHeightCm())
    .estado(PedidoMapper.estadoEnumToString(pedido.getEstado()))
    .total(pedido.getTotal())
    .build();
```

De hecho, en el `PedidoMapper.java` de REALPRINT, eso es exactamente lo que ves:

```java
public static PedidoDTO toDTO(Pedido pedido) {
    if (pedido == null) {
        return null;
    }
    
    return PedidoDTO.builder()  // ← Inicia el builder
            .id(pedido.getId())
            .clienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null)
            .clienteNombre(pedido.getCliente() != null ? pedido.getCliente().getNombre() : "")
            .servicio(pedido.getServicio())
            .descripcion(pedido.getDescripcion())
            .cantidad(pedido.getCantidad())
            .fecha(pedido.getFecha())
            .fechaEntrega(pedido.getFechaEntrega())
            .measurementWidthCm(pedido.getMeasurementWidthCm())
            .measurementHeightCm(pedido.getMeasurementHeightCm())
            .estado(estadoEnumToString(pedido.getEstado()))
            .total(pedido.getTotal())
            .build();  // ← Construye y retorna el DTO
}
```

Otro ejemplo en tu código — `LoginResponse.builder()`:

```java
// En AuthService.java
public LoginResponse login(LoginRequest request) {
    // ... validaciones ...
    
    String token = jwtService.generateToken(usuario);
    
    return LoginResponse.builder()  // ← Usa Builder
            .token(token)
            .user(
                LoginResponse.UserInfo.builder()  // ← Builder anidado
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .name(usuario.getNombre())
                    .role(usuario.getRol().name().toLowerCase())
                    .build()
            )
            .build();
}
```

#### 3.2.7 Ventajas

1. **Legibilidad:** El código que construye objetos es claro y autodocumentado
2. **Flexibilidad:** Puedes omitir parámetros opcionales sin usar nulls
3. **Inmutabilidad (opcional):** El patrón facilita la creación de objetos inmutables
4. **Validación:** Puedes validar el objeto en el método `build()` antes de crearlo
5. **Facilita mantenimiento:** Añadir nuevos campos es simple; solo añades un nuevo método setter en Builder

#### 3.2.8 Desventajas

1. **Código boilerplate:** Sin Lombok, escribes mucho código repetitivo
2. **Overhead:** Crear un Builder extra consume algo más de memoria (aunque es mínimo)
3. **Complejidad aparente:** Para principiantes, la interfaz fluida puede resultar confusa
4. **Seguridad de tipos limitada:** No puedes validar en tiempo de compilación que ciertos campos sean obligatorios

#### 3.2.9 Cuándo Usarlo / Cuándo No

**✅ USAR Builder cuando:**
- El objeto tiene 4 o más parámetros
- Muchos parámetros son opcionales
- El orden de parámetros no es intuitivo
- Quieres código legible y mantenible
- Usas Lombok u otra librería que genere el Builder

**❌ NO USAR cuando:**
- El objeto tiene solo 1-2 parámetros simples
- Todos los parámetros son obligatorios y el constructor es claro
- El rendimiento es crítico y no puedes añadir overhead

#### 3.2.10 Patrones Relacionados

- **Factory Method:** Puede usarse en combinación con Builder
- **Singleton:** Los Builders mismos pueden ser singletons en ciertos contextos
- **Prototype:** Builder construye objetos; Prototype los clona
- **Abstract Factory:** Ambos sirven para crear objetos complejos, pero Abstract Factory crea familias de objetos relacionados

---

<a id="estructurales"></a>
## 4. Patrones Estructurales

<a id="facade"></a>
### 4.1 Facade

#### 4.1.1 Nombre y Categoría

**Patrón:** Facade  
**Categoría:** Estructural  
**Propósito:** Proporcionar una interfaz unificada y simplificada a un conjunto de interfaces complejas en un subsistema. El cliente interactúa con la fachada en lugar de con los componentes internos.

#### 4.1.2 Intención / Propósito

El patrón Facade resuelve este problema: **¿Cómo simplificar el acceso a un subsistema complejo y ocultar detalles de implementación?**

Imagina que necesitas procesar un pedido. Internamente, tu sistema debe:
1. Buscar el pedido en la BD (repositorio)
2. Validar que el estado sea válido
3. Calcular impuestos y descuentos
4. Actualizar el inventario
5. Registrar el evento en auditoría
6. Enviar una notificación al cliente

Sin Facade, el controlador tendría que orquestar todo esto. Con Facade, delegas la complejidad a un servicio que **oculta los detalles**.

#### 4.1.3 Motivación: Problema sin Patrón

Sin Facade, tu controlador estaría desordenado:

```java
// ❌ SIN FACADE — PROBLEMA
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private InventoryService inventoryService;
    @Autowired private NotificationService notificationService;
    @Autowired private AuditService auditService;
    @Autowired private TaxCalculator taxCalculator;
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        // ¿Primero valido el ID?
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        // Busco en BD
        Optional<Pedido> pedidoOpt = pedidoRepository.findById(id);
        if (!pedidoOpt.isPresent()) {
            throw new PedidoNotFoundException("Pedido no encontrado");
        }
        
        Pedido pedido = pedidoOpt.get();
        
        // Valido estado
        if (!pedido.getEstado().equals(PedidoEstado.COMPLETADO)) {
            // ¿Qué hago? ¿Tiro excepción?
        }
        
        // Auditoría
        auditService.log("Pedido consultado: " + id);
        
        // Convertir a DTO (manualmente o con mapper)
        PedidoDTO dto = convertToDTO(pedido);
        
        return ResponseEntity.ok(dto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> actualizarPedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO pedidoDTO) {
        
        // Buscar
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado"));
        
        // Validar transición de estado
        PedidoEstado estadoAnterior = pedido.getEstado();
        PedidoEstado estadoNuevo = PedidoEstado.valueOf(pedidoDTO.getEstado().toUpperCase());
        if (!puedeTransicionar(estadoAnterior, estadoNuevo)) {
            throw new IllegalStateException("Transición de estado no permitida");
        }
        
        // Actualizar
        pedido.setEstado(estadoNuevo);
        pedido.setTotal(pedidoDTO.getTotal());
        // ... más campos ...
        
        // Si cambia a ENVIADO, actualizar inventario
        if (estadoNuevo.equals(PedidoEstado.ENVIADO)) {
            inventoryService.deductFromInventory(pedido);
        }
        
        // Guardar
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        
        // Notificar cliente
        notificationService.notifyClient(
            pedido.getCliente().getEmail(),
            "Tu pedido ha sido " + estadoNuevo.name().toLowerCase()
        );
        
        // Auditoría
        auditService.log("Pedido " + id + " actualizado de " + estadoAnterior + " a " + estadoNuevo);
        
        return ResponseEntity.ok(convertToDTO(pedidoActualizado));
    }
    
    // Método auxiliar
    private boolean puedeTransicionar(PedidoEstado from, PedidoEstado to) {
        // Lógica de validación...
        return true;
    }
    
    private PedidoDTO convertToDTO(Pedido p) { /* ... */ }
}
```

**Problemas:**
- El controlador está **sobrecargado** de responsabilidades
- **Lógica de negocio** mezclada con manejo de HTTP
- **Difícil de probar** (muchas dependencias)
- **Dónde cambia la lógica?** Hay que buscar en el controlador

#### 4.1.4 Estructura: Diagrama UML

```
┌─────────────────────────────────┐
│       Cliente (Controlador)     │
└─────────────────────────────────┘
                 │ usa
                 ▼
┌─────────────────────────────────┐
│     PedidoFacade (Servicio)     │  ← FACHADA
└─────────────────────────────────┘
         │ usa internamente
         ▼
┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐
│ Repo │ │ Inv  │ │ Not  │ │ Aud  │ │ Tax  │  ← COMPONENTES
└──────┘ └──────┘ └──────┘ └──────┘ └──────┘
                  (complejos)

Ventaja: El cliente solo ve la fachada;
los componentes internos quedan ocultos.
```

#### 4.1.5 Implementación en REALPRINT: PedidoService

En REALPRINT, **`PedidoService`** es la Facade. Observa cómo simplifica:

```java
// ✅ FACADE EN REALPRINT
@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {  // ← FACHADA
    
    private final PedidoRepository pedidoRepository;  // componentes internos
    private final UsuarioRepository usuarioRepository;
    
    // Método simple que oculta complejidad
    @Transactional(readOnly = true)
    public Pedido findById(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNoEncontradoException(
                    "Pedido no encontrado con id: " + id));
    }
    
    // Oculta la lógica de autenticación
    public Pedido save(Pedido pedido, Authentication auth) {
        String username = auth.getName();
        Usuario usuarioAutenticado = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        if (pedido.getCliente() == null) {
            pedido.setCliente(usuarioAutenticado);
        }
        
        return pedidoRepository.save(pedido);
    }
    
    // Oculta la lógica de actualización
    public Pedido update(Long id, Pedido pedidoActualizado) {
        Pedido pedidoExistente = findById(id);
        
        pedidoActualizado.setId(pedidoExistente.getId());
        pedidoActualizado.setCliente(pedidoExistente.getCliente());
        
        return pedidoRepository.save(pedidoActualizado);
    }
    
    // Oculta la transición de estados
    public Pedido updateEstado(Long id, PedidoEstado nuevoEstado) {
        Pedido pedido = findById(id);
        pedido.setEstado(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
    
    public void deleteById(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new PedidoNoEncontradoException("Pedido no encontrado con id: " + id);
        }
        pedidoRepository.deleteById(id);
    }
}
```

**Ahora el controlador es simple y limpio:**

```java
// ✅ CONTROLADOR LIMPIO — GRACIAS A LA FACHADA
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final PedidoService pedidoService;  // ← Solo la fachada
    
    @GetMapping("/{id}")
    @PostAuthorize("@securityRules.canReadPedido(authentication, returnObject.body)")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);  // Fachada
        return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
    }
    
    @PostMapping
    @PreAuthorize("@securityRules.canCreatePedido(authentication)")
    public ResponseEntity<PedidoDTO> crearPedido(
            @RequestBody PedidoDTO pedidoDTO,
            Authentication auth) {
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoService.save(pedido, auth);  // Fachada
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoMapper.toDTO(pedidoGuardado));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PedidoDTO> actualizarPedido(
            @PathVariable Long id,
            @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoActualizado = pedidoService.update(id, pedido);  // Fachada
        return ResponseEntity.ok(PedidoMapper.toDTO(pedidoActualizado));
    }
}
```

**Beneficios:**
- El controlador es **pequeño y enfocado** en HTTP
- La **lógica de negocio** está en `PedidoService` (fachada)
- **Fácil de mantener** y extender
- **Fácil de probar** (mockear solo la fachada)

#### 4.1.6 Otro Ejemplo: AuthService

Lo mismo ocurre con `AuthService`:

```java
// ✅ OTRA FACHADA EN REALPRINT
@Service
@RequiredArgsConstructor
public class AuthService {  // ← FACHADA de autenticación
    
    private final UsuarioRepository usuarioRepository;  // componentes
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    // Método simple que oculta lógica compleja
    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña incorrectos"));
        
        if (!usuario.isActivo()) {
            throw new UnauthorizedException("Usuario inactivo");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new UnauthorizedException("Usuario o contraseña incorrectos");
        }
        
        String token = jwtService.generateToken(usuario);
        
        return LoginResponse.builder()
                .token(token)
                .user(LoginResponse.UserInfo.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .name(usuario.getNombre())
                    .role(usuario.getRol().name().toLowerCase())
                    .build())
                .build();
    }
}
```

El controlador solo llama a la fachada:

```java
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;  // ← Solo la fachada
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);  // Fachada
        return ResponseEntity.ok(response);
    }
}
```

#### 4.1.7 Ventajas

1. **Simplicidad:** El cliente ve una interfaz simple, no la complejidad interna
2. **Bajo acoplamiento:** Los cambios internos no afectan al cliente
3. **Separación de responsabilidades:** Lógica de negocio separada de presentación
4. **Reutilizabilidad:** Otros componentes pueden usar la fachada
5. **Mantenibilidad:** Lógica concentrada en un lugar
6. **Testabilidad:** Fácil de mockear y probar

#### 4.1.8 Desventajas

1. **Excesiva simplificación:** A veces oculta demasiado, dificultando cambios
2. **Punto único de fallo:** Si la fachada falla, todo falla
3. **Over-engineering:** Para lógica muy simple, puede ser overkill
4. **Rendimiento:** Capas adicionales pueden añadir overhead (mínimo en Spring)

#### 4.1.9 Cuándo Usarlo / Cuándo No

**✅ USAR Facade cuando:**
- Tienes un subsistema con múltiples componentes acoplados
- Quieres simplificar el acceso para los clientes
- La lógica de negocio es compleja
- Necesitas centralizar la lógica para facilitar cambios
- Usas Spring y creas servicios `@Service` (ya es Facade implícitamente)

**❌ NO USAR cuando:**
- La lógica es muy simple (1-2 operaciones triviales)
- Necesitas acceso directo a los componentes internos
- La fachada crearía más complejidad que beneficio

#### 4.1.10 Patrones Relacionados

- **Singleton:** Las fachadas suelen ser Singletons (instancia única)
- **Observer:** Puede trabajar con Facade para notificar cambios
- **Strategy:** Puede usarse dentro de una Facade para elegir algoritmos
- **Proxy:** Ambos simplifican el acceso, pero Proxy controla el acceso; Facade simplifica interfaz

<a id="dto-adapter"></a>
### 4.2 Data Transfer Object (DTO) / Adapter

#### 4.2.1 Nombre y Categoría

**Patrón:** Data Transfer Object (DTO) / Adapter  
**Categoría:** Estructural  
**Propósito:** Convertir la interfaz de una clase a otra que los clientes esperan. En el contexto de REALPRINT, el Adapter es el `PedidoMapper`, que convierte entre la entidad `Pedido` (modelo de BD) y el DTO `PedidoDTO` (modelo de API REST).

> ⚠️ **Nota:** El DTO no es un patrón GoF original; es un patrón de arquitectura empresarial documentado por Martin Fowler en *Patterns of Enterprise Application Architecture* (2002). Se incluye aquí junto al **Adapter** (patrón GoF) porque `PedidoMapper` actúa como adaptador entre dos representaciones incompatibles: la entidad de BD y el modelo de API REST. Esta combinación es la más habitual en aplicaciones Spring Boot.

#### 4.2.2 Intención / Propósito

El patrón Adapter (implementado como DTO Mapper) resuelve este problema: **¿Cómo convertir un objeto de una forma a otra compatible con lo que el cliente espera?**

En REALPRINT:
- La **BD guarda** `Pedido` con campos en MAYÚSCULAS (`PedidoEstado.PENDIENTE`)
- La **API REST devuelve** `PedidoDTO` con estados en minúsculas (`"pendiente"`)
- Necesitas un **puente** que convierte entre estos formatos

Otras razones para usar DTOs:
1. **Seguridad:** No exponer todos los campos de la entidad
2. **Desacoplamiento:** La BD no está directamente vinculada a la API
3. **Composición:** Agrupar datos de múltiples entidades en un DTO
4. **Validación:** Validaciones diferentes en BD vs. API

#### 4.2.3 Motivación: Problema sin Patrón

Sin DTOs/Adapter, tu API devolvería directamente la entidad:

```java
// ❌ SIN DTO/ADAPTER — PROBLEMA
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @Autowired private PedidoRepository pedidoRepository;
    
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No encontrado"));
        
        return ResponseEntity.ok(pedido);  // ← Devuelves la entidad directamente
    }
}
```

**Problemas:**
1. **Exposición de BD:** El cliente ve exactamente la estructura de la BD
2. **Acoplamiento:** Si cambias la BD, cambia la API
3. **Relaciones cargadas:** Si `Pedido` tiene una relación `@ManyToOne` con `Usuario`, Spring carga TODO (problema N+1)
4. **Seguridad:** No puedes ocultar datos sensibles (ej: passwordHash)
5. **Incompatibilidad:** Si BD tiene `LocalDateTime` pero frontend espera string ISO, necesitas conversión

JSON que sale:
```json
{
  "id": 1,
  "cliente": {
    "id": 100,
    "username": "juan",
    "passwordHash": "$2a$10$...",  // ← ¡EXPUESTO!
    "email": "juan@...",
    "activo": true
  },
  "estado": "PENDIENTE",  // ← MAYÚSCULAS (no consistente con frontend)
  "createdAt": "2026-04-28T10:30:00",  // ← Formato puede no ser esperado
  // ... más campos ...
}
```

#### 4.2.4 Estructura: Diagrama UML

```
┌─────────────────────────────────────────┐
│      Pedido (Entity - BD)              │
├─────────────────────────────────────────┤
│ - id: Long                              │
│ - cliente: Usuario                      │
│ - estado: PedidoEstado (ENUM)           │  MAYÚSCULAS
│ - createdAt: LocalDateTime              │
│ ... (otros atributos)                   │
└─────────────────────────────────────────┘
         │
         │ PedidoMapper
         │ .toDTO(Pedido) -> PedidoDTO
         │ .toEntity(PedidoDTO) -> Pedido
         │
         v
┌─────────────────────────────────────────┐
│    PedidoDTO (DTO - API REST)           │
├─────────────────────────────────────────┤
│ - id: Long                              │
│ - clienteId: Long                       │  Solo ID (no objeto completo)
│ - clienteNombre: String                 │  Información extra para UI
│ - estado: String                        │  minúsculas
│ - fecha: LocalDate (sin hora)           │  Solo lo necesario
│ ... (otros atributos)                   │
└─────────────────────────────────────────┘

Ventaja: Adapter traduce entre dos interfaces incompatibles
```

#### 4.2.5 Implementación en REALPRINT: PedidoMapper

En REALPRINT, el `PedidoMapper` es el Adapter:

```java
// ✅ ADAPTER (DTO MAPPER) EN REALPRINT
public class PedidoMapper {
    
    /**
     * Convierte Pedido (Entity) → PedidoDTO
     * Cambios importantes:
     * 1. ENUM estado a string minúsculas: PENDIENTE → "pendiente"
     * 2. Usuario completo se convierte en clienteId + clienteNombre
     * 3. Solo devuelves lo que el frontend necesita
     */
    public static PedidoDTO toDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        
        return PedidoDTO.builder()
                .id(pedido.getId())
                // Extrae solo ID y nombre del usuario
                .clienteId(pedido.getCliente() != null ? pedido.getCliente().getId() : null)
                .clienteNombre(pedido.getCliente() != null ? pedido.getCliente().getNombre() : "")
                // CRITICO: Convierte ENUM a minúsculas
                .servicio(pedido.getServicio())
                .descripcion(pedido.getDescripcion())
                .cantidad(pedido.getCantidad())
                .fecha(pedido.getFecha())  // Solo LocalDate, no LocalDateTime
                .fechaEntrega(pedido.getFechaEntrega())
                .measurementWidthCm(pedido.getMeasurementWidthCm())
                .measurementHeightCm(pedido.getMeasurementHeightCm())
                .estado(estadoEnumToString(pedido.getEstado()))  // ← PENDIENTE → "pendiente"
                .total(pedido.getTotal())
                .build();
    }
    
    /**
     * Convierte PedidoDTO → Pedido (Entity)
     * Cambios importantes:
     * 1. String estado minúsculas → ENUM MAYÚSCULAS
     * 2. El cliente NO se asigna aquí (lo hace el servicio)
     */
    public static Pedido toEntity(PedidoDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Pedido.builder()
                .id(dto.getId())
                // NO asignamos cliente aquí — el servicio lo hace
                .servicio(dto.getServicio())
                .descripcion(dto.getDescripcion())
                .cantidad(dto.getCantidad())
                .fecha(dto.getFecha())
                .fechaEntrega(dto.getFechaEntrega())
                .measurementWidthCm(dto.getMeasurementWidthCm())
                .measurementHeightCm(dto.getMeasurementHeightCm())
                .estado(stringToEstadoEnum(dto.getEstado()))  // ← "pendiente" → PENDIENTE
                .total(dto.getTotal())
                .build();
    }
    
    // Métodos auxiliares de conversión
    public static String estadoEnumToString(PedidoEstado estado) {
        if (estado == null) {
            return "pendiente";
        }
        return estado.name().toLowerCase();  // PENDIENTE → "pendiente"
    }
    
    public static PedidoEstado stringToEstadoEnum(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            return PedidoEstado.PENDIENTE;
        }
        try {
            return PedidoEstado.valueOf(estado.toUpperCase());  // "pendiente" → PENDIENTE
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido: " + estado, e);
        }
    }
}
```

**Cómo se usa:**

En el controlador:

```java
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final PedidoService pedidoService;
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);  // Devuelve Entity
        PedidoDTO dto = PedidoMapper.toDTO(pedido);  // ← ADAPTER (conversión)
        return ResponseEntity.ok(dto);  // Devuelve DTO
    }
    
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(
            @RequestBody PedidoDTO pedidoDTO,  // Recibe DTO desde frontend
            Authentication auth) {
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);  // ← ADAPTER (conversión)
        Pedido pedidoGuardado = pedidoService.save(pedido, auth);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoMapper.toDTO(pedidoGuardado));  // ← ADAPTER (conversión)
    }
}
```

**JSON que sale ahora (con adapter):**

```json
{
  "id": 1,
  "clienteId": 100,
  "clienteNombre": "Juan",  // ✅ Sólo necesario para UI
  "servicio": "Serigrafía",
  "descripcion": "Imprimir logo",
  "cantidad": 50,
  "fecha": "2026-04-28",  // ✅ Solo fecha
  "estado": "pendiente",  // ✅ minúsculas
  "total": "250.00"
  // ✅ NO hay passwordHash, NO hay createdAt/updatedAt, NO hay relaciones complejas
}
```

#### 4.2.6 Ventajas

1. **Seguridad:** No expones campos sensibles (passwordHash)
2. **Desacoplamiento:** BD y API evolucionan independientemente
3. **Control de datos:** Envías solo lo que el cliente necesita
4. **Compatibilidad:** Conversiones entre formatos (ENUM ↔ String, LocalDateTime ↔ String ISO)
5. **Composición:** Puedes agrupar datos de múltiples entidades en un DTO
6. **Versionado de API:** Puedes tener DTOv1, DTOv2 sin cambiar las entidades
7. **Validación:** Validaciones diferentes en BD vs. API

#### 4.2.7 Desventajas

1. **Código boilerplate:** Más clases y métodos de conversión
2. **Overhead:** Las conversiones consumen CPU (aunque es mínimo en REALPRINT)
3. **Duplicación:** La información está en dos sitios (Entity + DTO)
4. **Complejidad:** Más capas significa más para entender

#### 4.2.8 Cuándo Usarlo / Cuándo No

**✅ USAR DTO/Adapter cuando:**
- Tienes una API REST que no debe exponer BD directamente
- Necesitas convertir entre formatos (ENUM ↔ String, tipos de datos)
- Quieres seguridad (ocultar campos sensibles)
- Necesitas componer datos de múltiples entidades
- Trabajas con versionado de API
- Necesitas validaciones diferentes en BD vs. API

**❌ NO USAR cuando:**
- La entidad y el DTO son idénticos
- Es una API interna (sin clientes externos)
- El overhead de conversión es crítico (muy raro)

#### 4.2.9 Patrones Relacionados

- **Builder:** El DTO se construye con Builder (como vimos en Lombok `@Builder`)
- **Facade:** El mapper suele usarse dentro de una fachada (servicio)
- **Factory:** Puede usar un factory para crear DTOs complejos
- **Proxy:** Similar en que ambos actúan como "intermediarios"
- **Decorator:** Puede decorar una entidad añadiendo campos al DTO

---

<a id="comportamiento"></a>
## 5. Patrones de Comportamiento

<a id="strategy"></a>
### 5.1 Strategy

#### 5.1.1 Nombre y Categoría

**Patrón:** Strategy  
**Categoría:** De Comportamiento  
**Propósito:** Definir una familia de algoritmos, encapsular cada uno, y hacerlos intercambiables. El patrón permite que el algoritmo varíe independientemente de los clientes que lo usan.

#### 5.1.2 Intención / Propósito

El patrón Strategy resuelve este problema: **¿Cómo hacer que el comportamiento de un objeto sea intercambiable en tiempo de ejecución?**

En REALPRINT, necesitas diferentes **estrategias de autorización** según el rol del usuario:
- Si eres **ADMIN**, puedes ver todos los pedidos
- Si eres **CLIENTE**, solo ves tus propios pedidos

Sin Strategy, tendrías un if-else gigante. Con Strategy, encapsulas cada comportamiento en una clase.

#### 5.1.3 Motivación: Problema sin Patrón

Sin Strategy, tu código estaría lleno de ifs:

```java
// ❌ SIN STRATEGY — PROBLEMA (if-else gigante)
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(
            @PathVariable Long id,
            Authentication auth) {
        
        Pedido pedido = pedidoService.findById(id);
        
        // ¡If-else gigante!
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // ADMIN puede ver cualquier pedido
            return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
        } else if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            // CLIENTE solo puede ver sus propios pedidos
            Usuario usuarioAutenticado = usuarioRepository.findByUsername(auth.getName()).orElse(null);
            if (usuarioAutenticado != null && 
                usuarioAutenticado.getId().equals(pedido.getCliente().getId())) {
                return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
            } else {
                throw new AccessDeniedException("No tienes permiso para ver este pedido");
            }
        } else {
            throw new AccessDeniedException("Rol desconocido");
        }
    }
    
    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(
            @RequestBody PedidoDTO pedidoDTO,
            Authentication auth) {
        
        // Otro if-else para crear...
        if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("ADMIN no puede crear pedidos");
        } else if (auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"))) {
            // CLIENTE puede crear
            Pedido pedido = PedidoMapper.toEntity(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PedidoMapper.toDTO(pedidoService.save(pedido, auth)));
        }
        // ...
    }
}
```

**Problemas:**
- Código **repetitivo** (el if-else se repite en cada método)
- **Difícil de extender** (añadir un nuevo rol significa modificar todos los métodos)
- **Legibilidad pobre** (los ifs oscurecen la lógica de negocio)
- **Violación de SRP** (el controlador tiene más responsabilidades)

#### 5.1.4 Estructura: Diagrama UML

```
┌──────────────────────────────────┐
│  «interface» AuthorizationStrategy│  ← Interfaz común
├──────────────────────────────────┤
│ + authorize(): boolean            │
└──────────────────────────────────┘
              ▲            ▲
              │            │
┌─────────────────┐  ┌─────────────────┐
│  AdminStrategy  │  │ ClienteStrategy │
├─────────────────┤  ├─────────────────┤
│ Siempre permite │  │Solo si cliente  │
│                 │  │== dueño         │
│ +authorize():   │  │ +authorize():   │
│   return true   │  │   return owner  │
└─────────────────┘  └─────────────────┘

El cliente elige la strategy según el contexto:
  if (role == ADMIN)   → usar AdminStrategy
  if (role == CLIENTE) → usar ClienteStrategy
```

#### 5.1.5 Implementación en REALPRINT: SecurityRulesService

En REALPRINT, **`SecurityRulesService`** encapsula las estrategias de autorización:

> ⚠️ **Nota:** En el patrón Strategy clásico existe una interfaz común con múltiples clases concretas intercambiables en tiempo de ejecución. En REALPRINT, la implementación es una variante **declarativa**: `SecurityRulesService` es una única clase con varios métodos, y Spring Security selecciona el comportamiento correcto a través de `@PreAuthorize`/`@PostAuthorize`. El espíritu del patrón (encapsular estrategias de autorización intercambiables según el contexto) se mantiene íntegro.

```java
// ✅ STRATEGY EN REALPRINT
@Component("securityRules")  // Spring inyecta esta clase
@RequiredArgsConstructor
public class SecurityRulesService {  // ← Contiene las estrategias
    
    private final UsuarioRepository usuarioRepository;
    
    // ESTRATEGIA 1: ¿Puedo crear un pedido?
    public boolean canCreatePedido(Authentication authentication) {
        // Solo CLIENTE puede crear
        return hasAuthority(authentication, "ROLE_CLIENTE");
    }
    
    // ESTRATEGIA 2: ¿Puedo subir un archivo?
    public boolean canUploadFile(Authentication authentication) {
        // Solo CLIENTE puede subir
        return canCreatePedido(authentication);
    }
    
    // ESTRATEGIA 3: ¿Puedo leer este pedido?
    public boolean canReadPedido(Authentication authentication, PedidoDTO pedido) {
        if (pedido == null) {
            return false;
        }
        
        // ADMIN puede leer cualquier pedido
        if (hasAuthority(authentication, "ROLE_ADMIN")) {
            return true;  // Estrategia ADMIN: siempre permite
        }
        
        // CLIENTE solo puede leer sus propios pedidos
        Usuario currentUser = currentUser(authentication);
        return currentUser != null && Objects.equals(currentUser.getId(), pedido.getClienteId());
        // Estrategia CLIENTE: solo si es dueño
    }
    
    // ESTRATEGIA 4: ¿Puedo leer este usuario?
    public boolean canReadUsuario(Authentication authentication, UsuarioDTO usuario) {
        if (usuario == null) {
            return false;
        }
        
        // ADMIN puede leer cualquier usuario
        if (hasAuthority(authentication, "ROLE_ADMIN")) {
            return true;
        }
        
        // CLIENTE solo puede leer sus propios datos
        Usuario currentUser = currentUser(authentication);
        return currentUser != null && Objects.equals(currentUser.getId(), usuario.getId());
    }
    
    // Métodos auxiliares
    private boolean hasAuthority(Authentication authentication, String authority) {
        return authentication != null
                && authentication.getAuthorities() != null
                && authentication.getAuthorities().stream()
                .anyMatch(ga -> authority.equals(ga.getAuthority()));
    }
    
    private Usuario currentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            return null;
        }
        return usuarioRepository.findByUsername(authentication.getName()).orElse(null);
    }
}
```

**Cómo se usa en el controlador:**

```java
// ✅ CONTROLADOR LIMPIO — GRACIAS A STRATEGY
@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {
    
    private final PedidoService pedidoService;
    
    @GetMapping("/{id}")
    @PostAuthorize("@securityRules.canReadPedido(authentication, returnObject.body)")
    // ← Spring automáticamente elige la estrategia correcta
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);
        return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
        // Sin if-else, sin lógica de autorización aquí
    }
    
    @PostMapping
    @PreAuthorize("@securityRules.canCreatePedido(authentication)")
    // ← La estrategia se decide automáticamente
    public ResponseEntity<PedidoDTO> crearPedido(
            @RequestBody PedidoDTO pedidoDTO,
            Authentication auth) {
        Pedido pedido = PedidoMapper.toEntity(pedidoDTO);
        Pedido pedidoGuardado = pedidoService.save(pedido, auth);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PedidoMapper.toDTO(pedidoGuardado));
    }
    
    @GetMapping("/archivos/{fileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> descargarArchivo(@PathVariable String fileName) {
        // Solo ADMIN, punto
        // ...
    }
}
```

**¿Cómo funciona?**

1. `@PreAuthorize` y `@PostAuthorize` son anotaciones de Spring Security
2. Antes de ejecutar el método, Spring evalúa la expresión
3. `@securityRules` se refiere al bean `SecurityRulesService` (registrado con `@Component("securityRules")`)
4. Spring **elige automáticamente la estrategia correcta**:
   - Si es ADMIN → `AdminStrategy` (permite todo)
   - Si es CLIENTE → `ClienteStrategy` (sólo datos propios)

#### 5.1.6 Ventajas

1. **Flexibilidad:** Añade nuevas estrategias sin cambiar el código existente
2. **Legibilidad:** El código es claro (sin if-else gigantes)
3. **Mantenibilidad:** Cada estrategia está en su propio método/clase
4. **Testabilidad:** Fácil de testear cada estrategia por separado
5. **Extensibilidad:** Si añades un nuevo rol, solo añades un nuevo método
6. **Separación de responsabilidades:** Lógica de autorización separada de lógica de negocio

#### 5.1.7 Desventajas

1. **Overhead:** Más clases/métodos (aunque es mínimo)
2. **Complejidad inicial:** Para estrategias simples, puede parecer overkill
3. **Indirección:** A veces es difícil seguir qué estrategia se ejecuta

#### 5.1.8 Cuándo Usarlo / Cuándo No

**✅ USAR Strategy cuando:**
- Tienes múltiples formas de resolver el mismo problema
- La forma exacta se decide en tiempo de ejecución
- Tienes if-else gigantes que se repiten
- Necesitas agregar nuevas estrategias frecuentemente
- Quieres mantener el código limpio y legible

**❌ NO USAR cuando:**
- Solo hay una forma de hacer algo
- Las estrategias son triviales
- El costo de abstracción es mayor que el beneficio

#### 5.1.9 Patrones Relacionados

- **State:** Similar, pero State es para cambiar el comportamiento cuando cambia el estado interno
- **Template Method:** Define un algoritmo; Strategy lo encapsula completamente
- **Decorator:** Ambos permiten cambios en tiempo de ejecución
- **Factory:** Puede usarse para crear las estrategias correctas

<a id="template-method"></a>
### 5.2 Template Method

#### 5.2.1 Nombre y Categoría

**Patrón:** Template Method  
**Categoría:** De Comportamiento  
**Propósito:** Definir el esqueleto de un algoritmo en una clase base, dejando que las subclases implementen pasos específicos sin cambiar la estructura general del algoritmo.

#### 5.2.2 Intención / Propósito

El patrón Template Method resuelve este problema: **¿Cómo definir un proceso con pasos repetidos, pero permitiendo que cada paso se implemente de forma diferente?**

En REALPRINT, cuando ocurre un error, necesitas:
1. Registrarlo (logging)
2. Convertirlo a una estructura consistente
3. Devolver una respuesta HTTP apropiada

Este "esqueleto" es igual para todos los errores. Template Method define ese flujo.

#### 5.2.3 Motivación: Problema sin Patrón

Sin Template Method, tu manejo de errores sería inconsistente:

```java
// ❌ SIN TEMPLATE METHOD — PROBLEMA (código repetitivo)
@RestController
public class PedidoController {
    
    @GetMapping("/pedidos/{id}")
    public ResponseEntity<?> obtenerPedido(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
        } catch (PedidoNoEncontradoException ex) {
            log.warn("Pedido no encontrado: {}", ex.getMessage());
            Map<String, Object> response = new HashMap<>();
            response.put("status", 404);
            response.put("message", "Pedido no encontrado");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception ex) {
            log.error("Error inesperado", ex);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 500);
            response.put("message", "Error interno del servidor");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    @PostMapping("/pedidos")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO dto) {
        try {
            Pedido pedido = PedidoMapper.toEntity(dto);
            Pedido guardado = pedidoService.save(pedido, auth);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(PedidoMapper.toDTO(guardado));
        } catch (UnauthorizedException ex) {
            log.warn("Acceso no autorizado: {}", ex.getMessage());
            // ¡Mismo código otra vez!
            Map<String, Object> response = new HashMap<>();
            response.put("status", 401);
            response.put("message", "No autorizado");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception ex) {
            // ¡Y otra vez!
            log.error("Error inesperado", ex);
            Map<String, Object> response = new HashMap<>();
            response.put("status", 500);
            response.put("message", "Error interno del servidor");
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

// El mismo patrón se repite en CADA controlador...
```

**Problemas:**
- Código **altamente repetitivo**
- Inconsistencias si un controlador olvida un paso
- **Difícil de mantener** (cambiar la estructura de error significa modificar todos los controllers)
- **Violación de DRY** (Don't Repeat Yourself)

#### 5.2.4 Estructura: Diagrama UML

```
┌─────────────────────────────────────────┐
│         ExceptionHandler (Clase Base)    │  ← Define el esqueleto
├─────────────────────────────────────────┤
│ # template_method()                     │  ESQUELETO
│   1) Log the error                      │  del algoritmo
│   2) Get HTTP status                   │
│   3) Build response structure           │
│   4) Return ResponseEntity              │
│ # abstract handle(Exception)            │  PASO VARIABLE
└─────────────────────────────────────────┘
         ↑
         │
  ╭──────────────────────╮
  │     PedidoNoEncontradoHandler         │  SUBCLASES
  ├────────────────────────┤  implementan
  │ # handle(Exception)                   │  PASOS
  │   return 404 NOT_FOUND                │  ESPECÍFICOS
  └──────────────────────╯

El template_method() es igual para todos;
lo que varía es el handle()
```

#### 5.2.5 Implementación en REALPRINT: GlobalExceptionHandler

En REALPRINT, **`GlobalExceptionHandler`** implementa Template Method:

> ⚠️ **Nota:** El Template Method clásico requiere herencia: una clase abstracta con un método plantilla que delega pasos específicos a subclases. En REALPRINT, `GlobalExceptionHandler` no usa herencia formal; el "template" es una convención de código que todos los `@ExceptionHandler` respetan (log → status HTTP → estructura de respuesta → retorno). Es una aplicación **conceptual** del patrón: Spring gestiona la selección y despacho internamente, sin necesidad de subclases explícitas.

```java
// ✅ TEMPLATE METHOD EN REALPRINT
@RestControllerAdvice  // ← Anotación especial que intercepta excepciones globalmente
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Este es el "template" (esqueleto):
     * 1. Registrar el error
     * 2. Crear estructura de respuesta consistente
     * 3. Retornar ResponseEntity con estado HTTP correcto
     * 
     * Lo que VARIA es el método concreto que se ejecuta.
     */
    
    // PASO 1: Manejar excepciones de "Pedido no encontrado"
    @ExceptionHandler(PedidoNoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handlePedidoNoEncontrado(
            PedidoNoEncontradoException ex) {
        
        // ESQUELETO aplicado:
        log.warn("Pedido no encontrado: {}", ex.getMessage());  // Paso 1: Log
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)  // Paso 2: Status HTTP
                .body(ErrorResponse.builder()  // Paso 3: Estructura consistente
                        .status(HttpStatus.NOT_FOUND.value())
                        .message("Pedido no encontrado")
                        .error(ex.getMessage())
                        .build());  // Paso 4: Respuesta
    }
    
    // PASO 2: Manejar excepciones de autorización
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            UnauthorizedException ex) {
        
        // MISMO ESQUELETO, diferente implementación
        log.warn("Acceso no autorizado: {}", ex.getMessage());  // Paso 1: Log
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)  // Paso 2: Status HTTP
                .body(ErrorResponse.builder()  // Paso 3: Estructura consistente
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .message("No autorizado")
                        .error(ex.getMessage())
                        .build());  // Paso 4: Respuesta
    }
    
    // PASO 3: Manejar excepciones genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex) {
        
        // MISMO ESQUELETO otra vez
        log.error("Error no esperado", ex);  // Paso 1: Log (a nivel ERROR)
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)  // Paso 2: Status HTTP
                .body(ErrorResponse.builder()  // Paso 3: Estructura consistente
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Error interno del servidor")
                        .error(ex.getMessage() != null ? ex.getMessage() : "Error desconocido")
                        .build());  // Paso 4: Respuesta
    }
    
    /**
     * Estructura común para TODAS las respuestas de error.
     * Se reutiliza en todos los @ExceptionHandler.
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class ErrorResponse {
        private int status;
        private String message;
        private String error;
    }
}
```

**¿Cómo funciona?**

1. Cuando ocurre una excepción en cualquier controlador, Spring la intercepta
2. Spring busca un `@ExceptionHandler` que maneje ese tipo de excepción
3. El `@ExceptionHandler` sigue el **esqueleto** (template):
   - Registra el error
   - Determina el HTTP status correcto
   - Construye la respuesta en formato consistente
   - Devuelve la respuesta

**Ejemplo de flujo:**

```java
@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    
    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.findById(id);  // Puede lanzar PedidoNoEncontradoException
        return ResponseEntity.ok(PedidoMapper.toDTO(pedido));
    }
}

// Si se lanza PedidoNoEncontradoException:
// 1. Spring intercepta la excepción
// 2. GlobalExceptionHandler.handlePedidoNoEncontrado() se ejecuta
// 3. Devuelve un JSON consistente:
// {
//   "status": 404,
//   "message": "Pedido no encontrado",
//   "error": "Pedido no encontrado con id: 999"
// }
```

#### 5.2.6 Ventajas

1. **Consistencia:** Todas las respuestas de error siguen el mismo formato
2. **Reutilización:** No repites el esqueleto en cada controlador
3. **Mantenibilidad:** Cambios en el manejo de errores en UN SOLO LUGAR
4. **Legibilidad:** Los controladores son simples; la lógica de error está centralizada
5. **Escalabilidad:** Añadir nuevos tipos de excepciones es fácil
6. **Separación de responsabilidades:** Lógica de negocio separada de manejo de errores

#### 5.2.7 Desventajas

1. **Abstracción:** Puede ser difícil de seguir para principiantes (dónde se maneja la excepción)
2. **Indirección:** El flujo no es obvio leyendo el controlador
3. **Limitaciones:** Algunos casos especiales podrían no encajar en el template

#### 5.2.8 Cuándo Usarlo / Cuándo No

**✅ USAR Template Method cuando:**
- Tienes algoritmos con pasos comunes pero diferentes detalles
- Necesitas evitar duplicación de código
- Los cambios de comportamiento deben poder hacerse en subclases
- Trabajas con manejo centralizado de errores (como en Spring)
- La estructura del algoritmo es más importante que la flexibilidad

**❌ NO USAR cuando:**
- Solo hay un tipo de comportamiento
- El algoritmo es trivial
- Necesitas libertad total en cada implementación (usa Strategy en su lugar)

#### 5.2.9 Patrones Relacionados

- **Strategy:** Similar, pero Strategy permite elegir algoritmos completos; Template Method define la estructura
- **Factory Method:** Frecuentemente usado dentro de Template Method
- **Hook Method:** Variación de Template Method que permite customización
- **Decorator:** Ambos permiten extender comportamiento

---

<a id="conclusión"></a>
## 6. Conclusión y Síntesis

### 6.1 Recapitulación de los Seis Patrones

A lo largo de este manual, hemos explorado seis patrones de diseño presentes en REALPRINT, distribuidos en las tres categorías clásicas:

**Patrones Creacionales:**
- **Singleton:** Garantiza una única instancia de un objeto. En REALPRINT, los servicios anotados con `@Service` son Singletons manejados por Spring. Ejemplo: `FileStorageService`.
- **Builder:** Permite construir objetos complejos paso a paso con una interfaz fluida. En REALPRINT, Lombok genera automáticamente builders para DTOs y entidades. Ejemplo: `PedidoDTO.builder()`.

**Patrones Estructurales:**
- **Facade:** Proporciona una interfaz simplificada a un subsistema complejo. En REALPRINT, `PedidoService` y `AuthService` actúan como facades ocultando la complejidad de repositorios y validaciones.
- **Data Transfer Object (DTO) / Adapter:** Convierte entre formatos incompatibles (Entity ↔ DTO). En REALPRINT, `PedidoMapper` traduce entre el modelo de BD (`Pedido`) y el modelo de API (`PedidoDTO`), incluyendo conversiones de tipos (ENUM ↔ String).

**Patrones de Comportamiento:**
- **Strategy:** Define familias de algoritmos intercambiables según el contexto. En REALPRINT, `SecurityRulesService` encapsula diferentes estrategias de autorización (ADMIN vs. CLIENTE).
- **Template Method:** Define el esqueleto de un algoritmo, permitiendo que pasos específicos varién. En REALPRINT, `GlobalExceptionHandler` implementa un template consistente para manejar todas las excepciones de forma uniforme.

### 6.2 Conexiones entre Patrones

Más allá de clasificarlos en categorías, estos patrones interactúan y se refuerzan:

1. **Singleton + Facade + DI (Inyección de Dependencias):**
   Los servicios singleton de Spring son facades que abstraen complejidad. La inyección de dependencias hace que sean accesibles sin usar getters estáticos.

2. **Builder + DTO + Mapper:**
   Los builders construyen DTOs de forma legible. El mapper usa builders para convertir entities a DTOs sin código boilerplate.

3. **Strategy + Annotations + Spring Security:**
   Spring Security interpreta anotaciones como `@PreAuthorize` y ejecuta métodos de `SecurityRulesService`. Esto implementa Strategy de forma declarativa (annotations) en lugar de imperativa (ifs).

4. **Template Method + Centralized Error Handling:**
   El `GlobalExceptionHandler` define un template para errores. Cualquier excepción en cualquier parte de la aplicación sigue automáticamente este template.

5. **Facade + Template Method:**
   Los servicios (facades) pueden usar template methods para estandarizar procesos comunes (búsqueda, validación, guardado).

### 6.3 Beneficios de Aplicar Estos Patrones en REALPRINT

**Mantenibilidad:**
- Los patrones hacen que el código sea más predecible. Un nuevo desarrollador puede entender rápidamente qué hace cada componente.
- Los cambios se localizan en un lugar (Principio de Responsabilidad Única).

**Escalabilidad:**
- Añadir nuevos roles de usuario es simple con Strategy.
- Añadir nuevos tipos de excepciones es simple con Template Method.
- Crear nuevos tipos de órdenes es simple con Builder.

**Testabilidad:**
- Los servicios (facades) inyectables son fáciles de mockear en tests.
- Las estrategias pueden probarse independientemente.
- Los mappers pueden validarse sin tocar BD.

**Profesionalismo:**
- Usar patrones reconocidos comunica claramente las intenciones del código.
- Otros desarrolladores entienden inmediatamente la arquitectura.

### 6.4 Lecciones Clave

1. **Los patrones resuelven problemas reales.** No son academicismos; vienen de necesidades prácticas.
2. **Los patrones trabajan mejor juntos.** Raras veces usas un patrón aislado.
3. **No abuses de los patrones.** Si el código es simple, mantente simple. Los patrones tienen un costo (mayor complejidad).
4. **Los frameworks como Spring implementan patrones automáticamente.** `@Service` → Singleton, `@Builder` → Builder Pattern. Conocer los patrones te ayuda a entender qué hace Spring bajo el capó.
5. **El refactoring vale la pena.** Cambiar de código desordenado a código estructurado con patrones es esfuerzo bien invertido.

### 6.5 Próximos Pasos

Ahora que entiendes estos patrones en el contexto de REALPRINT, considera:

- **Estudiar otros patrones** de las categorías no exploradas (Factory Method, Adapter, Observer, State, etc.).
- **Refactorizar tu código** identificando dónde podrías aplicar patrones para mejorar.
- **Leer el código de librerías populares** (Spring, Hibernate, JUnit) y reconocer los patrones que usan.
- **Practicar implementando patrones** en pequeños proyectos.
- **Participar en code reviews** compartiendo conocimiento sobre patrones con tu equipo.

---

<a id="reflexion-personal"></a>
## 7. Reflexión Personal - Mis Aprendizajes

### 7.1 Lo Aprendido

Tras completar este manual, mi comprensión de los patrones evolucionó través de varias fases:

1. **Los patrones resuelven problemas reales:** No son académicos. Trabajando con REALPRINT, vi que:
   - **Singleton** existe porque necesito que solo una instancia gestione los archivos
   - **Builder** existe porque los DTOs tienen muchos campos opcionales
   - **Facade** existe porque los controladores no deberían saber todos los detalles internos
   - **Strategy** existe porque necesito autorización diferente según el rol
   - **Template Method** existe porque manejar errores siempre sigue el mismo flujo

2. **Los patrones trabajan juntos:** No actúan aislados. Por ejemplo:
   - El **Builder** construye los DTOs que el **Adapter** convierte
   - La **Facade** usa **Strategy** internamente para decisiones
   - El **Singleton** en Spring se combina automáticamente con **Inyección de Dependencias**

3. **Spring implementa patrones automáticamente:** Anotaciones como `@Service` son Singleton, `@Builder` es el patrón Builder, `@RestControllerAdvice` es Template Method. Entender patrones significa entender qué hace Spring bajo el capó.

4. **Claridad sobre complejidad:** Un patrón bien usado hace el código más legible. Un patrón sobreutilizado lo hace confuso. La clave está en el equilibrio y en la estructura "problema → solución" que ayuda a entender el *por qué*.

### 7.2 Reconociendo Patrones

Ahora puedo identificar patrones en varios contextos:

**En REALPRINT:**
- `@Service` = Singleton + Facade
- `PedidoMapper` = Adapter
- `SecurityRulesService` = Strategy
- `GlobalExceptionHandler` = Template Method
- `@Builder` (Lombok) = Builder

**En código general:**
- Clase con `getInstance()` estática = Singleton
- Cualquier clase que convierta formatos = Adapter
- Cualquier servicio simplificador = Facade
- Clase base con métodos abstractos = Template Method
- Estructura con opciones intercambiables = Strategy

### 7.3 Lecciones Clave

1. **Los patrones no se memorizan, se entienden** — No memoricé "getInstance()", entendí *por qué* existe.
2. **Patrón ≠ complejidad** — Si el código se vuelve más complejo, probablemente no es el patrón correcto.
3. **Spring economy** — Los frameworks tienen decisiones inteligentes basadas en patrones. Esto no es "trampa", es buena arquitectura.
4. **Anti-patrones existen** — No todo necesita un patrón; simple es mejor que complejo (a menos que lo simple sea un caos).

### 7.4 Reflexión Final

Este trabajo me enseñó más a través de la **lectura crítica de código real** que a través de teoría pura. Aprendí a:

- Leer código preguntándome: "¿Por qué está estructurado así?" y "¿Qué patrón está aquí?"
- Escribir código pensando en futuro: "¿Será difícil cambiar esto?" y "¿Alguien más lo entenderá?"
- Apreciar el trabajo de otros desarrolladores en frameworks populares (Spring, Hibernate), cuyas decisiones inteligentes a menudo están basadas en patrones

**Conclusión:** Los patrones de diseño no son lujos académicos; son herramientas prácticas que hacen que el código sea más mantenible, flexible y profesional. Trabajar con REALPRINT y estos 6 patrones me ha dado confianza para reconocerlos, aplicarlos y defenderlos.

---

<a id="llms"></a>
## 8. Declaración de Uso de LLMs/IA

En conformidad con los requisitos de la tarea, este manual incluye la siguiente declaración sobre el uso de herramientas de IA generativa:

### 8.1 Herramientas IA Utilizadas

- **Claude (Anthropic)** - Utilizado como herramienta de apoyo para:
   - Generar estructuras de diagrama UML en texto (formato ASCII)
   - Sugerir mejoras en la redacción de explicaciones
   - Revisar la coherencia del documento
   - Generar ejemplos de código alternativo para comparar

### 8.2 Partes Concretas donde se Utilizó IA

**Redacción:**
- Reformulación de explicaciones para hacerlas más didácticas (ej: "Problema sin Patrón" en cada sección)
- Sugerencias de estructura del documento (índice, apartados)
- Revisión de coherencia y claridad de lenguaje

**Diagramas:**
- Generación de diagramas UML en formato ASCII (ya que no tenemos herramientas gráficas)
- Mejora visual de su legibilidad

**Ejemplos de Código:**
- Generación de código Java adicional para mostrar variantes (ej: Singleton manual vs. Spring Singleton)
- Sugerencias de cómo estructurar fragmentos para máxima claridad

**Verificación:**
- Revisión técnica de que los ejemplos sean correctos
- Validación de que los diagramas UML correspondan al código mostrado

### 8.3 Partes de Autoría Claramente Humana

**Selección de patrones:**
- Decisión de documentar exactamente estos 6 patrones de entre todos los disponibles
- Razones específicas por las que cada patrón está presente en REALPRINT

**Análisis del código real de REALPRINT:**
- Lectura y comprensión profunda del código fuente
- Identificación de dónde y cómo se aplicaban los patrones
- Extracción de fragmentos reales del proyecto

**Estructura didáctica "Problema → Solución":**
- Concepto de mostrar primero el problema sin patrón (código desordenado/ineficiente)
- Luego contrastar con la solución usando el patrón
- Esta progresión NO fue generada por IA; es una decisión pedagógica personal

**Explicaciones en palabras propias:**
- Todas las explicaciones fueron redactadas manualmente
- No son copias de Refactoring Guru u otras fuentes
- El tono, ejemplos y enfoque son personalizados para REALPRINT

**Casos de uso realistas:**
- Los ejemplos no son genéricos; están contextualizados en REALPRINT
- Las situaciones del "mundo real" mencionadas (ej: gestión de archivos, autenticación de usuarios) vienen de la experiencia con el proyecto

**Decisiones técnicas:**
- Qué incluir en cada sección (Motivación, Estructura UML, Ventajas/Desventajas, Casos de Uso)
- Cómo equilibrar profundidad con accesibilidad
- Qué relaciones mostrar entre patrones

### 8.4 Resumen del Uso Responsable de IA

La IA se utilizó como **herramienta de apoyo**, no de sustitución:

✅ **Lo que hizo IA:**
- Generar alternativas para evaluación
- Revisar coherencia y correcciones gramaticales
- Sugerir formatos visuales

✅ **Lo que hice yo:**
- Elegir qué patrones incluir (basándome en comprensión real del código)
- Leer y analizar el código de REALPRINT en profundidad
- Escribir todas las explicaciones en mis propias palabras
- Estructurar el documento de forma didáctica
- Tomar decisiones sobre qué mostrar y por qué
- Verificar que cada ejemplo corresponda exactamente con REALPRINT

### 8.5 Capacidad de Defender el Trabajo

Puedo explicar con seguridad:

- **Qué es cada patrón y por qué importa**
- **Dónde exactamente está cada patrón en REALPRINT** (línea de código, clase, anotación)
- **Cómo cada patrón resuelve un problema específico**
- **Cuál sería el código sin el patrón** (problema)
- **Por qué la solución con patrón es mejor**
- **Cómo modificar cada ejemplo** en tiempo de defensa (ej: añadir una nueva estrategia a SecurityRulesService)
- **Las conexiones entre patrones** y cómo trabajan juntos

---

**Fin del manual.**

---

## 📎 Apéndice: Referencias

- **Refactoring Guru - Design Patterns:** https://refactoring.guru/design-patterns
- **Gang of Four (GoF):** "Design Patterns: Elements of Reusable Object-Oriented Software" (1994)
- **Spring Framework Documentation:** https://spring.io/projects/spring-framework
- **Código fuente de REALPRINT:** Com.realprint.realprintbackend (backend del proyecto)


