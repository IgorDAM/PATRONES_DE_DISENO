package com.patrones.estructurales.facade;

/**
 * Demostración del patrón Facade
 */
public class FacadeExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN FACADE ===");
        System.out.println("Simula procesamiento de pedidos sin y con Facade\n");
        
        OrderProcessingFacade facade = new OrderProcessingFacade();
        
        // Caso 1: Pedido válido
        System.out.println("--- Caso 1: Pedido válido ---");
        facade.processOrder("Juan García", 100);
        
        // Caso 2: Pedido inválido (nombre vacío)
        System.out.println("\n--- Caso 2: Pedido inválido (sin nombre) ---");
        facade.processOrder("", 50);
        
        // Caso 3: Pedido inválido (cantidad negativa)
        System.out.println("\n--- Caso 3: Pedido inválido (cantidad negativa) ---");
        facade.processOrder("María López", -10);
        
        // Caso 4: Otro pedido válido
        System.out.println("\n--- Caso 4: Pedido válido ---");
        facade.processOrder("Carlos Fernández", 200);
        
        System.out.println("\n✅ Conclusión: La Facade ocultó la complejidad interna");
        System.out.println("   El cliente (nosotros) solo llamamos a processOrder()");
        System.out.println("   Sin Facade, tendríamos que:");
        System.out.println("   1. Crear validator, repository, notificationService, auditService");
        System.out.println("   2. Llamar a cada uno en orden correcto");
        System.out.println("   3. Manejar errores en cada paso");
        System.out.println("   ¡Todo eso está oculto en la Facade!\n");
    }
}
