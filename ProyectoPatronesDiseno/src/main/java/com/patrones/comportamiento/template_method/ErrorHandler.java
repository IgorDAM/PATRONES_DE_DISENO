package com.patrones.comportamiento.template_method;

/**
 * Clase abstracta base del patrón Template Method.
 *
 * Define el ESQUELETO del algoritmo de manejo de errores:
 * los pasos fijos están implementados aquí, el paso variable
 * ({@link #buildResponse}) lo implementan las subclases.
 */
public abstract class ErrorHandler {

    /**
     * TEMPLATE METHOD — El esqueleto del algoritmo.
     *
     * Marca {@code final} para que ninguna subclase pueda alterar el flujo:
     *   1. Registrar el error (fijo)
     *   2. Construir la respuesta (variable → lo delega a la subclase)
     *   3. Enviar la respuesta (fijo)
     *
     * @param ex la excepción a manejar
     */
    public final void handle(Exception ex) {
        logError(ex);                          // Paso 1: siempre igual
        ErrorResponse response = buildResponse(ex); // Paso 2: cada subclase lo personaliza
        sendResponse(response);                // Paso 3: siempre igual
    }

    /**
     * Paso fijo: registra el error en el log.
     * Privado para que las subclases no puedan sobreescribirlo ni saltárselo.
     */
    private void logError(Exception ex) {
        System.out.println("  [Log] Error: " + ex.getMessage());
    }

    /**
     * Paso fijo: envía la respuesta HTTP al cliente.
     * Privado por la misma razón que {@link #logError}.
     */
    private void sendResponse(ErrorResponse response) {
        System.out.println("  [Response] HTTP " + response.statusCode + ": " + response.message);
    }

    /**
     * Paso VARIABLE — Hook que cada subclase debe implementar.
     *
     * Es {@code protected} para que solo las subclases lo implementen,
     * y {@code abstract} para forzar que lo hagan obligatoriamente.
     * Cada handler decide el código HTTP y el mensaje apropiado.
     *
     * @param ex la excepción recibida
     * @return la respuesta de error construida por la subclase
     */
    protected abstract ErrorResponse buildResponse(Exception ex);

    /**
     * DTO simple que encapsula los datos de una respuesta de error HTTP.
     * Clase estática interna para mantener todo relacionado en un mismo lugar.
     */
    public static class ErrorResponse {
        public int statusCode;   // Código HTTP (404, 401, 500...)
        public String message;   // Mensaje breve del error
        public String details;   // Detalle técnico adicional

        public ErrorResponse(int statusCode, String message, String details) {
            this.statusCode = statusCode;
            this.message = message;
            this.details = details;
        }
    }
}