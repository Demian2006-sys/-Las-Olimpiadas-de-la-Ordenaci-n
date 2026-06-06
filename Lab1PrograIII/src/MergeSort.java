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

        double[] r1 = medirTiempo("ALEATORIO", generarArreglo("ALEATORIO", N), 4);
        double[] r2 = medirTiempo("ORDENADO",  generarArreglo("ORDENADO",  N), 3);
        double[] r3 = medirTiempo("INVERSO",   generarArreglo("INVERSO",   N), 3);

        double mediaGlobal = (r1[0] * 4 + r2[0] * 3 + r3[0] * 3) / 10.0;
        int hGlobal = (int) Math.max(r1[1], Math.max(r2[1], r3[1]));
        System.out.println("  ══════════════════════════════════════");
        System.out.printf("  MEDIA GLOBAL (10 pruebas) → %.0f ns  |  h = %d%n", mediaGlobal, hGlobal);
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

    static double[] medirTiempo(String tipo, int[] original, int repeticiones) {
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
        return new double[]{media, alturaFinal};
    }

    /** GENERADORES DE ARREGLOS **/

    static int[] generarArreglo(String estado, int n) {
        int[] arr = new int[n];
        switch (estado) {
            case "ALEATORIO":
                Random rnd = new Random(42);
                for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(n * 10);
                break;
            case "ORDENADO":
                for (int i = 0; i < n; i++) arr[i] = i;
                break;
            case "INVERSO":
                for (int i = 0; i < n; i++) arr[i] = n - i;
                break;
        }
        return arr;
    }
}
