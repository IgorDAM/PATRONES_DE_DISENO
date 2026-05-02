package com.patrones.creacionales.singleton;

/**
 * Demostración del patrón Singleton
 */
public class SingletonExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN SINGLETON ===\n");
        
        // Primera llamada
        System.out.println("1. Primera llamada a getInstance():");
        ConfigManager config1 = ConfigManager.getInstance();
        config1.printConfig();
        
        System.out.println("\n2. Segunda llamada a getInstance():");
        ConfigManager config2 = ConfigManager.getInstance();
        System.out.println("[Singleton] Se reutiliza la misma instancia (sin inicialización)");
        config2.printConfig();
        
        System.out.println("\n3. Verificación de identidad:");
        System.out.println("¿config1 == config2? " + (config1 == config2));
        System.out.println("System.identityHashCode(config1): " + System.identityHashCode(config1));
        System.out.println("System.identityHashCode(config2): " + System.identityHashCode(config2));
        
        System.out.println("\n✅ Conclusión: Solo existe UNA instancia de ConfigManager");
        System.out.println("   Ambas referencias apuntan al mismo objeto en memoria.\n");
    }
}
