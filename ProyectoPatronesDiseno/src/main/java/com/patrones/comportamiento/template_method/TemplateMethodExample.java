package com.patrones.comportamiento.template_method;

/**
 * Demostración del patrón Template Method
 */
public class TemplateMethodExample {
    
    public static void main(String[] args) {
        System.out.println("=== PATRÓN TEMPLATE METHOD ===\n");
        
        System.out.println("El template define la ESTRUCTURA del algoritmo:");
        System.out.println("  1. Registrar error (Log)");
        System.out.println("  2. Construir respuesta (específica por tipo)");
        System.out.println("  3. Enviar respuesta\n");
        
        // Caso 1: Error 404 Not Found
        System.out.println("--- Caso 1: Pedido no encontrado (404) ---\n");
        ErrorHandler handler404 = new NotFoundErrorHandler();
        try {
            throw new Exception("Pedido con id 999 no existe");
        } catch (Exception ex) {
            handler404.handle(ex);
        }
        
        // Caso 2: Error 401 Unauthorized
        System.out.println("\n--- Caso 2: Acceso no autorizado (401) ---\n");
        ErrorHandler handler401 = new UnauthorizedErrorHandler();
        try {
            throw new Exception("Usuario no tiene permisos");
        } catch (Exception ex) {
            handler401.handle(ex);
        }
        
        // Caso 3: Error 500 Internal Server Error
        System.out.println("\n--- Caso 3: Error interno del servidor (500) ---\n");
        ErrorHandler handler500 = new InternalServerErrorHandler();
        try {
            throw new Exception("NullPointerException en servicio de pedidos");
        } catch (Exception ex) {
            handler500.handle(ex);
        }
        
        System.out.println("\n✅ Conclusión: Template Method garantiza consistencia");
        System.out.println("   El flujo (template) es igual para TODOS los errores:");
        System.out.println("   - Log siempre");
        System.out.println("   - Construir respuesta (variantemente)");
        System.out.println("   - Enviar respuesta siempre");
        System.out.println("\n   Sin Template Method, repetirías este flujo en cada handler.\n");
    }
}
