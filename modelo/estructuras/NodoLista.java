package modelo.estructuras;

/**
 * Representa un nodo individual para ser usado en estructuras de datos
 * enlazadas. Es una clase genérica, por lo que puede contener cualquier tipo de
 * dato.
 * @param <T> El tipo de dato que almacenará el nodo.
 */
public class NodoLista<T> {

    private T dato;
    private NodoLista<T> siguiente;

    /**
     * Constructor para crear un nuevo nodo.
     *
     * @param dato El dato que se almacenará en este nodo.
     */
    public NodoLista(T dato) {
        this.dato = dato;
        this.siguiente = null; // Por defecto, no apunta a ningún otro nodo
    }

    // --- Getters y Setters ---
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoLista<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoLista<T> siguiente) {
        this.siguiente = siguiente;
    }
}
