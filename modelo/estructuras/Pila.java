package modelo.estructuras;


/**
 * Representa una pila (Stack) genérica implementada con lista enlazada.
 * Sigue el principio LIFO (Last In, First Out).
 * @param <T> El tipo de dato que almacenará la pila.
 */
public class Pila<T> implements IPila<T> {

    private NodoLista<T> tope;
    private int tamano;

    /**
     * Constructor para crear una pila vacía.
     */
    public Pila() {
        this.tope = null;
        this.tamano = 0;
    }

    /**
     * Devuelve el número de elementos en la pila.
     * @return el tamaño de la pila.
     */
    @Override
    public int getTamano() {
        return tamano;
    }

    /**
     * Comprueba si la pila está vacía.
     * @return true si la pila no tiene elementos.
     */
    @Override
    public boolean estaVacia() {
        return tope == null;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param dato El dato a agregar.
     */
    @Override
    public void apilar(T dato) {
        NodoLista<T> nuevoNodo = new NodoLista<>(dato);
        nuevoNodo.setSiguiente(tope);
        tope = nuevoNodo;
        tamano++;
    }

    /**
     * Elimina y devuelve el elemento en el tope de la pila.
     * @return El elemento en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    @Override
    public T desapilar() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        T dato = tope.getDato();
        tope = tope.getSiguiente();
        tamano--;
        return dato;
    }

    /**
     * Devuelve el elemento en el tope de la pila sin eliminarlo.
     * @return El elemento en el tope.
     * @throws IllegalStateException si la pila está vacía.
     */
    @Override
    public T verTope() {
        if (estaVacia()) {
            throw new IllegalStateException("La pila está vacía");
        }
        return tope.getDato();
    }

    /**
     * Limpia todos los elementos de la pila.
     */
    @Override
    public void limpiar() {
        tope = null;
        tamano = 0;
    }

    /**
     * Convierte la pila a una lista enlazada (del tope al fondo).
     * @return Una lista con todos los elementos de la pila.
     */
    @Override
    public ListaEnlazada<T> toLista() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        NodoLista<T> actual = tope;
        while (actual != null) {
            lista.agregarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    /**
     * Busca un elemento en la pila.
     * @param dato El dato a buscar.
     * @return true si el elemento existe en la pila.
     */
    @Override
    public boolean contiene(T dato) {
        NodoLista<T> actual = tope;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
