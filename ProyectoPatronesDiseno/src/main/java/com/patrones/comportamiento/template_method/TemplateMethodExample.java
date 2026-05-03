package com.patrones.comportamiento.template_method;

/**
 * Clase de demostración del patrón Template Method.
 *
 * Muestra cómo tres handlers distintos (404, 401, 500) comparten
 * el mismo flujo de ejecución gracias a la clase base {@link ErrorHandler},
 * pero cada uno genera su propia respuesta personalizada.
 */
public class TemplateMethodExample {

    public static void main(String[] args) {
        System.out.println("=== PATRÓN TEMPLATE METHOD ===\n");

        // El flujo es siempre este — lo garantiza el template method en ErrorHandler:
        System.out.println("El template define la ESTRUCTURA del algoritmo:");
        System.out.println("  1. Registrar error (Log)");
        System.out.println("  2. Construir respuesta (específica por tipo)");
        System.out.println("  3. Enviar respuesta\n");

        // -------------------------------------------------------
        // Caso 1: 404 Not Found
        // handler404 es de tipo ErrorHandler (interfaz), no NotFoundErrorHandler.
        // Así programamos contra la abstracción, no la implementación concreta.
        // -------------------------------------------------------
        System.out.println("--- Caso 1: Pedido no encontrado (404) ---\n");
        ErrorHandler handler404 = new NotFoundErrorHandler();
        try {
            throw new Exception("Pedido con id 999 no existe");
        } catch (Exception ex) {
            // handle() ejecuta: log → buildResponse (404) → sendResponse
            handler404.handle(ex);
        }

        // -------------------------------------------------------
        // Caso 2: 401 Unauthorized
        // Mismo método handle(), diferente buildResponse() internamente.
        // El código cliente no cambia — solo cambia la estrategia de respuesta.
        // -------------------------------------------------------
        System.out.println("\n--- Caso 2: Acceso no autorizado (401) ---\n");
        ErrorHandler handler401 = new UnauthorizedErrorHandler();
        try {
            throw new Exception("Usuario no tiene permisos");
        } catch (Exception ex) {
            // handle() ejecuta: log → buildResponse (401) → sendResponse
            handler401.handle(ex);
        }

        // -------------------------------------------------------
        // Caso 3: 500 Internal Server Error
        // El flujo es idéntico a los anteriores — eso es lo que aporta el patrón:
        // consistencia en el proceso, variabilidad solo donde se necesita.
        // -------------------------------------------------------
        System.out.println("\n--- Caso 3: Error interno del servidor (500) ---\n");
        ErrorHandler handler500 = new InternalServerErrorHandler();
        try {
            throw new Exception("NullPointerException en servicio de pedidos");
        } catch (Exception ex) {
            // handle() ejecuta: log → buildResponse (500) → sendResponse
            handler500.handle(ex);
        }

        // Sin Template Method, cada handler repetiría el log y el sendResponse.
        // Con Template Method, esa lógica vive en un único lugar: ErrorHandler.
        System.out.println("\n✅ Conclusión: Template Method garantiza consistencia");
        System.out.println("   El flujo (template) es igual para TODOS los errores:");
        System.out.println("   - Log siempre");
        System.out.println("   - Construir respuesta (variante)");
        System.out.println("   - Enviar respuesta siempre");
        System.out.println("\n   Sin Template Method, repetirías este flujo en cada handler.\n");
    }
}