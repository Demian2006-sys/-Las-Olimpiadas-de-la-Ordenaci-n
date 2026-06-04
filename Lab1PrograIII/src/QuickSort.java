import java.util.Arrays;
import java.util.Random;

public class QuickSort {


    /** EDICION DE VALORES **/

    static final int N = 100000;  // Tamaño del arreglo
    // Distribución: 10 aleatorio | 10 ordenado  (sin inverso)


    static int alturaMaxima = 0;

    public static void main(String[] args) {
        System.out.println("QUICK SORT  O(n log n)");

        System.out.println("  N = " + N);
        System.out.println("  Distribución: 10 aleatorio | 10 ordenado  (sin inverso)");
        System.out.println();

        System.out.println("── Pivote: ELEMENTO ALEATORIO ──────────");
        medirTiempo("ALEATORIO", generarAleatorio(N), true,  10);
        medirTiempo("ORDENADO",  generarOrdenado(N),  true,  10);

        System.out.println();
    }

    /** ALGORITMOS **/

    static final Random RND = new Random(42);

    static void quickSortAleatorio(int[] arr, int izq, int der, int prof) {
        if (prof > alturaMaxima) alturaMaxima = prof;
        if (izq < der) {
            int pivotIdx = izq + RND.nextInt(der - izq + 1);
            swap(arr, pivotIdx, der);
            int p = particion(arr, izq, der);
            quickSortAleatorio(arr, izq, p - 1, prof + 1);
            quickSortAleatorio(arr, p + 1, der, prof + 1);
        }
    }



    static void quickSortPrimero(int[] arr, int izq, int der) {
        int[] pila = new int[N * 3];
        int tope = -1;

        pila[++tope] = izq;
        pila[++tope] = der;
        pila[++tope] = 0;

        while (tope >= 0) {
            int prof = pila[tope--];
            der      = pila[tope--];
            izq      = pila[tope--];

            if (prof > alturaMaxima) alturaMaxima = prof;
            if (izq >= der) continue;

            swap(arr, izq, der);
            int p = particion(arr, izq, der);

            pila[++tope] = izq;
            pila[++tope] = p - 1;
            pila[++tope] = prof + 1;

            pila[++tope] = p + 1;
            pila[++tope] = der;
            pila[++tope] = prof + 1;
        }
    }



    static int particion(int[] arr, int izq, int der) {
        int pivote = arr[der];
        int i = izq - 1;
        for (int j = izq; j < der; j++)
            if (arr[j] <= pivote) swap(arr, ++i, j);
        swap(arr, i + 1, der);
        return i + 1;
    }

    static void swap(int[] arr, int a, int b) {
        int tmp = arr[a]; arr[a] = arr[b]; arr[b] = tmp;
    }

    /** MEDICION DE TIEMPO **/

    static void medirTiempo(String tipo, int[] original, boolean pivoteAleatorio, int repeticiones) {
        System.out.printf("  %s (%d pruebas):%n", tipo, repeticiones);
        long totalNs = 0;
        int alturaFinal = 0;
        for (int r = 1; r <= repeticiones; r++) {
            int[] copia = Arrays.copyOf(original, original.length);
            alturaMaxima = 0;
            long inicio = System.nanoTime();
            if (pivoteAleatorio)
                quickSortAleatorio(copia, 0, copia.length - 1, 0);
            else
                quickSortPrimero(copia, 0, copia.length - 1);
            long tiempo = System.nanoTime() - inicio;
            totalNs += tiempo;
            alturaFinal = alturaMaxima;
            System.out.printf("    Prueba %2d → %d ns  |  h = %d%n", r, tiempo, alturaFinal);
        }
        double media = totalNs / (double) repeticiones;
        System.out.printf("    Media    → %.0f ns  |  h = %d%n%n", media, alturaFinal);
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
}