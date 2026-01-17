package modelo.estructuras;

/**
 * Interfaz que define el contrato para estructuras de datos tipo Pila (Stack).
 * Sigue el principio LIFO (Last In, First Out).
 * 
 * @param <T> El tipo de dato que almacenará la pila.
 * @author Sistema
 */
public interface IPila<T> {
    
    /**
     * Devuelve el número de elementos en la pila.
     * @return el tamaño de la pila.
     */
    int getTamano();
    
    /**
     * Comprueba si la pila está vacía.
     * @return true si la pila no tiene elementos.
     */
    boolean estaVacia();
    
    /**
     * Agrega un elemento al tope de la pila.
     * @param dato El dato a agregar.
     */
    void apilar(T dato);
    
    /**
     * Elimina y devuelve el elemento en el tope de la pila.
     * @return El elemento en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    T desapilar();
    
    /**
     * Devuelve el elemento en el tope de la pila sin eliminarlo.
     * @return El elemento en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    T verTope();
    
    /**
     * Limpia todos los elementos de la pila.
     */
    void limpiar();
    
    /**
     * Busca un elemento en la pila.
     * @param dato El dato a buscar.
     * @return true si el elemento existe en la pila.
     */
    boolean contiene(T dato);
    
    /**
     * Convierte la pila a una lista enlazada.
     * @return Una lista con todos los elementos de la pila.
     */
    ListaEnlazada<T> toLista();
}
