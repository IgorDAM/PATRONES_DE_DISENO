package com.patrones.creacionales.builder;

/**
 * Demostración del patrón Builder
 */
public class BuilderExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN BUILDER ===\n");
        
        // Construcción de un pedido completo
        System.out.println("1. Construcción de un Pedido COMPLETO:\n");
        Order order1 = Order.builder()
                .id(1L)
                .clientName("Juan García")
                .service("Serigrafía")
                .description("Imprimir logo en 100 camisetas")
                .quantity(100)
                .price(250.50)
                .status("pendiente")
                .notes("Urgente - entregar en 5 días")
                .build();
        
        System.out.println(order1);
        System.out.println("\n✅ Fácil de leer: cada campo tiene su significado claro");
        
        // Construcción de un pedido MÍNIMO
        System.out.println("\n2. Construcción de un Pedido MÍNIMO:\n");
        Order order2 = Order.builder()
                .clientName("María López")
                .service("Planchado")
                .quantity(50)
                .build();
        
        System.out.println(order2);
        System.out.println("\n✅ Sin Builder, tendrías que usar: ");
        System.out.println("   new Order(2L, \"María López\", \"Planchado\", null, 50, null, null, null)");
        System.out.println("   ¡Ilegible! ¿Qué significan esos nulls?");
        
        // Construcción de otro pedido
        System.out.println("\n3. Construcción de otro Pedido:\n");
        Order order3 = Order.builder()
                .id(3L)
                .clientName("Carlos Fernández")
                .service("Bordado")
                .description("Bordar iniciales en 200 gorras")
                .quantity(200)
                .price(500.00)
                .status("en_proceso")
                .build();
        
        System.out.println(order3);
        
        System.out.println("\n✅ Conclusión: Builder permite construir objetos complejos");
        System.out.println("   de forma legible y flexible.\n");
    }
}
