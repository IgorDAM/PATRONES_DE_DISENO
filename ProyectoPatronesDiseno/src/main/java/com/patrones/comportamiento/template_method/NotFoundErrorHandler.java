package com.patrones.comportamiento.template_method;

/**
 * Subclase concreta del patrón Template Method para errores 404.
 *
 * Solo sobreescribe {@code buildResponse} — el paso variable.
 * Los pasos fijos (log y envío) los hereda de {@link ErrorHandler} sin tocarlos.
 * Eso garantiza consistencia: el flujo siempre es el mismo, solo cambia la respuesta.
 */
public class NotFoundErrorHandler extends ErrorHandler {

    /**
     * Construye una respuesta HTTP 404 Not Found.
     *
     * El mensaje de la excepción ya describe qué recurso no se encontró,
     * así que se reutiliza directamente como detalle de la respuesta.
     *
     * @param ex excepción con el mensaje de qué no se encontró
     * @return respuesta con código 404 y el mensaje de la excepción
     */
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        // HTTP 404: el recurso solicitado no existe en el servidor
        return new ErrorResponse(404, "Not Found", ex.getMessage());
    }
}