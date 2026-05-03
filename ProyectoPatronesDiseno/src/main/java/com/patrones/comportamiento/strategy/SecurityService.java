package com.patrones.comportamiento.strategy;

/**
 * ============================================================
 *  CONTEXTO (Context) del patrón Strategy
 * ============================================================
 *
 * En el patrón Strategy, el CONTEXTO es la clase que:
 *   1. Mantiene una referencia a la estrategia activa.
 *   2. Delega el trabajo a esa estrategia, sin saber cómo lo hace internamente.
 *   3. Permite cambiar de estrategia en tiempo de ejecución.
 *
 * Esta clase NO sabe si el usuario es ADMIN o CLIENTE una vez que se construye.
 * Simplemente llama a {@code strategy.hacerAlgo()} y la estrategia correcta
 * se encarga de la lógica. Eso desacopla el "quién decide" del "cómo se decide".
 *
 * Sin Strategy, este servicio estaría lleno de:
 *   if (role.equals("ADMIN")) { ... } else if (role.equals("CLIENTE")) { ... }
 * Con Strategy, esos bloques están encapsulados en sus propias clases.
 */
public class SecurityService {

    /**
     * La estrategia activa para este contexto de seguridad.
     *
     * Es del tipo de la INTERFAZ ({@link AuthorizationStrategy}), no de una
     * implementación concreta. Esto es clave: el contexto no sabe ni le importa
     * si es un AdminStrategy o ClienteStrategy — solo sabe que cumple el contrato.
     *
     * Este es el principio de "programar hacia interfaces, no implementaciones".
     */
    private AuthorizationStrategy strategy;

    /**
     * Constructor que selecciona la estrategia adecuada según el rol recibido.
     *
     * Este es el único punto donde se toma la decisión de qué estrategia usar.
     * Una vez asignada, el resto del servicio trabaja de forma polimórfica
     * sin preocuparse más por el rol.
     *
     * Nota: en una aplicación real, este mapeo podría hacerse con un Map,
     * un factory o inyección de dependencias (Spring), para evitar el if-else
     * y facilitar la adición de nuevos roles sin modificar este constructor.
     *
     * @param role el rol del usuario que determinará la estrategia a usar
     *             ("ADMIN" o "CLIENTE")
     */
    public SecurityService(String role) {
        if ("ADMIN".equals(role)) {
            // Rol ADMIN → se asigna la estrategia con permisos totales
            this.strategy = new AdminStrategy();
        } else if ("CLIENTE".equals(role)) {
            // Rol CLIENTE → se asigna la estrategia con permisos restringidos
            this.strategy = new ClienteStrategy();
        }
        // Si el rol no coincide con ninguno, strategy queda null
        // (en producción convendría lanzar una excepción aquí)
    }

    /**
     * Delega la comprobación de lectura a la estrategia activa.
     *
     * Este método no contiene lógica de autorización propia.
     * Simplemente pasa los parámetros a la estrategia y devuelve su resultado.
     * Eso es la delegación: el contexto confía en la estrategia.
     *
     * @param userId  ID del usuario que quiere leer el pedido
     * @param ownerId ID del propietario del pedido
     * @return {@code true} si la estrategia activa permite la lectura
     */
    public boolean canReadOrder(Long userId, Long ownerId) {
        // Delegación total: la estrategia sabe mejor que nadie si puede o no
        return strategy.canReadOrder(userId, ownerId);
    }

    /**
     * Delega la comprobación de creación a la estrategia activa.
     *
     * @param role el rol del usuario (reenviado a la estrategia)
     * @return {@code true} si la estrategia activa permite crear pedidos
     */
    public boolean canCreateOrder(String role) {
        // La estrategia encapsula la regla: CLIENTE sí, ADMIN no
        return strategy.canCreateOrder(role);
    }

    /**
     * Delega la comprobación de eliminación a la estrategia activa.
     *
     * @param role el rol del usuario (reenviado a la estrategia)
     * @return {@code true} si la estrategia activa permite eliminar pedidos
     */
    public boolean canDeleteOrder(String role) {
        // La estrategia encapsula la regla: ADMIN sí, CLIENTE no
        return strategy.canDeleteOrder(role);
    }
}