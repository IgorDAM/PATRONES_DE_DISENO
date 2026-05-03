package com.patrones.creacionales.builder;

/**
 * Clase de demostración del patrón Builder.
 *
 * Muestra tres variantes de construcción de un {@link Order}:
 * completo, mínimo y parcial — todas usando la misma API fluida del Builder.
 * El objetivo es evidenciar por qué Builder mejora la legibilidad
 * frente a constructores tradicionales con múltiples parámetros.
 */
public class BuilderExample {

    public static void main(String[] args) {
        System.out.println("=== PATRÓN BUILDER ===\n");

        // -------------------------------------------------------
        // Caso 1: Pedido COMPLETO — todos los campos informados.
        // Cada línea del builder es autoexplicativa: se sabe qué
        // valor se asigna a qué campo sin necesidad de consultar
        // la firma del constructor.
        // -------------------------------------------------------
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
                .build(); // build() valida y construye el objeto final inmutable

        System.out.println(order1);
        System.out.println("\n✅ Fácil de leer: cada campo tiene su significado claro");

        // -------------------------------------------------------
        // Caso 2: Pedido MÍNIMO — solo los campos indispensables.
        // Los campos no especificados quedan como null automáticamente.
        // Con un constructor tradicional habría que pasar null explícitamente
        // en cada posición, lo que resulta ilegible y propenso a errores.
        // -------------------------------------------------------
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

        // -------------------------------------------------------
        // Caso 3: Pedido PARCIAL — campos intermedios.
        // Demuestra la flexibilidad del Builder: se usan exactamente
        // los campos necesarios, sin condicionantes de orden ni sobrecarga
        // de constructores (el antipatrón "telescoping constructor").
        // -------------------------------------------------------
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

        // Resumen de beneficios del patrón aplicados en este ejemplo:
        // → Legibilidad: cada campo tiene nombre explícito en la construcción.
        // → Flexibilidad: se incluyen solo los campos necesarios en cada caso.
        // → Mantenibilidad: añadir un campo nuevo a Order no rompe las construcciones existentes.
        System.out.println("\n✅ Conclusión: Builder permite construir objetos complejos");
        System.out.println("   de forma legible y flexible.\n");
    }
}