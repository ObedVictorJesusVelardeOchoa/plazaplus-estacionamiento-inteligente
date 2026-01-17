package modelo.algoritmos;

import java.util.Comparator;

/**
 * Interfaz que define el contrato para algoritmos de ordenamiento.
 * Permite implementar diferentes estrategias de ordenamiento.
 * 
 * @author Sistema
 */
public interface IOrdenamiento {
    
    /**
     * Ordena un array de elementos según el comparador proporcionado.
     * @param <T> El tipo de dato del array.
     * @param array El array que se va a ordenar (se modifica en el lugar).
     * @param comparador El criterio para comparar los elementos.
     */
    <T> void ordenar(T[] array, Comparator<T> comparador);
}
