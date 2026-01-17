package modelo.algoritmos;

import java.util.Comparator;

/**
 * Implementación del algoritmo de ordenamiento Quick Sort.
 * 
 * @author Sistema
 */
public class QuickSort implements IOrdenamiento {

    @Override
    public <T> void ordenar(T[] array, Comparator<T> comparador) {
        if (array.length > 0) {
            quickSortRecursivo(array, 0, array.length - 1, comparador);
        }
    }
    
    private <T> void quickSortRecursivo(T[] array, int bajo, int alto, Comparator<T> comparador) {
        if (bajo < alto) {
            int indicePivote = particion(array, bajo, alto, comparador);
            quickSortRecursivo(array, bajo, indicePivote - 1, comparador);
            quickSortRecursivo(array, indicePivote + 1, alto, comparador);
        }
    }

    private <T> int particion(T[] array, int bajo, int alto, Comparator<T> comparador) {
        T pivote = array[alto];
        int i = (bajo - 1);

        for (int j = bajo; j < alto; j++) {
            if (comparador.compare(array[j], pivote) <= 0) {
                i++;
                T temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        T temp = array[i + 1];
        array[i + 1] = array[alto];
        array[alto] = temp;

        return i + 1;
    }
}
