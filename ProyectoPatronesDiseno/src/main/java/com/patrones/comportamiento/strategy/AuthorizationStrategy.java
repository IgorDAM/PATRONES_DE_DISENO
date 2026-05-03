package com.patrones.comportamiento.strategy;

/**
 * ============================================================
 *  PATRÓN STRATEGY — Interfaz principal (el "contrato")
 * ============================================================
 *
 * En el patrón Strategy, esta interfaz representa la ESTRATEGIA ABSTRACTA.
 * Define QUÉ operaciones existen, pero NO CÓMO se implementan.
 *
 * Cada clase que implemente esta interfaz representará un algoritmo/comportamiento
 * diferente para las mismas operaciones. Eso es exactamente el patrón Strategy:
 * intercambiar comportamientos en tiempo de ejecución sin cambiar el código que los usa.
 *
 * Analogía: es como un contrato laboral que dice "debes saber conducir",
 * sin importar si conduces un coche, moto o camión — cada uno lo hace diferente.
 */
public interface AuthorizationStrategy {

    /**
     * Determina si un usuario puede LEER un pedido.
     *
     * Cada estrategia (rol) tendrá su propia lógica:
     * - ADMIN: siempre puede leer cualquier pedido.
     * - CLIENTE: solo puede leer sus propios pedidos.
     *
     * @param userId  ID del usuario que intenta realizar la acción
     * @param ownerId ID del propietario del pedido
     * @return {@code true} si tiene permiso, {@code false} si no
     */
    boolean canReadOrder(Long userId, Long ownerId);

    /**
     * Determina si un rol puede CREAR un pedido.
     *
     * Nótese que aquí el parámetro es el String del rol, no IDs de usuario.
     * Cada implementación decidirá si su rol tiene este privilegio.
     *
     * @param role el rol del usuario que intenta crear el pedido (ej: "ADMIN", "CLIENTE")
     * @return {@code true} si tiene permiso, {@code false} si no
     */
    boolean canCreateOrder(String role);

    /**
     * Determina si un rol puede ELIMINAR un pedido.
     *
     * Operación sensible: normalmente restringida a roles administrativos.
     * Cada implementación define si su rol tiene este privilegio.
     *
     * @param role el rol del usuario que intenta eliminar el pedido
     * @return {@code true} si tiene permiso, {@code false} si no
     */
    boolean canDeleteOrder(String role);
}