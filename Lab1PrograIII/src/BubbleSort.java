import java.util.Arrays;
import java.util.Random;

public class BubbleSort {

    /** EDICION DE VALORES **/

    static final int N = 10000;  // Tamaño del arreglo
    // Distribución: 4 aleatorio, 3 ordenado, 3 inverso


    public static void main(String[] args) {

        System.out.println("BUBBLE SORT  O(n²)");

        System.out.println("  N = " + N);
        System.out.println("  Distribución: 4 aleatorio | 3 ordenado | 3 inverso");
        System.out.println();

        medirTiempo("ALEATORIO", generarAleatorio(N), 4);
        medirTiempo("ORDENADO",  generarOrdenado(N),  3);
        medirTiempo("INVERSO",   generarInverso(N),   3);
    }

    /** ALGORITMO **/

    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j]; arr[j] = arr[j + 1]; arr[j + 1] = tmp;
                }
    }

    /** MEDICION DE TIEMPO **/

    static void medirTiempo(String tipo, int[] original, int repeticiones) {
        System.out.printf("  %s (%d pruebas):%n", tipo, repeticiones);
        long totalNs = 0;
        for (int r = 1; r <= repeticiones; r++) {
            int[] copia = Arrays.copyOf(original, original.length);
            long inicio = System.nanoTime();
            bubbleSort(copia);
            long tiempo = System.nanoTime() - inicio;
            totalNs += tiempo;
            System.out.printf("    Prueba %d → %d ns%n", r, tiempo);
        }
        double media = totalNs / (double) repeticiones;
        System.out.printf("    Media   → %.0f ns  |  h = N/A%n%n", media);
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
