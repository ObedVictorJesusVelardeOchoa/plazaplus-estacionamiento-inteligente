package modelo.estructuras;

import java.util.Comparator;

/**
 * Representa un árbol binario de búsqueda genérico. Los elementos se organizan
 * usando un Comparador personalizado.
 *
 * @param <T> El tipo de dato que almacenará el árbol.
 */
public class ArbolBinario<T> implements IArbol<T> {

    private NodoArbol<T> raiz;
    private int tamano;
    private Comparator<T> comparador;

    /**
     * Constructor para crear un árbol vacío.
     *
     * @param comparador El comparador que se usará para ordenar los elementos.
     */
    public ArbolBinario(Comparator<T> comparador) {
        this.raiz = null;
        this.tamano = 0;
        this.comparador = comparador;
    }
    

    /**
     * Devuelve el número de elementos en el árbol.
     *
     * @return el tamaño del árbol.
     */
    @Override
    public int getTamano() {
        return tamano;
    }

    /**
     * Comprueba si el árbol está vacío.
     *
     * @return true si el árbol no tiene elementos.
     */
    @Override
    public boolean estaVacio() {
        return raiz == null;
    }

    /**
     * Obtiene el nodo raíz del árbol.
     *
     * @return el nodo raíz.
     */
    public NodoArbol<T> getRaiz() {
        return raiz;
    }

    /**
     * Inserta un nuevo elemento en el árbol.
     *
     * @param dato El dato a insertar.
     */
    @Override
    public void insertar(T dato) {
        raiz = insertarRecursivo(raiz, dato);
        tamano++;
    }

    /**
     * Método recursivo auxiliar para insertar un elemento.
     *
     * @param nodo El nodo actual en la recursión.
     * @param dato El dato a insertar.
     * @return El nodo actualizado.
     */
    private NodoArbol<T> insertarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return new NodoArbol<>(dato);
        }

        int comparacion = comparador.compare(dato, nodo.getDato());
        if (comparacion < 0) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), dato));
        }
        // Si es igual, no insertamos duplicados

        return nodo;
    }

    /**
     * Busca un elemento en el árbol.
     *
     * @param dato El dato a buscar.
     * @return true si el elemento existe en el árbol.
     */
    @Override
    public boolean buscar(T dato) {
        return buscarRecursivo(raiz, dato);
    }

    /**
     * Método recursivo auxiliar para buscar un elemento.
     *
     * @param nodo El nodo actual en la recursión.
     * @param dato El dato a buscar.
     * @return true si el dato fue encontrado.
     */
    private boolean buscarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return false;
        }

        int comparacion = comparador.compare(dato, nodo.getDato());
        if (comparacion == 0) {
            return true;
        }

        if (comparacion < 0) {
            return buscarRecursivo(nodo.getIzquierdo(), dato);
        } else {
            return buscarRecursivo(nodo.getDerecho(), dato);
        }
    }

    /**
     * Elimina un elemento del árbol.
     *
     * @param dato El dato a eliminar.
     * @return true si el elemento fue encontrado y eliminado.
     */
    @Override
    public boolean eliminar(T dato) {
        if (!buscar(dato)) {
            return false;
        }
        raiz = eliminarRecursivo(raiz, dato);
        tamano--;
        return true;
    }

    /**
     * Método recursivo auxiliar para eliminar un elemento.
     *
     * @param nodo El nodo actual en la recursión.
     * @param dato El dato a eliminar.
     * @return El nodo actualizado.
     */
    private NodoArbol<T> eliminarRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }

        int comparacion = comparador.compare(dato, nodo.getDato());
        if (comparacion < 0) {
            nodo.setIzquierdo(eliminarRecursivo(nodo.getIzquierdo(), dato));
        } else if (comparacion > 0) {
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), dato));
        } else {
            // Nodo encontrado
            // Caso 1: Nodo sin hijos o con un solo hijo
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();
            } else if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();
            }

            // Caso 2: Nodo con dos hijos
            // Encontrar el sucesor inorden (menor del subárbol derecho)
            T sucesor = encontrarMinimo(nodo.getDerecho());
            nodo.setDato(sucesor);
            nodo.setDerecho(eliminarRecursivo(nodo.getDerecho(), sucesor));
        }

        return nodo;
    }

    /**
     * Encuentra el valor mínimo en un subárbol.
     *
     * @param nodo La raíz del subárbol.
     * @return El valor mínimo.
     */
    private T encontrarMinimo(NodoArbol<T> nodo) {
        while (nodo.getIzquierdo() != null) {
            nodo = nodo.getIzquierdo();
        }
        return nodo.getDato();
    }

    /**
     * Recorrido inorden (izquierda-raíz-derecha).
     *
     * @return Una lista con los elementos en orden.
     */
    @Override
    public ListaEnlazada<T> recorridoInorden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        inordenRecursivo(raiz, lista);
        return lista;
    }

    /**
     * Método auxiliar recursivo para recorrido inorden.
     */
    private void inordenRecursivo(NodoArbol<T> nodo, ListaEnlazada<T> lista) {
        if (nodo != null) {
            inordenRecursivo(nodo.getIzquierdo(), lista);
            lista.agregarAlFinal(nodo.getDato());
            inordenRecursivo(nodo.getDerecho(), lista);
        }
    }

    /**
     * Recorrido preorden (raíz-izquierda-derecha).
     *
     * @return Una lista con los elementos en preorden.
     */
    @Override
    public ListaEnlazada<T> recorridoPreorden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        preordenRecursivo(raiz, lista);
        return lista;
    }

    /**
     * Método auxiliar recursivo para recorrido preorden.
     */
    private void preordenRecursivo(NodoArbol<T> nodo, ListaEnlazada<T> lista) {
        if (nodo != null) {
            lista.agregarAlFinal(nodo.getDato());
            preordenRecursivo(nodo.getIzquierdo(), lista);
            preordenRecursivo(nodo.getDerecho(), lista);
        }
    }

    /**
     * Recorrido postorden (izquierda-derecha-raíz).
     *
     * @return Una lista con los elementos en postorden.
     */
    @Override
    public ListaEnlazada<T> recorridoPostorden() {
        ListaEnlazada<T> lista = new ListaEnlazada<>();
        postordenRecursivo(raiz, lista);
        return lista;
    }

    /**
     * Método auxiliar recursivo para recorrido postorden.
     */
    private void postordenRecursivo(NodoArbol<T> nodo, ListaEnlazada<T> lista) {
        if (nodo != null) {
            postordenRecursivo(nodo.getIzquierdo(), lista);
            postordenRecursivo(nodo.getDerecho(), lista);
            lista.agregarAlFinal(nodo.getDato());
        }
    }

    /**
     * Calcula la altura del árbol.
     *
     * @return La altura del árbol (la raíz tiene altura 1).
     */
    @Override
    public int altura() {
        return alturaRecursiva(raiz);
    }

    /**
     * Método auxiliar recursivo para calcular la altura.
     */
    private int alturaRecursiva(NodoArbol<T> nodo) {
        if (nodo == null) {
            return 0;
        }
        int alturaIzq = alturaRecursiva(nodo.getIzquierdo());
        int alturaDer = alturaRecursiva(nodo.getDerecho());
        return Math.max(alturaIzq, alturaDer) + 1;
    }
    
    public T obtener(T dato) {
        return obtenerRecursivo(raiz, dato);
    }
    
    private T obtenerRecursivo(NodoArbol<T> nodo, T dato) {
        if (nodo == null) {
            return null;
        }
        
        int comparacion = comparador.compare(dato, nodo.getDato());
        if (comparacion == 0) {
            return nodo.getDato();
        }
        
        if (comparacion < 0) {
            return obtenerRecursivo(nodo.getIzquierdo(), dato);
        } else {
            return obtenerRecursivo(nodo.getDerecho(), dato);
        }
    }
}
