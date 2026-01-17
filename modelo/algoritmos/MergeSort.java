package modelo.algoritmos;

import java.util.Comparator;

/**
 * Implementación del algoritmo de ordenamiento Merge Sort.
 * 
 * @author Sistema
 */
public class MergeSort implements IOrdenamiento {

    @Override
    public <T> void ordenar(T[] array, Comparator<T> comparador) {
        if (array.length > 1) {
            mergeSortRecursivo(array, 0, array.length - 1, comparador);
        }
    }
    
    private <T> void mergeSortRecursivo(T[] array, int izq, int der, Comparator<T> comparador) {
        if (izq < der) {
            int medio = izq + (der - izq) / 2;
            mergeSortRecursivo(array, izq, medio, comparador);
            mergeSortRecursivo(array, medio + 1, der, comparador);
            merge(array, izq, medio, der, comparador);
        }
    }

    private <T> void merge(T[] array, int izq, int medio, int der, Comparator<T> comparador) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        @SuppressWarnings("unchecked")
        T[] arrIzq = (T[]) new Object[n1];
        @SuppressWarnings("unchecked")
        T[] arrDer = (T[]) new Object[n2];

        System.arraycopy(array, izq, arrIzq, 0, n1);
        System.arraycopy(array, medio + 1, arrDer, 0, n2);

        int i = 0, j = 0;
        int k = izq;

        while (i < n1 && j < n2) {
            if (comparador.compare(arrIzq[i], arrDer[j]) <= 0) {
                array[k] = arrIzq[i];
                i++;
            } else {
                array[k] = arrDer[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = arrIzq[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = arrDer[j];
            j++;
            k++;
        }
    }
}
