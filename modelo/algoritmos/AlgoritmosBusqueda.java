package modelo.algoritmos;

import java.util.Comparator;
import modelo.estructuras.ListaEnlazada;

public class AlgoritmosBusqueda {

    private static final IBusqueda busquedaSecuencialImpl = new BusquedaSecuencial();
    private static final IBusqueda busquedaBinariaImpl = new BusquedaBinaria();

    public static <T> int busquedaSecuencial(ListaEnlazada<T> lista, T elementoBuscado, Comparator<T> comparador) {
        return busquedaSecuencialImpl.buscar(lista, elementoBuscado, comparador);
    }

    public static <T> int busquedaBinaria(T[] datos, T elementoBuscado, Comparator<T> comparador) {
        return busquedaBinariaImpl.buscar(datos, elementoBuscado, comparador);
    }
    
    public static <T> int buscarConImplementacion(IBusqueda implementacion, Object estructura, 
                                                   T elementoBuscado, Comparator<T> comparador) {
        return implementacion.buscar(estructura, elementoBuscado, comparador);
    }
}
