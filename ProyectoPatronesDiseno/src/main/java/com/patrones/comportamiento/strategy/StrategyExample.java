package com.patrones.comportamiento.strategy;

/**
 * ============================================================
 *  DEMO — Punto de entrada y demostración del patrón Strategy
 * ============================================================
 *
 * Esta clase muestra cómo el patrón Strategy permite que el mismo
 * servicio ({@link SecurityService}) se comporte de forma diferente
 * según el rol asignado, sin usar condicionales en cada operación.
 *
 * Flujo del patrón en acción:
 *   1. Se crea un SecurityService con un rol → se asigna la estrategia.
 *   2. Se llaman los métodos del servicio → delega a la estrategia.
 *   3. La estrategia responde según su lógica interna.
 *   4. El código cliente nunca sabe qué clase concreta se usó.
 */
public class StrategyExample {

    public static void main(String[] args) {
        System.out.println("=== PATRÓN STRATEGY ===\n");

        // ============================================================
        //  CASO 1: Rol ADMIN
        //  → SecurityService asignará internamente AdminStrategy
        //  → Todos los métodos usarán la lógica de AdminStrategy
        // ============================================================
        System.out.println("--- Caso 1: Usuario ADMIN ---\n");

        // Al pasar "ADMIN", el SecurityService instancia AdminStrategy
        // El código cliente no interactúa con AdminStrategy directamente
        SecurityService adminSecurity = new SecurityService("ADMIN");

        // Test 1: Lectura — ADMIN puede leer cualquier pedido
        // userId=100 quiere leer el pedido de ownerId=200 (son distintos)
        // AdminStrategy.canReadOrder() ignora los IDs y devuelve true siempre
        System.out.println("1. ¿ADMIN puede leer pedido (userId=100, ownerId=200)?");
        System.out.println("   Respuesta: " + adminSecurity.canReadOrder(100L, 200L));
        System.out.println("   ADMIN puede leer cualquier pedido\n");

        // Test 2: Creación — ADMIN NO puede crear pedidos (regla de negocio)
        // AdminStrategy.canCreateOrder() devuelve false siempre
        System.out.println("2. ¿ADMIN puede crear pedido?");
        System.out.println("   Respuesta: " + adminSecurity.canCreateOrder("ADMIN"));
        System.out.println("   ADMIN NO puede crear pedidos\n");

        // Test 3: Eliminación — ADMIN puede eliminar cualquier pedido
        // AdminStrategy.canDeleteOrder() devuelve true siempre
        System.out.println("3. ¿ADMIN puede eliminar pedido?");
        System.out.println("   Respuesta: " + adminSecurity.canDeleteOrder("ADMIN"));
        System.out.println("   ADMIN puede eliminar cualquier pedido\n");

        // ============================================================
        //  CASO 2: Rol CLIENTE
        //  → SecurityService asignará internamente ClienteStrategy
        //  → Mismos métodos, comportamiento completamente distinto
        //  Aquí se ve la potencia del patrón Strategy:
        //  mismo código cliente, diferente resultado según la estrategia
        // ============================================================
        System.out.println("--- Caso 2: Usuario CLIENTE ---\n");

        // Al pasar "CLIENTE", el SecurityService instancia ClienteStrategy
        SecurityService clienteSecurity = new SecurityService("CLIENTE");

        // Test 1: Lectura de su PROPIO pedido — debe devolver true
        // userId=100, ownerId=100 → son iguales → ClienteStrategy permite la lectura
        System.out.println("1. ¿CLIENTE puede leer su propio pedido (userId=100, ownerId=100)?");
        System.out.println("   Respuesta: " + clienteSecurity.canReadOrder(100L, 100L));
        System.out.println("   CLIENTE puede leer sus propios pedidos\n");

        // Test 2: Lectura de pedido AJENO — debe devolver false
        // userId=100, ownerId=200 → son distintos → ClienteStrategy deniega
        System.out.println("2. ¿CLIENTE puede leer pedido de otro (userId=100, ownerId=200)?");
        System.out.println("   Respuesta: " + clienteSecurity.canReadOrder(100L, 200L));
        System.out.println("   CLIENTE NO puede leer pedidos ajenos\n");

        // Test 3: Creación — CLIENTE sí puede crear pedidos
        // ClienteStrategy.canCreateOrder() devuelve true siempre
        System.out.println("3. ¿CLIENTE puede crear pedido?");
        System.out.println("   Respuesta: " + clienteSecurity.canCreateOrder("CLIENTE"));
        System.out.println("   CLIENTE puede crear pedidos\n");

        // Test 4: Eliminación — CLIENTE NO puede eliminar
        // ClienteStrategy.canDeleteOrder() devuelve false siempre
        System.out.println("4. ¿CLIENTE puede eliminar pedido?");
        System.out.println("   Respuesta: " + clienteSecurity.canDeleteOrder("CLIENTE"));
        System.out.println("   CLIENTE NO puede eliminar\n");

        // ============================================================
        //  CONCLUSIÓN del patrón Strategy
        // ============================================================
        System.out.println("  Conclusión: Strategy permite diferentes comportamientos");
        System.out.println("   según el rol (contexto) sin usar if-else gigantes.");
        System.out.println("   Cada estrategia está encapsulada en su propia clase.\n");
        // Beneficios observados en este ejemplo:
        // → SecurityService no tiene if-else para cada operación
        // → Añadir un rol nuevo (ej: MODERADOR) solo requiere crear una nueva clase
        // → Cada estrategia es testeable de forma independiente y aislada
        // → Cambio de comportamiento en tiempo de ejecución sin alterar el contexto
    }
}