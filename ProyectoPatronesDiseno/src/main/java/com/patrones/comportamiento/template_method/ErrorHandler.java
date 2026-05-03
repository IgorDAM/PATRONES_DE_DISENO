package com.patrones.comportamiento.template_method;

/**
 * Clase base que define el template method
 */
public abstract class ErrorHandler {
    
    /**
     * TEMPLATE METHOD - Define el esqueleto del algoritmo
     */
    public final void handle(Exception ex) {
        logError(ex);
        ErrorResponse response = buildResponse(ex);
        sendResponse(response);
    }
    
    private void logError(Exception ex) {
        System.out.println("  [Log] Error: " + ex.getMessage());
    }
    
    private void sendResponse(ErrorResponse response) {
        System.out.println("  [Response] HTTP " + response.statusCode + ": " + response.message);
    }
    
    protected abstract ErrorResponse buildResponse(Exception ex);
    
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
