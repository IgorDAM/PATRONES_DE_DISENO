package com.patrones.estructurales.dto_adapter;

/**
 * Demostración del patrón DTO / Adapter
 */
public class DTOAdapterExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN DTO / ADAPTER ===\n");
        
        // Crear una Entity (como si viniera de BD)
        System.out.println("1. Crear Pedido (Entity - BD):\n");
        PedidoAdapter.PedidoEntity entityDeBD = new PedidoAdapter.PedidoEntity(
                1L,
                100L,
                "Juan García",
                "Serigrafía",
                50,
                PedidoAdapter.PedidoEstado.PENDIENTE  // ENUM en MAYUSCULAS
        );
        
        System.out.println("Entity desde BD:");
        System.out.println("  - Id: " + entityDeBD.getId());
        System.out.println("  - Servicio: " + entityDeBD.getServicio());
        System.out.println("  - Estado: " + entityDeBD.getEstado());
        System.out.println("  ⚠️  Estado es ENUM MAYUSCULAS (PENDIENTE)");
        
        // Convertir Entity → DTO usando Adapter
        System.out.println("\n2. Convertir Entity → DTO (usando Adapter):\n");
        PedidoAdapter.PedidoDTO dtoParaAPI = PedidoAdapter.PedidoMapper.toDTO(entityDeBD);
        
        System.out.println(dtoParaAPI);
        System.out.println("  ✅ Estado ahora es String MINUSCULAS (pendiente)");
        System.out.println("  ✅ Solo los campos necesarios para la API");
        
        // JSON que se enviaría al frontend
        System.out.println("\n3. JSON que se envía al frontend:\n");
        System.out.println("  {");
        System.out.println("    \"id\": " + dtoParaAPI.getId() + ",");
        System.out.println("    \"clienteId\": " + dtoParaAPI.getClienteId() + ",");
        System.out.println("    \"clienteNombre\": \"" + dtoParaAPI.getClienteNombre() + "\",");
        System.out.println("    \"servicio\": \"" + dtoParaAPI.getServicio() + "\",");
        System.out.println("    \"cantidad\": " + dtoParaAPI.getCantidad() + ",");
        System.out.println("    \"estado\": \"" + dtoParaAPI.getEstado() + "\"");
        System.out.println("  }");
        
        // Convertir DTO → Entity
        System.out.println("\n4. Convertir DTO → Entity (desde frontend):\n");
        PedidoAdapter.PedidoDTO dtoDelFrontend = new PedidoAdapter.PedidoDTO(
                2L,
                101L,
                "María López",
                "Planchado",
                100,
                "en_proceso"  // String minusculas desde frontend
        );
        
        PedidoAdapter.PedidoEntity entityParaBD = PedidoAdapter.PedidoMapper.toEntity(dtoDelFrontend);
        
        System.out.println("Entity para guardar en BD:");
        System.out.println("  - Id: " + entityParaBD.getId());
        System.out.println("  - Servicio: " + entityParaBD.getServicio());
        System.out.println("  - Estado: " + entityParaBD.getEstado());
        System.out.println("  ✅ Estado convertido a ENUM MAYUSCULAS (EN_PROCESO)");
        
        System.out.println("\n✅ Conclusión: El Adapter convierte entre formatos incompatibles");
        System.out.println("   BD usa ENUM MAYUSCULAS, API usa String minusculas");
        System.out.println("   El Mapper oculta esta conversión automáticamente.\n");
    }
}
