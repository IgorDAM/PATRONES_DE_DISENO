package com.patrones.comportamiento.strategy;

/**
 * Demostración del patrón Strategy
 */
public class StrategyExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN STRATEGY ===\n");
        
        // Caso 1: ADMIN intenta realizar acciones
        System.out.println("--- Caso 1: Usuario ADMIN ---\n");
        StrategyClasses.SecurityService adminSecurity = 
            new StrategyClasses.SecurityService("ADMIN");
        
        System.out.println("1. ¿ADMIN puede leer pedido (userId=100, ownerId=200)?");
        System.out.println("   Respuesta: " + adminSecurity.canReadOrder(100L, 200L));
        System.out.println("   ✅ ADMIN puede leer cualquier pedido\n");
        
        System.out.println("2. ¿ADMIN puede crear pedido?");
        System.out.println("   Respuesta: " + adminSecurity.canCreateOrder("ADMIN"));
        System.out.println("   ❌ ADMIN NO puede crear pedidos\n");
        
        System.out.println("3. ¿ADMIN puede eliminar pedido?");
        System.out.println("   Respuesta: " + adminSecurity.canDeleteOrder("ADMIN"));
        System.out.println("   ✅ ADMIN puede eliminar cualquier pedido\n");
        
        // Caso 2: CLIENTE intenta realizar acciones
        System.out.println("--- Caso 2: Usuario CLIENTE ---\n");
        StrategyClasses.SecurityService clienteSecurity = 
            new StrategyClasses.SecurityService("CLIENTE");
        
        System.out.println("1. ¿CLIENTE puede leer su propio pedido (userId=100, ownerId=100)?");
        System.out.println("   Respuesta: " + clienteSecurity.canReadOrder(100L, 100L));
        System.out.println("   ✅ CLIENTE puede leer sus propios pedidos\n");
        
        System.out.println("2. ¿CLIENTE puede leer pedido de otro (userId=100, ownerId=200)?");
        System.out.println("   Respuesta: " + clienteSecurity.canReadOrder(100L, 200L));
        System.out.println("   ❌ CLIENTE NO puede leer pedidos ajenos\n");
        
        System.out.println("3. ¿CLIENTE puede crear pedido?");
        System.out.println("   Respuesta: " + clienteSecurity.canCreateOrder("CLIENTE"));
        System.out.println("   ✅ CLIENTE puede crear pedidos\n");
        
        System.out.println("4. ¿CLIENTE puede eliminar pedido?");
        System.out.println("   Respuesta: " + clienteSecurity.canDeleteOrder("CLIENTE"));
        System.out.println("   ❌ CLIENTE NO puede eliminar\n");
        
        System.out.println("✅ Conclusión: Strategy permite diferentes comportamientos");
        System.out.println("   según el rol (contexto) sin usar if-else gigantes");
        System.out.println("   Cada estrategia está encapsulada en su propia clase.\n");
    }
}
