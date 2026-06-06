import java.util.Arrays;
import java.util.Random;

public class BubbleSort {

    /** EDICION DE VALORES **/

    static final int N = 100000;  // Tamaño del arreglo
    // Distribución: 4 aleatorio, 3 ordenado, 3 inverso


    public static void main(String[] args) {

        System.out.println("BUBBLE SORT  O(n²)");

        System.out.println("  N = " + N);
        System.out.println("  Distribución: 4 aleatorio | 3 ordenado | 3 inverso");
        System.out.println();


        double mediaAleatorio = medirTiempo("ALEATORIO", generarArreglo("ALEATORIO", N), 4);
        double mediaOrdenado  = medirTiempo("ORDENADO",  generarArreglo("ORDENADO",  N), 3);
        double mediaInverso   = medirTiempo("INVERSO",   generarArreglo("INVERSO",   N), 3);

        double mediaGlobal = (mediaAleatorio * 4 + mediaOrdenado * 3 + mediaInverso * 3) / 10.0;
        System.out.println("  ══════════════════════════════════════");
        System.out.printf("  MEDIA GLOBAL (10 pruebas) → %.0f ns  |  h = N/A%n", mediaGlobal);
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

    static double medirTiempo(String tipo, int[] original, int repeticiones) {
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
        return media;
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
