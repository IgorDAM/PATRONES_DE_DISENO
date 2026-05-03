package com.patrones.creacionales.singleton;

/**
 * Clase de demostración del patrón Singleton.
 *
 * Prueba los tres aspectos clave del patrón:
 *   1. La primera llamada crea la instancia.
 *   2. Las llamadas posteriores reutilizan la misma.
 *   3. Ambas referencias apuntan al mismo objeto en memoria.
 */
public class SingletonExample {

    public static void main(String[] args) {
        System.out.println("=== PATRÓN SINGLETON ===\n");

        // -------------------------------------------------------
        // Llamada 1: la instancia aún no existe → se crea.
        // El constructor privado se ejecuta y el mensaje de
        // inicialización aparece por primera (y única) vez.
        // -------------------------------------------------------
        System.out.println("1. Primera llamada a getInstance():");
        ConfigManager config1 = ConfigManager.getInstance();
        config1.printConfig();

        // -------------------------------------------------------
        // Llamada 2: la instancia ya existe → se reutiliza.
        // El constructor NO vuelve a ejecutarse. No hay nuevo objeto.
        // config2 recibe exactamente el mismo objeto que config1.
        // -------------------------------------------------------
        System.out.println("\n2. Segunda llamada a getInstance():");
        ConfigManager config2 = ConfigManager.getInstance();
        System.out.println("[Singleton] Se reutiliza la misma instancia (sin inicialización)");
        config2.printConfig();

        // -------------------------------------------------------
        // Verificación: == compara referencias, no contenido.
        // Si ambas variables apuntan al mismo objeto, == es true.
        // identityHashCode confirma que comparten dirección de memoria.
        // -------------------------------------------------------
        System.out.println("\n3. Verificación de identidad:");
        System.out.println("¿config1 == config2? " + (config1 == config2));
        System.out.println("System.identityHashCode(config1): " + System.identityHashCode(config1));
        System.out.println("System.identityHashCode(config2): " + System.identityHashCode(config2));
        // Ambos identityHashCode son iguales → mismo objeto en memoria

        System.out.println("\n✅ Conclusión: Solo existe UNA instancia de ConfigManager");
        System.out.println("   Ambas referencias apuntan al mismo objeto en memoria.\n");
    }
}