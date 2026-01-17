package modelo.algoritmos;

import java.util.Comparator;

/**
 * Clase fachada para algoritmos de ordenamiento.
 * Delega las operaciones a implementaciones específicas que siguen la interfaz IOrdenamiento.
 * Mantiene compatibilidad con código existente mientras permite flexibilidad mediante interfaces.
 *
 * @author Sistema
 */
public class AlgoritmosOrdenamiento {

    // Implementaciones por defecto
    private static final IOrdenamiento bubbleSortImpl = new BubbleSort();
    private static final IOrdenamiento selectionSortImpl = new SelectionSort();
    private static final IOrdenamiento quickSortImpl = new QuickSort();
    private static final IOrdenamiento mergeSortImpl = new MergeSort();

    /**
     * Ordenamiento Bubble Sort.
     * @param <T> El tipo de dato del array.
     * @param array El array que se va a ordenar (se modifica en el lugar).
     * @param comparador El criterio para comparar los elementos.
     */
    public static <T> void bubbleSort(T[] array, Comparator<T> comparador) {
        bubbleSortImpl.ordenar(array, comparador);
    }

    /**
     * Ordenamiento Selection Sort.
     * @param <T> El tipo de dato del array.
     * @param array El array que se va a ordenar (se modifica en el lugar).
     * @param comparador El criterio para comparar los elementos.
     */
    public static <T> void selectionSort(T[] array, Comparator<T> comparador) {
        selectionSortImpl.ordenar(array, comparador);
    }

    /**
     * Ordenamiento Quick Sort.
     * @param <T> El tipo de dato del array.
     * @param array El array a ordenar.
     * @param bajo El índice inicial de la porción del array a ordenar.
     * @param alto El índice final de la porción del array a ordenar.
     * @param comparador El criterio de comparación.
     */
    public static <T> void quickSort(T[] array, int bajo, int alto, Comparator<T> comparador) {
        quickSortImpl.ordenar(array, comparador);
    }

    /**
     * Ordenamiento Merge Sort.
     * @param <T> El tipo de dato del array.
     * @param array El array a ordenar.
     * @param izq El índice izquierdo de la porción del array a ordenar.
     * @param der El índice derecho de la porción del array a ordenar.
     * @param comparador El criterio de comparación.
     */
    public static <T> void mergeSort(T[] array, int izq, int der, Comparator<T> comparador) {
        mergeSortImpl.ordenar(array, comparador);
    }
    
    /**
     * Permite usar una implementación personalizada de ordenamiento.
     * @param <T> El tipo de dato del array.
     * @param implementacion La implementación del algoritmo de ordenamiento.
     * @param array El array que se va a ordenar.
     * @param comparador El criterio para comparar los elementos.
     */
    public static <T> void ordenarConImplementacion(IOrdenamiento implementacion, T[] array, 
                                                     Comparator<T> comparador) {
        implementacion.ordenar(array, comparador);
    }
}
