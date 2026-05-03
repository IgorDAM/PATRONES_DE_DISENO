package com.patrones.comportamiento.template_method;

/**
 * Handler específico: Error 401 Unauthorized
 */
public class UnauthorizedErrorHandler extends ErrorHandler {
    
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        return new ErrorResponse(401, "Unauthorized", "Acceso denegado: " + ex.getMessage());
    }
}
