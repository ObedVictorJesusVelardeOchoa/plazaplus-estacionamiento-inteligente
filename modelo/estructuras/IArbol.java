package modelo.estructuras;

/**
 * Interfaz que define el contrato para estructuras de datos tipo Árbol.
 * 
 * @param <T> El tipo de dato que almacenará el árbol.
 * @author Sistema
 */
public interface IArbol<T> {
    
    /**
     * Devuelve el número de elementos en el árbol.
     * @return el tamaño del árbol.
     */
    int getTamano();
    
    /**
     * Comprueba si el árbol está vacío.
     * @return true si el árbol no tiene elementos.
     */
    boolean estaVacio();
    
    /**
     * Inserta un nuevo elemento en el árbol.
     * @param dato El dato a insertar.
     */
    void insertar(T dato);
    
    /**
     * Busca un elemento en el árbol.
     * @param dato El dato a buscar.
     * @return true si el elemento existe en el árbol.
     */
    boolean buscar(T dato);
    
    /**
     * Elimina un elemento del árbol.
     * @param dato El dato a eliminar.
     * @return true si el elemento fue encontrado y eliminado.
     */
    boolean eliminar(T dato);
    
    /**
     * Recorrido inorden (izquierda-raíz-derecha).
     * @return Una lista con los elementos en orden.
     */
    ListaEnlazada<T> recorridoInorden();
    
    /**
     * Recorrido preorden (raíz-izquierda-derecha).
     * @return Una lista con los elementos en preorden.
     */
    ListaEnlazada<T> recorridoPreorden();
    
    /**
     * Recorrido postorden (izquierda-derecha-raíz).
     * @return Una lista con los elementos en postorden.
     */
    ListaEnlazada<T> recorridoPostorden();
    
    /**
     * Calcula la altura del árbol.
     * @return La altura del árbol.
     */
    int altura();
}
