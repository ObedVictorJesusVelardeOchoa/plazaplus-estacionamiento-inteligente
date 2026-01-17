package modelo.estructuras;

/**
 * Es una clase genérica, por lo que puede contener cualquier tipo de dato.
 *
 * @param <T> El tipo de dato que almacenará el nodo.
 */
public class NodoArbol<T> {

    private T dato;
    private NodoArbol<T> izquierdo;
    private NodoArbol<T> derecho;

    /**
     * Constructor para crear un nuevo nodo.
     *
     * @param dato El dato que se almacenará en este nodo.
     */
    public NodoArbol(T dato) {
        this.dato = dato;
        this.izquierdo = null;
        this.derecho = null;
    }

    // --- Getters y Setters ---
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoArbol<T> getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoArbol<T> izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoArbol<T> getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoArbol<T> derecho) {
        this.derecho = derecho;
    }

    /**
     * Verifica si el nodo es una hoja (no tiene hijos).
     *
     * @return true si el nodo no tiene hijos.
     */
    public boolean esHoja() {
        return izquierdo == null && derecho == null;
    }
}
