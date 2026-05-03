package com.patrones.comportamiento.strategy;

/**
 * Context que utiliza la estrategia de autorización
 */
public class SecurityService {
    
    private AuthorizationStrategy strategy;
    
    public SecurityService(String role) {
        if ("ADMIN".equals(role)) {
            this.strategy = new AdminStrategy();
        } else if ("CLIENTE".equals(role)) {
            this.strategy = new ClienteStrategy();
        }
    }
    
    public boolean canReadOrder(Long userId, Long ownerId) {
        return strategy.canReadOrder(userId, ownerId);
    }
    
    public boolean canCreateOrder(String role) {
        return strategy.canCreateOrder(role);
    }
    
    public boolean canDeleteOrder(String role) {
        return strategy.canDeleteOrder(role);
    }
}
