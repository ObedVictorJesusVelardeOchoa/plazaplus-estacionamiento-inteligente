package modelo.estructuras;

import modelo.estructuras.NodoLista;

public class ListaEnlazada<T> implements ILista<T> {

    private NodoLista<T> cabeza; // Primer nodo de la lista
    private int tamano;     // Número de elementos en la lista

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamano = 0;
    }

    /**
     * Devuelve el número de elementos en la lista.
     * @return el tamaño de la lista.
     */
    @Override
    public int getTamano() {
        return tamano;
    }

    /**
     * Comprueba si la lista está vacía.
     * @return true si la lista no tiene elementos.
     */
    @Override
    public boolean estaVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un nuevo elemento al final de la lista.
     * @param dato El dato a agregar.
     */
    @Override
    public void agregarAlFinal(T dato) {
        NodoLista<T> nuevoNodo = new NodoLista<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            NodoLista<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamano++;
    }

    /**
     * Elimina la primera ocurrencia de un dato en la lista.
     * @param dato El dato a eliminar, comparado con .equals().
     * @return true si el elemento fue encontrado y eliminado.
     */
    @Override
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }

        if (cabeza.getDato().equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamano--;
            return true;
        }

        NodoLista<T> actual = cabeza;
        while (actual.getSiguiente() != null && !actual.getSiguiente().getDato().equals(dato)) {
            actual = actual.getSiguiente();
        }

        if (actual.getSiguiente() != null) {
            NodoLista<T> nodoAEliminar = actual.getSiguiente();
            actual.setSiguiente(nodoAEliminar.getSiguiente());
            tamano--;
            return true;
        }

        return false;
    }

    /**
     * Obtiene el dato en una posición específica (índice).
     * @param indice La posición del dato a obtener.
     * @return El dato en esa posición.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    @Override
    public T get(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoLista<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    
    /**
     * Convierte la lista a un Array.
     * @param a Un array del tipo genérico (ej. new Usuario[0]).
     * @return Un array con todos los elementos de la lista.
     */
    @Override
    public T[] toArray(T[] a) {
        @SuppressWarnings("unchecked")
        T[] array = (T[]) java.lang.reflect.Array.newInstance(
                a.getClass().getComponentType(), tamano);
        
        NodoLista<T> actual = cabeza;
        int i = 0;
        while (actual != null) {
            array[i++] = actual.getDato();
            actual = actual.getSiguiente();
        }
        return array;
    }
    
    /**
     * Verifica si la lista contiene un elemento específico.
     * @param dato El elemento a buscar.
     * @return true si el elemento está en la lista.
     */
    public boolean contiene(T dato) {
        NodoLista<T> actual = cabeza;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    
    /**
     * Elimina todos los elementos de la lista.
     */
    public void limpiar() {
        cabeza = null;
        tamano = 0;
    }
    
    /**
     * Reemplaza el elemento en el índice especificado con un nuevo valor.
     * @param indice La posición del elemento a reemplazar.
     * @param nuevoDato El nuevo valor.
     * @return El valor anterior en esa posición.
     * @throws IndexOutOfBoundsException si el índice es inválido.
     */
    public T reemplazar(int indice, T nuevoDato) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
        NodoLista<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        T datoAnterior = actual.getDato();
        actual.setDato(nuevoDato);
        return datoAnterior;
    }
}
