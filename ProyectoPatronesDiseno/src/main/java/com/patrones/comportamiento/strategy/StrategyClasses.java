package com.patrones.comportamiento.strategy;

/**
 * PATRÓN STRATEGY - Ejemplo Didáctico
 * 
 * Propósito: Definir una familia de algoritmos, encapsular cada uno,
 * y hacerlos intercambiables.
 * 
 * En este ejemplo, diferentes estrategias de autorización según el rol.
 */

// Interfaz Strategy
public interface AuthorizationStrategy {
    boolean canReadOrder(Long userId, Long ownerId);
    boolean canCreateOrder(String role);
    boolean canDeleteOrder(String role);
}

// Estrategia 1: ADMIN
public static class AdminStrategy implements AuthorizationStrategy {
    
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        // ADMIN puede leer cualquier pedido
        return true;
    }
    
    @Override
    public boolean canCreateOrder(String role) {
        // ADMIN NO puede crear pedidos (solo clientes)
        return false;
    }
    
    @Override
    public boolean canDeleteOrder(String role) {
        // ADMIN puede eliminar cualquier pedido
        return true;
    }
}

// Estrategia 2: CLIENTE
public static class ClienteStrategy implements AuthorizationStrategy {
    
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        // CLIENTE solo puede leer sus propios pedidos
        return userId.equals(ownerId);
    }
    
    @Override
    public boolean canCreateOrder(String role) {
        // CLIENTE puede crear pedidos
        return true;
    }
    
    @Override
    public boolean canDeleteOrder(String role) {
        // CLIENTE NO puede eliminar pedidos
        return false;
    }
}

// Context que usa la estrategia
public static class SecurityService {
    
    private AuthorizationStrategy strategy;
    
    public SecurityService(String role) {
        // Elige la estrategia según el rol
        if ("ADMIN".equals(role)) {
            this.strategy = new AdminStrategy();
        } else if ("CLIENTE".equals(role)) {
            this.strategy = new ClienteStrategy();
        }
    }
    
    // Métodos que delegan a la estrategia
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
