package com.patrones.comportamiento.strategy;

/**
 * ============================================================
 *  ESTRATEGIA CONCRETA — Rol CLIENTE
 * ============================================================
 *
 * Esta clase es otra ESTRATEGIA CONCRETA del patrón Strategy.
 * Implementa {@link AuthorizationStrategy} con la lógica del rol CLIENTE,
 * que tiene permisos limitados y orientados a sus propios recursos.
 *
 * Compárala con {@link AdminStrategy}: las mismas operaciones,
 * pero con comportamientos totalmente distintos. Eso es el patrón Strategy
 * en acción — misma interfaz, diferente algoritmo interno.
 *
 * En términos de seguridad, aplica el principio de "mínimo privilegio":
 * el cliente solo accede a lo que le pertenece.
 */
public class ClienteStrategy implements AuthorizationStrategy {

    /**
     * El CLIENTE solo puede leer pedidos que le pertenecen.
     *
     * Aquí sí importan ambos parámetros, a diferencia del ADMIN.
     * Se compara el ID del usuario que hace la petición con el ID
     * del propietario del pedido. Solo coinciden cuando es su propio pedido.
     *
     * Ejemplo:
     *   - userId=100, ownerId=100 → true  (su propio pedido)
     *   - userId=100, ownerId=200 → false (pedido ajeno)
     *
     * @param userId  ID del cliente que solicita la lectura
     * @param ownerId ID del propietario real del pedido
     * @return {@code true} solo si el cliente es el dueño del pedido
     */
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        // Solo puede leer si el pedido le pertenece (principio de mínimo privilegio)
        return userId.equals(ownerId);
    }

    /**
     * El CLIENTE puede crear pedidos.
     *
     * Esta es la operación principal para la que existe el rol CLIENTE:
     * realizar compras/pedidos en el sistema.
     *
     * @param role el rol del usuario (en este caso siempre "CLIENTE")
     * @return siempre {@code true}
     */
    @Override
    public boolean canCreateOrder(String role) {
        // El cliente puede comprar — es su función principal en el sistema
        return true;
    }

    /**
     * El CLIENTE NO puede eliminar pedidos.
     *
     * La eliminación es una operación destructiva reservada al ADMIN.
     * Un cliente puede cancelar un pedido (lógica de negocio aparte),
     * pero no tiene privilegio para eliminarlo físicamente del sistema.
     *
     * @param role el rol del usuario (en este caso siempre "CLIENTE")
     * @return siempre {@code false}
     */
    @Override
    public boolean canDeleteOrder(String role) {
        // Los clientes no tienen privilegio de eliminación — operación restringida
        return false;
    }
}