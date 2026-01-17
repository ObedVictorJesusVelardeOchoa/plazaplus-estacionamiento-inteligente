package modelo.algoritmos;

import java.util.Comparator;

/**
 * Interfaz que define los contratos para algoritmos de búsqueda.
 * Permite implementar diferentes estrategias de búsqueda.
 * 
 * @author Sistema
 */
public interface IBusqueda {
    
    /**
     * Realiza una búsqueda en una estructura de datos.
     * @param <T> El tipo de dato a buscar.
     * @param estructura La estructura de datos donde buscar.
     * @param elementoBuscado El valor que se busca.
     * @param comparador Define cómo se comparan dos elementos.
     * @return El índice del elemento encontrado, o -1 si no se encuentra.
     */
    <T> int buscar(Object estructura, T elementoBuscado, Comparator<T> comparador);
}
