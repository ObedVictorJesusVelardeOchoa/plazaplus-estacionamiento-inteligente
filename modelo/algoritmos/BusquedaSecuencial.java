package modelo.algoritmos;

import java.util.Comparator;
import modelo.estructuras.ListaEnlazada;

/**
 * Implementación del algoritmo de Búsqueda Secuencial.
 * 
 * @author Sistema
 */
public class BusquedaSecuencial implements IBusqueda {

    @Override
    public <T> int buscar(Object estructura, T elementoBuscado, Comparator<T> comparador) {
        if (!(estructura instanceof ListaEnlazada)) {
            throw new IllegalArgumentException("La estructura debe ser una ListaEnlazada");
        }
        
        @SuppressWarnings("unchecked")
        ListaEnlazada<T> lista = (ListaEnlazada<T>) estructura;
        
        for (int i = 0; i < lista.getTamano(); i++) {
            if (comparador.compare(lista.get(i), elementoBuscado) == 0) {
                return i;
            }
        }
        return -1;
    }
}
