package com.patrones.comportamiento.template_method;

/**
 * PATRÓN TEMPLATE METHOD - Ejemplo Didáctico
 * 
 * Propósito: Definir el esqueleto de un algoritmo en una clase base,
 * dejando que las subclases implementen pasos específicos.
 * 
 * En este ejemplo, el manejo de excepciones sigue el mismo "template"
 * pero cada excepción se trata de forma específica.
 */

// Clase base que define el template
public abstract class ErrorHandler {
    
    /**
     * TEMPLATE METHOD - Define el esqueleto del algoritmo
     */
    public final void handle(Exception ex) {
        // Paso 1: Registrar
        logError(ex);
        
        // Paso 2: Construir respuesta
        ErrorResponse response = buildResponse(ex);
        
        // Paso 3: Devolver respuesta
        sendResponse(response);
    }
    
    // Pasos comunes a TODOS
    private void logError(Exception ex) {
        System.out.println("  [Log] Error: " + ex.getMessage());
    }
    
    private void sendResponse(ErrorResponse response) {
        System.out.println("  [Response] HTTP " + response.statusCode + ": " + response.message);
    }
    
    // Pasos que VARÍAN según el tipo de error
    protected abstract ErrorResponse buildResponse(Exception ex);
    
    // Clase interna para respuesta
    public static class ErrorResponse {
        public int statusCode;
        public String message;
        public String details;
        
        public ErrorResponse(int statusCode, String message, String details) {
            this.statusCode = statusCode;
            this.message = message;
            this.details = details;
        }
    }
}

// Handler específico 1: Not Found
public static class NotFoundErrorHandler extends ErrorHandler {
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(404, "Not Found", ex.getMessage());
    }
}

// Handler específico 2: Unauthorized
public static class UnauthorizedErrorHandler extends ErrorHandler {
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(401, "Unauthorized", "Acceso denegado: " + ex.getMessage());
    }
}

// Handler específico 3: Internal Server Error
public static class InternalServerErrorHandler extends ErrorHandler {
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(500, "Internal Server Error", 
            "Error inesperado: " + ex.getMessage());
    }
}
