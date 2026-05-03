package com.patrones.comportamiento.template_method;

/**
 * Handler específico: Error 500 Internal Server Error
 */
public class InternalServerErrorHandler extends ErrorHandler {
    
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(500, "Internal Server Error", 
            "Error inesperado: " + ex.getMessage());
    }
}
