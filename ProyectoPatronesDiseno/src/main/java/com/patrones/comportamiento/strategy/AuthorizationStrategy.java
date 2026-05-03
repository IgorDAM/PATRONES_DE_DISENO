package com.patrones.comportamiento.strategy;

/**
 * Interfaz Strategy - Define el contrato que todas las estrategias deben cumplir
 */
public interface AuthorizationStrategy {
    boolean canReadOrder(Long userId, Long ownerId);
    boolean canCreateOrder(String role);
    boolean canDeleteOrder(String role);
}
