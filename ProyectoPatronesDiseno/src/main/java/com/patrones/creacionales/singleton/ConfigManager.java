package com.patrones.creacionales.singleton;

/**
 * PATRÓN SINGLETON - Ejemplo Didáctico
 * 
 * Propósito: Garantizar que una clase tenga una única instancia y proporcionar
 * un punto de acceso global a esa instancia.
 * 
 * En este ejemplo, simulamos un gestor de configuración que debe existir
 * una sola vez en toda la aplicación.
 */
public class ConfigManager {
    
    // Instancia estática única
    private static ConfigManager instance;
    
    // Constructor privado impide instanciación externa
    private ConfigManager() {
        System.out.println("[Singleton] ConfigManager inicializado (UNA SOLA VEZ)");
    }
    
    // Método sincronizado para obtener la instancia única
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }
    
    public void printConfig() {
        System.out.println("[Singleton] Configuración del sistema...");
        System.out.println("  - App Name: PatronesApp");
        System.out.println("  - Version: 1.0.0");
        System.out.println("  - Database: localhost:5432");
    }
}
