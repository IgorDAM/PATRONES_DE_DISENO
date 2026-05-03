package com.patrones.comportamiento.template_method;

/**
 * Handler específico: Error 404 Not Found
 */
public class NotFoundErrorHandler extends ErrorHandler {
    
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(404, "Not Found", ex.getMessage());
    }
}
