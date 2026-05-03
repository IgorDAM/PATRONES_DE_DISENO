package com.patrones.estructurales.dto_adapter;

/**
 * Entity — representación interna de un pedido tal como vive en la base de datos.
 *
 * En el patrón DTO/Adapter, la Entity es el modelo del mundo interno:
 * usa tipos propios del dominio (como el enum {@link PedidoEstado}) y puede
 * contener campos que nunca deben exponerse al exterior (auditoría, claves, etc.).
 *
 * Nunca se envía directamente al cliente — primero pasa por {@link PedidoMapper}
 * para convertirse en un {@link PedidoDTO} seguro y adaptado a la API.
 */
public class PedidoEntity {

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private Integer cantidad;

    /**
     * El estado usa el enum {@link PedidoEstado} (MAYÚSCULAS), no un String.
     * Esto garantiza integridad: la BD nunca tendrá un estado inválido.
     * El Mapper se encargará de convertirlo a String en minúsculas para la API.
     */
    private PedidoEstado estado;

    public PedidoEntity(Long id, Long clienteId, String clienteNombre,
                        String servicio, Integer cantidad, PedidoEstado estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getClienteNombre() { return clienteNombre; }
    public String getServicio() { return servicio; }
    public Integer getCantidad() { return cantidad; }
    public PedidoEstado getEstado() { return estado; }
}