/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.control;

/**
 *
 * @author Jhon
 */
import java.util.List;


public class Organizador {

    // ===================== BURBUJA =====================
    public static ResultadoOrdenamiento burbuja(List<Integer> lista) {
        int[] arr = lista.stream().mapToInt(Integer::intValue).toArray();
        long comparaciones = 0, intercambios = 0;
        int n = arr.length;

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                comparaciones++;
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    intercambios++;
                }
            }
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // ===================== INSERCIÓN =====================
    public static ResultadoOrdenamiento insercion(List<Integer> lista) {
        int[] arr = lista.stream().mapToInt(Integer::intValue).toArray();
        long comparaciones = 0, intercambios = 0;

        long inicio = System.currentTimeMillis();
        for (int i = 1; i < arr.length; i++) {
            int clave = arr[i];
            int j = i - 1;
            while (j >= 0) {
                comparaciones++;
                if (arr[j] > clave) {
                    arr[j + 1] = arr[j];
                    intercambios++;
                    j--;
                } else break;
            }
            arr[j + 1] = clave;
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // ===================== SELECCIÓN =====================
    public static ResultadoOrdenamiento seleccion(List<Integer> lista) {
        int[] arr = lista.stream().mapToInt(Integer::intValue).toArray();
        long comparaciones = 0, intercambios = 0;

        long inicio = System.currentTimeMillis();
        for (int i = 0; i < arr.length - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                comparaciones++;
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                int temp = arr[minIdx];
                arr[minIdx] = arr[i];
                arr[i] = temp;
                intercambios++;
            }
        }
        long fin = System.currentTimeMillis();
        return new ResultadoOrdenamiento(comparaciones, intercambios, fin - inicio);
    }

    // ===================== MERGE =====================
    public static ResultadoOrdenamiento merge(List<Integer> lista) {
        int[] arr = lista.stream().mapToInt(Integer::intValue).toArray();
        Contador contador = new Contador();

        long inicio = System.currentTimeMillis();
        mergeSort(arr, 0, arr.length - 1, contador);
        long fin = System.currentTimeMillis();

        return new ResultadoOrdenamiento(contador.comparaciones, contador.intercambios, fin - inicio);
    }

    private static void mergeSort(int[] arr, int izq, int der, Contador contador) {
        if (izq < der) {
            int medio = (izq + der) / 2;
            mergeSort(arr, izq, medio, contador);
            mergeSort(arr, medio + 1, der, contador);
            merge(arr, izq, medio, der, contador);
        }
    }

    private static void merge(int[] arr, int izq, int medio, int der, Contador contador) {
        int n1 = medio - izq + 1;
        int n2 = der - medio;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; i++) L[i] = arr[izq + i];
        for (int j = 0; j < n2; j++) R[j] = arr[medio + 1 + j];

        int i = 0, j = 0, k = izq;
        while (i < n1 && j < n2) {
            contador.comparaciones++;
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
                contador.intercambios++;
            }
        }
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // ===================== QUICK =====================
    public static ResultadoOrdenamiento quick(List<Integer> lista) {
        int[] arr = lista.stream().mapToInt(Integer::intValue).toArray();
        Contador contador = new Contador();

        long inicio = System.currentTimeMillis();
        quickSort(arr, 0, arr.length - 1, contador);
        long fin = System.currentTimeMillis();

        return new ResultadoOrdenamiento(contador.comparaciones, contador.intercambios, fin - inicio);
    }

    private static void quickSort(int[] arr, int low, int high, Contador contador) {
        if (low < high) {
            int pi = partition(arr, low, high, contador);
            quickSort(arr, low, pi - 1, contador);
            quickSort(arr, pi + 1, high, contador);
        }
    }

    private static int partition(int[] arr, int low, int high, Contador contador) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            contador.comparaciones++;
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                contador.intercambios++;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        contador.intercambios++;
        return i + 1;
    }

    // Clase interna para contar comparaciones e intercambios
    private static class Contador {
        long comparaciones = 0;
        long intercambios = 0;
    }
}
