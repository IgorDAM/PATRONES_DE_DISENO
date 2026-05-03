package com.patrones.comportamiento.template_method;

/**
 * Subclase concreta del patrón Template Method para errores 500.
 *
 * Maneja errores inesperados del servidor: excepciones no controladas,
 * fallos de base de datos, NullPointerException, etc.
 * Al igual que los demás handlers, solo personaliza {@code buildResponse}.
 */
public class InternalServerErrorHandler extends ErrorHandler {

    /**
     * Construye una respuesta HTTP 500 Internal Server Error.
     *
     * El prefijo "Error inesperado" comunica al cliente que no fue un error
     * de su petición, sino un fallo interno del sistema.
     *
     * @param ex excepción con el detalle técnico del fallo interno
     * @return respuesta con código 500 y descripción del error inesperado
     */
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        // HTTP 500: algo falló en el servidor, no en la petición del cliente
        return new ErrorResponse(500, "Internal Server Error",
                "Error inesperado: " + ex.getMessage());
    }
}