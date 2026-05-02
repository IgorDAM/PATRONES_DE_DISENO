package com.patrones.estructurales.facade;

/**
 * PATRÓN FACADE - Ejemplo Didáctico
 * 
 * Propósito: Proporcionar una interfaz simplificada a un subsistema complejo.
 * 
 * En este ejemplo, la fachada "OrderProcessingFacade" oculta la complejidad
 * de validar, guardar, notificar y auditar un pedido.
 */

// Componentes internos complejos (simulados)
class OrderValidator {
    public boolean validate(String clientName, Integer quantity) {
        System.out.println("  → Validando: clientName='" + clientName + "', quantity=" + quantity);
        if (clientName == null || clientName.isEmpty()) {
            System.out.println("    ❌ Error: Nombre de cliente requerido");
            return false;
        }
        if (quantity == null || quantity <= 0) {
            System.out.println("    ❌ Error: Cantidad debe ser > 0");
            return false;
        }
        System.out.println("    ✅ Validación OK");
        return true;
    }
}

class OrderRepository {
    public void save(String clientName, Integer quantity) {
        System.out.println("  → Guardando en BD: " + clientName + " x " + quantity + " unidades");
    }
}

class NotificationService {
    public void notifyClient(String clientName) {
        System.out.println("  → Enviando email a " + clientName + "...");
    }
}

class AuditService {
    public void log(String action) {
        System.out.println("  → Registrando auditoría: " + action);
    }
}

/**
 * FACADE - Simplifica el acceso a todos estos componentes
 */
public class OrderProcessingFacade {
    
    private OrderValidator validator;
    private OrderRepository repository;
    private NotificationService notificationService;
    private AuditService auditService;
    
    public OrderProcessingFacade() {
        this.validator = new OrderValidator();
        this.repository = new OrderRepository();
        this.notificationService = new NotificationService();
        this.auditService = new AuditService();
    }
    
    /**
     * Método simple que oculta la complejidad interna
     */
    public void processOrder(String clientName, Integer quantity) {
        System.out.println("\n[Facade] Procesando pedido...");
        
        // Paso 1: Validar
        if (!validator.validate(clientName, quantity)) {
            System.out.println("❌ Pedido rechazado\n");
            return;
        }
        
        // Paso 2: Guardar
        repository.save(clientName, quantity);
        
        // Paso 3: Notificar
        notificationService.notifyClient(clientName);
        
        // Paso 4: Auditar
        auditService.log("Pedido creado por " + clientName);
        
        System.out.println("✅ Pedido procesado exitosamente");
    }
}
