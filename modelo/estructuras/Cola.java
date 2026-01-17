package modelo.estructuras;

/**
 * Representa una cola (Queue) genérica implementada con lista enlazada. Sigue
 * el principio FIFO (First In, First Out).
 *
 * @param <T> El tipo de dato que almacenará la cola.
 */
public class Cola<T> implements ICola<T> {

    private NodoLista<T> frente;
    private NodoLista<T> fin;
    private int tamano;

    /**
     * Constructor para crear una cola vacía.
     */
    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamano = 0;
    }

    /**
     * Devuelve el número de elementos en la cola.
     *
     * @return el tamaño de la cola.
     */
    @Override
    public int getTamano() {
        return tamano;
    }

    /**
     * Comprueba si la cola está vacía.
     *
     * @return true si la cola no tiene elementos.
     */
    @Override
    public boolean estaVacia() {
        return frente == null;
    }

    /**
     * Agrega un elemento al final de la cola.
     *
     * @param dato El dato a agregar.
     */
    @Override
    public void encolar(T dato) {
        NodoLista<T> nuevoNodo = new NodoLista<>(dato);
        if (estaVacia()) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.setSiguiente(nuevoNodo);
            fin = nuevoNodo;
        }
        tamano++;
    }

    /**
     * Elimina y devuelve el elemento al frente de la cola.
     *
     * @return El elemento al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        if (frente == null) {
            fin = null; // La cola quedó vacía
        }
        tamano--;
        return dato;
    }

    /**
     * Devuelve el elemento al frente de la cola sin eliminarlo.
     *
     * @return El elemento al frente.
     * @throws IllegalStateException si la cola está vacía.
     */
    @Override
    public T verFrente() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return frente.getDato();
    }

    /**
     * Devuelve el elemento al final de la cola sin eliminarlo.
     *
     * @return El elemento al final.
     * @throws IllegalStateException si la cola está vacía.
     */
    public T verFin() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola está vacía");
        }
        return fin.getDato();
    }

    /**
     * Limpia todos los elementos de la cola.
     */
    @Override
    public void limpiar() {
        frente = null;
        fin = null;
        tamano = 0;
    }

    /**
     * Convierte la cola a una lista enlazada (del frente al final).
     *
     * @return Una lista con todos los elementos de la cola.
     */
    @Override
    public ListaEnlazada<T> toLista() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        NodoLista<T> actual = frente;
        while (actual != null) {
            lista.agregarAlFinal(actual.getDato());
            actual = actual.getSiguiente();
        }
        return lista;
    }

    /**
     * Busca un elemento en la cola.
     *
     * @param dato El dato a buscar.
     * @return true si el elemento existe en la cola.
     */
    @Override
    public boolean contiene(T dato) {
        NodoLista<T> actual = frente;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
}
