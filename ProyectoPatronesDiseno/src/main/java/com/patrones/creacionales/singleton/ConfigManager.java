package com.patrones.creacionales.singleton;

/**
 * Clase Singleton que actúa como gestor de configuración global.
 *
 * El patrón Singleton garantiza dos cosas:
 *   1. Solo existe UNA instancia de esta clase en toda la aplicación.
 *   2. Hay un punto de acceso global a esa instancia: {@link #getInstance()}.
 *
 * Cuándo usarlo: recursos compartidos que deben ser únicos, como gestores
 * de configuración, conexiones a base de datos o sistemas de logging.
 */
public class ConfigManager {

    /**
     * La única instancia de esta clase en toda la JVM.
     *
     * Es {@code static} para pertenecer a la clase, no a un objeto concreto.
     * Empieza en {@code null} y se crea solo la primera vez que se solicita
     * (inicialización diferida o "lazy initialization").
     */
    private static ConfigManager instance;

    /**
     * Constructor privado — la clave del patrón Singleton.
     *
     * Al ser {@code private}, nadie fuera de esta clase puede hacer
     * {@code new ConfigManager()}. Toda instanciación pasa obligatoriamente
     * por {@link #getInstance()}, lo que garantiza unicidad.
     */
    private ConfigManager() {
        // Este mensaje solo aparece UNA vez en toda la ejecución
        System.out.println("[Singleton] ConfigManager inicializado (UNA SOLA VEZ)");
    }

    /**
     * Punto de acceso global a la única instancia.
     *
     * La palabra clave {@code synchronized} garantiza que en entornos
     * multihilo dos hilos no creen dos instancias simultáneamente.
     * Si ya existe una instancia, simplemente la devuelve sin crear nada.
     *
     * @return la única instancia de {@code ConfigManager}
     */
    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            // Solo entra aquí la PRIMERA vez — el constructor se llama una sola vez
            instance = new ConfigManager();
        }
        // Las siguientes llamadas devuelven directamente la instancia ya creada
        return instance;
    }

    /**
     * Imprime los valores de configuración del sistema.
     *
     * En una aplicación real, estos valores vendrían de un archivo
     * de propiedades o variables de entorno, no hardcodeados.
     */
    public void printConfig() {
        System.out.println("[Singleton] Configuración del sistema...");
        System.out.println("  - App Name: PatronesApp");
        System.out.println("  - Version: 1.0.0");
        System.out.println("  - Database: localhost:5432");
    }
}