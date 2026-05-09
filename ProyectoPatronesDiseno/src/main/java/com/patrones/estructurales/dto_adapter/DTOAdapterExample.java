package com.patrones.estructurales.dto_adapter;

/**
 * Clase de demostración del patrón DTO / Adapter.
 *
 * Recorre el ciclo completo de conversión en ambas direcciones:
 *   Entity → DTO (BD hacia API / frontend)
 *   DTO → Entity (frontend hacia BD)
 *
 * El objetivo es mostrar cómo {@link PedidoMapper} actúa de puente
 * entre dos representaciones incompatibles del mismo concepto.
 */
public class DTOAdapterExample {

    public static void main(String[] args) {
        System.out.println("=== PATRÓN DTO / ADAPTER ===\n");

        // -------------------------------------------------------
        // Paso 1: Simular una Entity que viene de la base de datos.
        // En una app real, esto lo haría un repositorio JPA/Hibernate.
        // Nótese que el estado es un enum (PedidoEstado.PENDIENTE).
        // -------------------------------------------------------
        System.out.println("1. Crear Pedido (Entity - BD):\n");
        PedidoEntity entityDeBD = new PedidoEntity(
                1L,
                100L,
                "Juan García",
                "Serigrafía",
                50,
                PedidoEstado.PENDIENTE
        );

        System.out.println("Entity desde BD:");
        System.out.println("  - Id: " + entityDeBD.getId());
        System.out.println("  - Servicio: " + entityDeBD.getServicio());
        System.out.println("  - Estado: " + entityDeBD.getEstado());
        System.out.println("  ⚠️  Estado es ENUM MAYÚSCULAS (PENDIENTE)");

        // -------------------------------------------------------
        // Paso 2: Convertir Entity → DTO usando el Adapter (Mapper).
        // El Mapper traduce el enum a String en minúsculas y selecciona
        // solo los campos que necesita la API — la Entity no se expone.
        // -------------------------------------------------------
        System.out.println("\n2. Convertir Entity → DTO (usando Adapter):\n");
        PedidoDTO dtoParaAPI = PedidoMapper.toDTO(entityDeBD);

        System.out.println(dtoParaAPI);
        System.out.println("  ✅ Estado ahora es String MINUSCULAS (pendiente)");
        System.out.println("  ✅ Solo los campos necesarios para la API");

        // -------------------------------------------------------
        // Paso 3: El DTO se serializa a JSON para el frontend.
        // Este es el resultado final que viaja por la red — sin enums,
        // sin lógica interna, solo datos en formato legible.
        // -------------------------------------------------------
        System.out.println("\n3. JSON que se envía al frontend:\n");
        System.out.println("  {");
        System.out.println("    \"id\": " + dtoParaAPI.getId() + ",");
        System.out.println("    \"clienteId\": " + dtoParaAPI.getClienteId() + ",");
        System.out.println("    \"clienteNombre\": \"" + dtoParaAPI.getClienteNombre() + "\",");
        System.out.println("    \"servicio\": \"" + dtoParaAPI.getServicio() + "\",");
        System.out.println("    \"cantidad\": " + dtoParaAPI.getCantidad() + ",");
        System.out.println("    \"estado\": \"" + dtoParaAPI.getEstado() + "\"");
        System.out.println("  }");

        // -------------------------------------------------------
        // Paso 4: Conversión inversa DTO → Entity.
        // El frontend envía un JSON con estado en minúsculas ("en_proceso").
        // El Mapper lo convierte al enum correspondiente (EN_PROCESO)
        // para que pueda persistirse correctamente en la base de datos.
        // -------------------------------------------------------
        System.out.println("\n4. Convertir DTO → Entity (desde frontend):\n");
        PedidoDTO dtoDelFrontend = new PedidoDTO(
                2L,
                101L,
                "María López",
                "Planchado",
                100,
                "en_proceso" // String en minúsculas tal como lo envía el frontend
        );

        PedidoEntity entityParaBD = PedidoMapper.toEntity(dtoDelFrontend);

        System.out.println("Entity para guardar en BD:");
        System.out.println("  - Id: " + entityParaBD.getId());
        System.out.println("  - Servicio: " + entityParaBD.getServicio());
        System.out.println("  - Estado: " + entityParaBD.getEstado());
        System.out.println("  ✅ Estado convertido a ENUM MAYÚSCULAS (EN_PROCESO)");

        // El Mapper centraliza toda la conversión — si el formato cambia,
        // solo se toca PedidoMapper, no el resto de la aplicación.
        System.out.println("\n✅ Conclusión: El Adapter convierte entre formatos incompatibles");
        System.out.println("   BD usa ENUM MAYÚSCULAS, API usa String minúsculas");
        System.out.println("   El Mapper oculta esta conversión automáticamente.\n");
    }
}