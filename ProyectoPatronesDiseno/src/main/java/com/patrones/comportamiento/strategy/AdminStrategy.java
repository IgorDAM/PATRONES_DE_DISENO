package com.patrones.comportamiento.strategy;

/**
 * ============================================================
 *  ESTRATEGIA CONCRETA — Rol ADMIN
 * ============================================================
 *
 * Esta clase es una ESTRATEGIA CONCRETA del patrón Strategy.
 * Implementa {@link AuthorizationStrategy} con la lógica específica
 * del rol ADMINISTRADOR, que tiene privilegios máximos en el sistema.
 *
 * ¿Por qué una clase separada y no un simple if-else?
 * → Principio Abierto/Cerrado (SOLID): si mañana cambian los permisos
 *   del ADMIN, solo modificamos ESTA clase, sin tocar nada más.
 * → Principio de Responsabilidad Única: cada clase maneja solo su rol.
 */
public class AdminStrategy implements AuthorizationStrategy {

    /**
     * El ADMIN puede leer CUALQUIER pedido, independientemente
     * de quién sea el propietario.
     *
     * Nótese que los parámetros {@code userId} y {@code ownerId}
     * ni siquiera se usan — el ADMIN no necesita esa comprobación.
     * Se mantienen porque la interfaz los exige (contrato).
     *
     * @param userId  ID del admin que solicita la lectura (no se usa)
     * @param ownerId ID del propietario del pedido (no se usa)
     * @return siempre {@code true}
     */
    @Override
    public boolean canReadOrder(Long userId, Long ownerId) {
        // ADMIN tiene acceso total: no importa quién sea el dueño del pedido
        return true;
    }

    /**
     * El ADMIN NO puede crear pedidos.
     *
     * Esto puede parecer contra-intuitivo, pero refleja una regla
     * de negocio: el administrador gestiona el sistema, no realiza compras.
     * Ejemplo de cómo el patrón Strategy permite reglas específicas por rol.
     *
     * @param role el rol del usuario (en este caso siempre "ADMIN")
     * @return siempre {@code false}
     */
    @Override
    public boolean canCreateOrder(String role) {
        // Regla de negocio: el ADMIN administra, no compra
        return false;
    }

    /**
     * El ADMIN puede eliminar CUALQUIER pedido.
     *
     * Operación de alto privilegio reservada para administradores.
     * Es el único rol con este permiso en el sistema.
     *
     * @param role el rol del usuario (en este caso siempre "ADMIN")
     * @return siempre {@code true}
     */
    @Override
    public boolean canDeleteOrder(String role) {
        // ADMIN tiene la autoridad máxima: puede eliminar cualquier pedido
        return true;
    }
}