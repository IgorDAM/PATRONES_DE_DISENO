package com.patrones;

import com.patrones.creacionales.singleton.SingletonExample;
import com.patrones.creacionales.builder.BuilderExample;
import com.patrones.estructurales.facade.FacadeExample;
import com.patrones.estructurales.dto_adapter.DTOAdapterExample;
import com.patrones.comportamiento.strategy.StrategyExample;
import com.patrones.comportamiento.template_method.TemplateMethodExample;

/**
 * CLASE PRINCIPAL - Ejecuta todos los ejemplos de patrones de diseño
 * 
 * Este programa demuestra 6 patrones de diseño documentados en el manual:
 * 
 * CREACIONALES:
 *   1. Singleton - Una única instancia
 *   2. Builder - Construcción paso a paso
 * 
 * ESTRUCTURALES:
 *   3. Facade - Interfaz simplificada
 *   4. DTO/Adapter - Conversión entre formatos
 * 
 * DE COMPORTAMIENTO:
 *   5. Strategy - Algoritmos intercambiables
 *   6. Template Method - Esqueleto de algoritmo
 */
public class MainPrincipal {
    
    public static void main(String[] args) {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                                                ║");
        System.out.println("║     PROYECTO: PATRONES DE DISEÑO EN JAVA                       ║");
        System.out.println("║     6 Ejemplos Didácticos Basados en REALPRINT                 ║");
        System.out.println("║                                                                ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println("\n");
        
        // SINGLETON
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 1/6: SINGLETON");
        System.out.println("=".repeat(70));
        SingletonExample.main(args);
        pause();
        
        // BUILDER
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 2/6: BUILDER");
        System.out.println("=".repeat(70));
        BuilderExample.main(args);
        pause();
        
        // FACADE
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 3/6: FACADE");
        System.out.println("=".repeat(70));
        FacadeExample.main(args);
        pause();
        
        // DTO / ADAPTER
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 4/6: DTO / ADAPTER");
        System.out.println("=".repeat(70));
        DTOAdapterExample.main(args);
        pause();
        
        // STRATEGY
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 5/6: STRATEGY");
        System.out.println("=".repeat(70));
        StrategyExample.main(args);
        pause();
        
        // TEMPLATE METHOD
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EJEMPLO 6/6: TEMPLATE METHOD");
        System.out.println("=".repeat(70));
        TemplateMethodExample.main(args);
        
        // Resumen final
        printSummary();
    }
    
    /**
     * Pausa entre ejemplos (opcional)
     */
    private static void pause() {
        // Se ejecuta rápido; sin pausa
    }
    
    /**
     * Resumen final
     */
    private static void printSummary() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        RESUMEN FINAL                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("✅ CREACIONALES (2):");
        System.out.println("   1. Singleton    → Una única instancia en toda la app");
        System.out.println("   2. Builder      → Construir objetos complejos paso a paso");
        System.out.println();
        System.out.println("✅ ESTRUCTURALES (2):");
        System.out.println("   3. Facade       → Interfaz simplificada a subsistema complejo");
        System.out.println("   4. DTO/Adapter  → Convertir entre formatos incompatibles");
        System.out.println();
        System.out.println("✅ DE COMPORTAMIENTO (2):");
        System.out.println("   5. Strategy     → Algoritmos intercambiables según contexto");
        System.out.println("   6. Template     → Esqueleto de algoritmo consistente");
        System.out.println("      Method");
        System.out.println();
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println("Todos estos patrones están presentes en el proyecto REALPRINT");
        System.out.println("y se documentan en el Manual_Patrones_Diseño.md");
        System.out.println("═══════════════════════════════════════════════════════════════");
        System.out.println();
    }
}
