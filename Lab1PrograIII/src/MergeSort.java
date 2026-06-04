import java.util.Arrays;
import java.util.Random;

public class MergeSort {

    /** EDICION DE VALORES **/

    static final int N = 100000;  // Tamaño del arreglo
    // Distribución: 4 aleatorio, 3 ordenado, 3 inverso


    static int alturaMaxima = 0;

    public static void main(String[] args) {

        System.out.println("MERGE SORT  O(n log n)");
        System.out.println("  N = " + N);
        System.out.println("  Distribución: 4 aleatorio | 3 ordenado | 3 inverso");
        System.out.println();

        medirTiempo("ALEATORIO", generarAleatorio(N), 4);
        medirTiempo("ORDENADO",  generarOrdenado(N),  3);
        medirTiempo("INVERSO",   generarInverso(N),   3);
    }

    /** ALGORITMO **/


    static void mergeSort(int[] arr, int izq, int der, int prof) {
        if (prof > alturaMaxima) alturaMaxima = prof;
        if (izq < der) {
            int mid = (izq + der) / 2;
            mergeSort(arr, izq, mid, prof + 1);
            mergeSort(arr, mid + 1, der, prof + 1);
            merge(arr, izq, mid, der);
        }
    }

    static void merge(int[] arr, int izq, int mid, int der) {
        int n1 = mid - izq + 1, n2 = der - mid;
        int[] L = new int[n1], R = new int[n2];
        System.arraycopy(arr, izq, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        int i = 0, j = 0, k = izq;
        while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    /** MEDICION DE TIEMPO **/

    static void medirTiempo(String tipo, int[] original, int repeticiones) {
        System.out.printf("  %s (%d pruebas):%n", tipo, repeticiones);
        long totalNs = 0;
        int alturaFinal = 0;
        for (int r = 1; r <= repeticiones; r++) {
            int[] copia = Arrays.copyOf(original, original.length);
            alturaMaxima = 0;
            long inicio = System.nanoTime();
            mergeSort(copia, 0, copia.length - 1, 0);
            long tiempo = System.nanoTime() - inicio;
            totalNs += tiempo;
            alturaFinal = alturaMaxima;
            System.out.printf("    Prueba %d → %d ns  |  h = %d%n", r, tiempo, alturaFinal);
        }
        double media = totalNs / (double) repeticiones;
        System.out.printf("    Media   → %.0f ns  |  h = %d%n%n", media, alturaFinal);
    }

    /** GENERADORES DE ARREGLOS **/

    static int[] generarAleatorio(int n) {
        int[] arr = new int[n];
        Random rnd = new Random(42);
        for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(n * 10);
        return arr;
    }

    static int[] generarOrdenado(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    static int[] generarInverso(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }
}
