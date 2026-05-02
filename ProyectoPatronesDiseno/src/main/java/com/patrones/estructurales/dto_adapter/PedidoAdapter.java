package com.patrones.estructurales.dto_adapter;

/**
 * PATRÓN DTO / ADAPTER - Ejemplo Didáctico
 * 
 * Propósito: Convertir la interfaz de una clase a otra compatible.
 * En este caso, convertimos entre Pedido (modelo de BD) y PedidoDTO (modelo de API).
 */

// Entity (modelo de BD)
public class PedidoEntity {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private Integer cantidad;
    private PedidoEstado estado;  // ENUM en MAYUSCULAS
    
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

// Enum de BD
enum PedidoEstado {
    PENDIENTE, EN_PROCESO, COMPLETADO, ENVIADO, CANCELADO
}

// DTO (modelo de API REST)
public static class PedidoDTO {
    private Long id;
    private Long clienteId;
    private String clienteNombre;
    private String servicio;
    private Integer cantidad;
    private String estado;  // String en minusculas (diferente de BD)
    
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

/**
 * ADAPTER (Mapper) - Convierte entre Entity y DTO
 */
public class PedidoMapper {
    
    /**
     * Convierte Pedido (Entity BD) → PedidoDTO (API)
     */
    public static PedidoDTO toDTO(PedidoEntity entity) {
        if (entity == null) return null;
        
        // CAMBIO CRÍTICO: Convertir ENUM a String minusculas
        String estadoMinusculas = entity.getEstado().name().toLowerCase();
        
        return new PedidoDTO(
                entity.getId(),
                entity.getClienteId(),
                entity.getClienteNombre(),
                entity.getServicio(),
                entity.getCantidad(),
                estadoMinusculas  // PENDIENTE → "pendiente"
        );
    }
    
    /**
     * Convierte PedidoDTO (API) → Pedido (Entity BD)
     */
    public static PedidoEntity toEntity(PedidoDTO dto) {
        if (dto == null) return null;
        
        // CAMBIO CRÍTICO: Convertir String a ENUM mayusculas
        PedidoEstado estado = PedidoEstado.valueOf(dto.getEstado().toUpperCase());
        
        return new PedidoEntity(
                dto.getId(),
                dto.getClienteId(),
                dto.getClienteNombre(),
                dto.getServicio(),
                dto.getCantidad(),
                estado  // "pendiente" → PENDIENTE
        );
    }
}
