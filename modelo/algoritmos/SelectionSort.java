package modelo.algoritmos;

import java.util.Comparator;

/**
 * Implementación del algoritmo de ordenamiento Selection Sort.
 * 
 * @author Sistema
 */
public class SelectionSort implements IOrdenamiento {

    @Override
    public <T> void ordenar(T[] array, Comparator<T> comparador) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int indiceMinimo = i;
            for (int j = i + 1; j < n; j++) {
                if (comparador.compare(array[j], array[indiceMinimo]) < 0) {
                    indiceMinimo = j;
                }
            }
            T temp = array[indiceMinimo];
            array[indiceMinimo] = array[i];
            array[i] = temp;
        }
    }
}
