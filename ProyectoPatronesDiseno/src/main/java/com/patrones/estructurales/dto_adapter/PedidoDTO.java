package com.patrones.estructurales.dto_adapter;

/**
 * DTO (Data Transfer Object) — representación de un pedido adaptada para la API REST.
 *
 * En el patrón DTO/Adapter, el DTO es el modelo del mundo externo:
 * solo expone los campos que el cliente necesita ver, en el formato que espera.
 *
 * Diferencias clave respecto a {@link PedidoEntity}:
 *   - {@code estado} es un {@code String} en minúsculas ("pendiente"), no un enum.
 *   - No incluye campos internos sensibles que no deben salir de la capa de datos.
 *
 * La conversión entre Entity y DTO la gestiona {@link PedidoMapper}.
 */
public class PedidoDTO {

    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private Integer cantidad;

    /**
     * Estado como String en minúsculas para que el JSON sea legible y estándar REST.
     * Ejemplo: "pendiente", "en_proceso", "completado".
     * El Mapper convierte este String al enum {@link PedidoEstado} cuando va a la BD.
     */
    private String estado;

    public PedidoDTO(Long id, Long clienteId, String clienteNombre,
                     String servicio, Integer cantidad, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.servicio = servicio;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "PedidoDTO{" +
                "id=" + id +
                ", clienteId=" + clienteId +
                ", clienteNombre='" + clienteNombre + '\'' +
                ", servicio='" + servicio + '\'' +
                ", cantidad=" + cantidad +
                ", estado='" + estado + '\'' +
                '}';
    }

    public Long getId() { return id; }
    public Long getClienteId() { return clienteId; }
    public String getClienteNombre() { return clienteNombre; }
    public String getServicio() { return servicio; }
    public Integer getCantidad() { return cantidad; }
    public String getEstado() { return estado; }
}