package modelo.estructuras;

/**
 * Interfaz que define el contrato para estructuras de datos tipo Lista.
 * Permite implementar diferentes tipos de listas (enlazadas, dobles, circulares, etc.).
 * 
 * @param <T> El tipo de dato que almacenará la lista.
 * @author Sistema
 */
public interface ILista<T> {
    
    /**
     * Devuelve el número de elementos en la lista.
     * @return el tamaño de la lista.
     */
    int getTamano();
    
    /**
     * Comprueba si la lista está vacía.
     * @return true si la lista no tiene elementos.
     */
    boolean estaVacia();
    
    /**
     * Agrega un nuevo elemento al final de la lista.
     * @param dato El dato a agregar.
     */
    void agregarAlFinal(T dato);
    
    /**
     * Elimina la primera ocurrencia de un dato en la lista.
     * @param dato El dato a eliminar.
     * @return true si el elemento fue encontrado y eliminado.
     */
    boolean eliminar(T dato);
    
    /**
     * Obtiene el dato en una posición específica (índice).
     * @param indice La posición del dato a obtener.
     * @return El dato en esa posición.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    T get(int indice);
    
    /**
     * Convierte la lista a un Array.
     * @param a Un array del tipo genérico.
     * @return Un array con todos los elementos de la lista.
     */
    T[] toArray(T[] a);
}
