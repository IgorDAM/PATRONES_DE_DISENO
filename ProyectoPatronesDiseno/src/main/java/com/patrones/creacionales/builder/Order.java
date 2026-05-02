package com.patrones.creacionales.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * PATRÓN BUILDER - Ejemplo Didáctico
 * 
 * Propósito: Permitir la construcción de objetos complejos paso a paso,
 * facilitando la creación de objetos con muchos parámetros opcionales.
 * 
 * En este ejemplo, construimos un Pedido con múltiples campos opcionales.
 */
@Getter
@ToString
@Builder
public class Order {
    
    private Long id;
    private String clientName;
    private String service;
    private String description;
    private Integer quantity;
    private Double price;
    private String status;
    private String notes;
    
    // Lombok genera automáticamente:
    // - Un constructor privado
    // - Una clase interna Order.OrderBuilder
    // - Métodos fluidos: id(Long), clientName(String), etc.
    // - Un método build() que construye el Order
}
