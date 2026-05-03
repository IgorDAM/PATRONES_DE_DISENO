package com.patrones.comportamiento.strategy;

/**
 * Estrategia ADMIN - ADMIN puede hacer todo
 */
public class AdminStrategy implements AuthorizationStrategy {
    
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        return true;
    }
    
    @Override
    public boolean canCreateOrder(String role) {
        return false;
    }
    
    @Override
    public boolean canDeleteOrder(String role) {
        return true;
    }
}
