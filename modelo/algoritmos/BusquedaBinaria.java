package modelo.algoritmos;

import java.util.Comparator;

/**
 * Implementación del algoritmo de Búsqueda Binaria.
 * Requiere que los datos estén ordenados.
 * 
 * @author Sistema
 */
public class BusquedaBinaria implements IBusqueda {

    @Override
    public <T> int buscar(Object estructura, T elementoBuscado, Comparator<T> comparador) {
        if (!(estructura instanceof Object[])) {
            throw new IllegalArgumentException("La estructura debe ser un array");
        }
        
        @SuppressWarnings("unchecked")
        T[] datos = (T[]) estructura;
        
        int bajo = 0;
        int alto = datos.length - 1;

        while (bajo <= alto) {
            int medio = bajo + (alto - bajo) / 2;
            T elementoMedio = datos[medio];

            int resultadoCmp = comparador.compare(elementoMedio, elementoBuscado);

            if (resultadoCmp == 0) {
                return medio;
            }

            if (resultadoCmp < 0) {
                bajo = medio + 1;
            } else {
                alto = medio - 1;
            }
        }
        return -1;
    }
}
