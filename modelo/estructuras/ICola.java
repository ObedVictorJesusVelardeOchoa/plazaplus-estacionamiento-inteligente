package modelo.estructuras;

/**
 * Interfaz que define el contrato para estructuras de datos tipo Cola (Queue).
 * Sigue el principio FIFO (First In, First Out).
 * 
 * @param <T> El tipo de dato que almacenará la cola.
 * @author Sistema
 */
public interface ICola<T> {
    
    /**
     * Devuelve el número de elementos en la cola.
     * @return el tamaño de la cola.
     */
    int getTamano();
    
    /**
     * Comprueba si la cola está vacía.
     * @return true si la cola no tiene elementos.
     */
    boolean estaVacia();
    
    /**
     * Agrega un elemento al final de la cola.
     * @param dato El dato a agregar.
     */
    void encolar(T dato);
    
    /**
     * Elimina y devuelve el elemento al frente de la cola.
     * @return El elemento al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    T desencolar();
    
    /**
     * Devuelve el elemento al frente de la cola sin eliminarlo.
     * @return El elemento al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    T verFrente();
    
    /**
     * Limpia todos los elementos de la cola.
     */
    void limpiar();
    
    /**
     * Busca un elemento en la cola.
     * @param dato El dato a buscar.
     * @return true si el elemento existe en la cola.
     */
    boolean contiene(T dato);
    
    /**
     * Convierte la cola a una lista enlazada.
     * @return Una lista con todos los elementos de la cola.
     */
    ListaEnlazada<T> toLista();
}
