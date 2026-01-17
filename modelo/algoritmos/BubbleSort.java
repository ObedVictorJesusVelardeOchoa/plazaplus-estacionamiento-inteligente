package modelo.algoritmos;

import java.util.Comparator;

/**
 * Implementación del algoritmo de ordenamiento Bubble Sort.
 * 
 * @author Sistema
 */
public class BubbleSort implements IOrdenamiento {

    @Override
    public <T> void ordenar(T[] array, Comparator<T> comparador) {
        int n = array.length;
        boolean intercambiado;
        for (int i = 0; i < n - 1; i++) {
            intercambiado = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparador.compare(array[j], array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    intercambiado = true;
                }
            }
            if (!intercambiado) {
                break;
            }
        }
    }
}
