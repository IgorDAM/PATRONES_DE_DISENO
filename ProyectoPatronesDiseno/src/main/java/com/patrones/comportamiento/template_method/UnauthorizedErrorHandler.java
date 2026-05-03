package com.patrones.comportamiento.template_method;

/**
 * Subclase concreta del patrón Template Method para errores 401.
 *
 * Como el resto de handlers, solo implementa {@code buildResponse}.
 * Nótese que a pesar de tener lógica diferente al 404 o al 500,
 * el flujo log → buildResponse → sendResponse se mantiene idéntico.
 * Eso es precisamente la ventaja del Template Method.
 */
public class UnauthorizedErrorHandler extends ErrorHandler {

    /**
     * Construye una respuesta HTTP 401 Unauthorized.
     *
     * Añade el prefijo "Acceso denegado" para dar contexto al cliente,
     * complementando el mensaje técnico de la excepción.
     *
     * @param ex excepción con el motivo del acceso denegado
     * @return respuesta con código 401 y mensaje de acceso denegado
     */
    @Override
    protected ErrorResponse buildResponse(Exception ex) {
        // HTTP 401: el usuario no está autenticado o no tiene permisos
        return new ErrorResponse(401, "Unauthorized", "Acceso denegado: " + ex.getMessage());
    }
}