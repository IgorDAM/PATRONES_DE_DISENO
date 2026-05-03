package com.patrones.creacionales.builder;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Clase producto del patrón Builder.
 *
 * Representa el objeto complejo que se va a construir paso a paso.
 * Todos sus campos son opcionales excepto los que la lógica de negocio exija,
 * lo que hace al Builder ideal: evita constructores con muchos parámetros nullables.
 *
 * Las tres anotaciones de Lombok generan automáticamente en tiempo de compilación:
 *   @Getter   → un método get por cada campo (getId(), getClientName(), etc.)
 *   @ToString → un método toString() con todos los campos (útil para logs y depuración)
 *   @Builder  → la clase estática interna OrderBuilder con sus métodos encadenables
 *
 * Sin Lombok, habría que escribir todo ese código a mano — @Builder evita ~50 líneas extra.
 */
@Getter
@ToString
@Builder
public class Order {

    // Identificador único del pedido. Opcional al construir (puede generarlo la BD).
    private Long id;

    // Nombre del cliente que realizó el pedido.
    private String clientName;

    // Tipo de servicio solicitado (ej: "Serigrafía", "Bordado", "Planchado").
    private String service;

    // Descripción detallada del trabajo. Campo opcional.
    private String description;

    // Número de unidades del pedido.
    private Integer quantity;

    // Precio total del pedido. Opcional hasta que se calcule o confirme.
    private Double price;

    // Estado actual del pedido (ej: "pendiente", "en_proceso", "completado").
    private String status;

    // Anotaciones adicionales del pedido. Campo completamente opcional.
    private String notes;
}