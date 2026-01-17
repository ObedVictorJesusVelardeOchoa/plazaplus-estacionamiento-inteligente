package controlador;

import modelo.entidades.SolicitudEspera;
import modelo.entidades.Ticket;
import modelo.entidades.TipoVehiculo;
import modelo.gestores.GestorPrincipal;
import modelo.gestores.SistemaEstacionamiento;
import modelo.estructuras.Cola;
import modelo.estructuras.ListaEnlazada;

/**
 * Controlador para el panel de administrador.
 * Maneja la lógica de gestión del estacionamiento usando el sistema integrado.
 */
public class ControladorAdministrador {
    private final GestorPrincipal gestorPrincipal;
    private final ControladorEstadias controladorEstadias;
    
    public ControladorAdministrador() {
        this.gestorPrincipal = SistemaEstacionamiento.getInstancia().getGestorPrincipal();
        this.controladorEstadias = new ControladorEstadias();
        System.out.println("ControladorAdministrador: Sistema integrado inicializado.");
    }
    
    /**
     * Constructor alternativo (mantiene compatibilidad pero usa sistema integrado).
     */
    public ControladorAdministrador(int numPisos, int numSectores, int numPlazasPorSector) {
        this.gestorPrincipal = SistemaEstacionamiento.getInstancia().getGestorPrincipal();
        this.controladorEstadias = new ControladorEstadias();
        System.out.println("ControladorAdministrador: Sistema integrado inicializado.");
    }
    
    /**
     * Registra el ingreso de un vehículo al estacionamiento.
     * @param placa Placa del vehículo
     * @param tipo Tipo de vehículo
     * @return Ticket generado si fue exitoso, null si no hay plazas disponibles
     */
    public Ticket registrarIngresoVehiculo(String placa, TipoVehiculo tipo) {
        if (placa == null || placa.trim().isEmpty()) {
            System.err.println("La placa no puede estar vacía.");
            return null;
        }
        
        // Usar DNI por defecto para registro rápido
        return gestorPrincipal.registrarIngreso(placa, tipo, "00000000");
    }
    
    /**
     * Busca un ticket por placa.
     * @param placa Placa del vehículo a buscar
     * @return Ticket encontrado o null
     */
    public Ticket buscarTicketPorPlaca(String placa) {
        return controladorEstadias.buscarTicketPorPlaca(placa);
    }
    
    /**
     * Deshace la última operación de ingreso (usando pila).
     * @return Ticket deshecho o null si no hay operaciones
     */
    public Ticket deshacerUltimaOperacion() {
        modelo.estructuras.Pila<Ticket> historial = gestorPrincipal.getHistorialTickets();
        if (!historial.estaVacia()) {
            return historial.desapilar();
        }
        return null;
    }
    
    /**
     * Obtiene la cantidad de solicitudes en espera (cola).
     * @return Número de elementos en la cola de espera
     */
    public int getCantidadEnEspera() {
        return gestorPrincipal.getCantidadEnEspera();
    }
    
    /**
     * Obtiene la cantidad de operaciones en el historial (pila).
     * @return Número de elementos en la pila de historial
     */
    public int getCantidadEnHistorial() {
        return gestorPrincipal.getHistorialTickets().getTamano();
    }
    
    /**
     * Obtiene la cola de espera.
     * @return Cola de solicitudes en espera
     */
    public Cola<SolicitudEspera> getColaEspera() {
        return new Cola<>();
    }
    
    /**
     * Obtiene TODOS los tickets (historial completo: activos y finalizados).
     * @return Lista enlazada de todos los tickets
     */
    public ListaEnlazada<Ticket> getTodosLosTickets() {
        return gestorPrincipal.getTodosLosTickets();
    }
    
    /**
     * Obtiene la lista de tickets activos (vehículos actualmente en el estacionamiento).
     * @return Lista enlazada de tickets activos
     */
    public ListaEnlazada<Ticket> getTicketsActivos() {
        return gestorPrincipal.getTicketsActivos();
    }
    
    /**
     * Registra el egreso de un vehículo.
     * @param ticket Ticket del vehículo que egresa
     * @return Ticket actualizado con el monto calculado, o null si falla
     */
    public Ticket registrarEgreso(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        
        try {
            Ticket ticketSalida = gestorPrincipal.registrarSalida(ticket.getVehiculo().getPlaca());
            return ticketSalida;
        } catch (Exception e) {
            System.err.println("Error al registrar egreso: " + e.getMessage());
            return null;
        }
    }
}
