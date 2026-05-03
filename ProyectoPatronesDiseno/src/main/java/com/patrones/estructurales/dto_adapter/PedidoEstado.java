package com.patrones.estructurales.dto_adapter;

/**
 * Enumerado que representa los posibles estados de un pedido en la base de datos.
 *
 * Se usa {@code enum} en lugar de String para garantizar que solo existan
 * valores válidos — el compilador impide estados inventados como "procesando".
 *
 * Convención: los enums en BD se almacenan en MAYÚSCULAS (PENDIENTE, EN_PROCESO...).
 * La conversión a minúsculas para la API la realiza {@link PedidoMapper}.
 */
public enum PedidoEstado {
    PENDIENTE,   // El pedido ha sido creado pero aún no se ha comenzado
    EN_PROCESO,  // El pedido está siendo trabajado actualmente
    COMPLETADO,  // El trabajo ha finalizado correctamente
    ENVIADO,     // El pedido ha sido entregado al cliente
    CANCELADO    // El pedido fue anulado antes de completarse
}