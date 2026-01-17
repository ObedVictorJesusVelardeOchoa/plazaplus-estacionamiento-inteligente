package modelo.estructuras;

/**
 * Implementación simple de un conjunto (Set) que no permite duplicados.
 * Basado en ListaEnlazada pero garantiza unicidad de elementos.
 * 
 * @param <T> El tipo de dato que almacenará el conjunto.
 */
public class ConjuntoSimple<T> {
    
    private ListaEnlazada<T> elementos;
    
    public ConjuntoSimple() {
        this.elementos = new ListaEnlazada<>();
    }
    
    /**
     * Agrega un elemento al conjunto si no existe.
     * @param elemento El elemento a agregar.
     * @return true si el elemento fue agregado, false si ya existía.
     */
    public boolean agregar(T elemento) {
        if (contiene(elemento)) {
            return false;
        }
        elementos.agregarAlFinal(elemento);
        return true;
    }
    
    /**
     * Verifica si el conjunto contiene un elemento.
     * @param elemento El elemento a buscar.
     * @return true si el elemento está en el conjunto.
     */
    public boolean contiene(T elemento) {
        return elementos.contiene(elemento);
    }
    
    /**
     * Elimina un elemento del conjunto.
     * @param elemento El elemento a eliminar.
     * @return true si el elemento fue eliminado.
     */
    public boolean eliminar(T elemento) {
        return elementos.eliminar(elemento);
    }
    
    /**
     * Obtiene el tamaño del conjunto.
     * @return El número de elementos.
     */
    public int getTamano() {
        return elementos.getTamano();
    }
    
    /**
     * Verifica si el conjunto está vacío.
     * @return true si no hay elementos.
     */
    public boolean estaVacio() {
        return elementos.estaVacia();
    }
    
    /**
     * Elimina todos los elementos del conjunto.
     */
    public void limpiar() {
        elementos.limpiar();
    }
    
    /**
     * Convierte el conjunto a un array.
     * @param a Un array del tipo genérico.
     * @return Un array con todos los elementos.
     */
    public T[] toArray(T[] a) {
        return elementos.toArray(a);
    }
}
