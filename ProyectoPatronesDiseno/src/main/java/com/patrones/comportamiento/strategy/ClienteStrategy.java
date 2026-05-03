package com.patrones.comportamiento.strategy;

/**
 * Estrategia CLIENTE - CLIENTE tiene permisos limitados
 */
public class ClienteStrategy implements AuthorizationStrategy {
    
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        return userId.equals(ownerId);
    }
    
    @Override
    public boolean canCreateOrder(String role) {
        return true;
    }
    
    @Override
    public boolean canDeleteOrder(String role) {
        return false;
    }
}
