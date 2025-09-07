package co.edu.udistrital.control;

import java.util.List;

public class Organizador {
    // --------------------- BURBUJA ---------------------
    public static ResultadoOrdenamiento burbuja(List<Integer> lista) {
        long comparaciones = 0, intercambios = 0;
        int n = lista.size();
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                comparaciones++;
                if (lista.get(j) > lista.get(j+1)) {
                    int temp = lista.get(j);
                    lista.set(j, lista.get(j+1));
                    lista.set(j+1, temp);
                    intercambios++;
                }
            }
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // --------------------- INSERCIÓN ---------------------
    public static ResultadoOrdenamiento insercion(List<Integer> lista) {
        long comparaciones = 0, intercambios = 0;
        int n = lista.size();
        long inicio = System.currentTimeMillis();
        for (int i = 1; i < n; i++) {
            int key = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j) > key) {
                comparaciones++;
                lista.set(j + 1, lista.get(j));
                intercambios++;
                j--;
            }
            if (j >= 0) comparaciones++;
            lista.set(j + 1, key);
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // --------------------- SELECCIÓN ---------------------
    public static ResultadoOrdenamiento seleccion(List<Integer> lista) {
        long comparaciones = 0, intercambios = 0;
        int n = lista.size();
        long inicio = System.currentTimeMillis();
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                comparaciones++;
                if (lista.get(j) < lista.get(min_idx)) {
                    min_idx = j;
                }
            }
            if (min_idx != i) {
                int temp = lista.get(i);
                lista.set(i, lista.get(min_idx));
                lista.set(min_idx, temp);
                intercambios++;
            }
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // --------------------- MERGE ---------------------
    public static ResultadoOrdenamiento merge(List<Integer> lista) {
        long[] c = {0, 0}; // comparaciones, intercambios
        long inicio = System.currentTimeMillis();
        mergeSort(lista, 0, lista.size() - 1, c);
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(c[0], c[1], fin - inicio);
    }
    private static void mergeSort(List<Integer> lista, int izq, int der, long[] c) {
        if (izq < der) {
            int mid = (izq + der) / 2;
            mergeSort(lista, izq, mid, c);
            mergeSort(lista, mid + 1, der, c);
            mergeMerge(lista, izq, mid, der, c);
        }
    }
    private static void mergeMerge(List<Integer> lista, int izq, int mid, int der, long[] c) {
        int n1 = mid - izq + 1;
        int n2 = der - mid;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = lista.get(izq + i);
        for (int j = 0; j < n2; j++) R[j] = lista.get(mid + 1 + j);
        int i = 0, j = 0, k = izq;
        while (i < n1 && j < n2) {
            c[0]++; // comparaciones
            if (L[i] <= R[j]) {
                lista.set(k, L[i]);
                i++;
            } else {
                lista.set(k, R[j]);
                j++;
            }
            c[1]++; // intercambio (escritura)
            k++;
        }
        while (i < n1) {
            lista.set(k, L[i]);
            i++; k++;
            c[1]++; // intercambio (escritura)
        }
        while (j < n2) {
            lista.set(k, R[j]);
            j++; k++;
            c[1]++; // intercambio (escritura)
        }
    }

    // --------------------- QUICK ---------------------
    public static ResultadoOrdenamiento quick(List<Integer> lista) {
        long[] c = {0, 0}; // comparaciones, intercambios
        long inicio = System.currentTimeMillis();
        quickSort(lista, 0, lista.size() - 1, c);
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(c[0], c[1], fin - inicio);
    }
    private static void quickSort(List<Integer> lista, int low, int high, long[] c) {
        if (low < high) {
            int pi = partition(lista, low, high, c);
            quickSort(lista, low, pi - 1, c);
            quickSort(lista, pi + 1, high, c);
        }
    }
    private static int partition(List<Integer> lista, int low, int high, long[] c) {
        int pivot = lista.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            c[0]++; // comparaciones
            if (lista.get(j) <= pivot) {
                i++;
                // swap i y j
                int temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
                c[1]++; // intercambio
            }
        }
        int temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(high));
        lista.set(high, temp);
        c[1]++; // intercambio final
        return i + 1;
    }
}
