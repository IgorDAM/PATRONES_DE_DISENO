package com.patrones.estructurales.dto_adapter;

/**
 * Adapter (Mapper) — traduce entre los dos mundos: Entity ↔ DTO.
 *
 * Este es el núcleo del patrón Adapter: oculta la incompatibilidad de formatos
 * entre la base de datos (enum MAYÚSCULAS) y la API REST (String minúsculas).
 * Ni la Entity ni el DTO saben nada el uno del otro — el Mapper es el puente.
 *
 * Al centralizar la conversión aquí, si el formato cambia en el futuro
 * solo hay que modificar esta clase, no todas las capas de la aplicación.
 */
public class PedidoMapper {

    /**
     * Convierte una {@link PedidoEntity} (modelo de BD) en un {@link PedidoDTO} (modelo de API).
     *
     * La conversión clave: {@code enum.name().toLowerCase()} transforma
     * {@code PENDIENTE} → {@code "pendiente"}, formato esperado por el frontend.
     *
     * @param entity la entidad proveniente de la base de datos
     * @return el DTO listo para serializar a JSON, o {@code null} si la entity es nula
     */
    public static PedidoDTO toDTO(PedidoEntity entity) {
        if (entity == null) return null;

        // Convierte el enum (MAYÚSCULAS) a String en minúsculas para la API
        String estadoMinusculas = entity.getEstado().name().toLowerCase();

        return new PedidoDTO(
                entity.getId(),
                entity.getClienteId(),
                entity.getClienteNombre(),
                entity.getServicio(),
                entity.getCantidad(),
                estadoMinusculas
        );
    }

    /**
     * Convierte un {@link PedidoDTO} (modelo de API) en una {@link PedidoEntity} (modelo de BD).
     *
     * La conversión inversa: {@code String.toUpperCase()} + {@code valueOf} transforma
     * {@code "en_proceso"} → {@code PedidoEstado.EN_PROCESO}.
     * Si el String no coincide con ningún valor del enum, {@code valueOf} lanza
     * {@code IllegalArgumentException} — señal de que llegó un estado inválido.
     *
     * @param dto el DTO recibido desde el frontend o la API
     * @return la entidad lista para persistir en BD, o {@code null} si el DTO es nulo
     */
    public static PedidoEntity toEntity(PedidoDTO dto) {
        if (dto == null) return null;

        // Convierte el String en minúsculas al enum equivalente en MAYÚSCULAS
        PedidoEstado estado = PedidoEstado.valueOf(dto.getEstado().toUpperCase());

        return new PedidoEntity(
                dto.getId(),
                dto.getClienteId(),
                dto.getClienteNombre(),
                dto.getServicio(),
                dto.getCantidad(),
                estado
        );
    }
}