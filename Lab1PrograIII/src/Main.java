import java.util.Arrays;

public class Main {


/** EDICION DE VALORES **/

  static final int N = 100000;          // Tamaño del arreglo
  static final int REPETICIONES = 10;   // Ejecuciones para calcular la media


  static int alturaMaxima = 0;

  public static void main(String[] args) {

    System.out.println("DESAFÍO DEL PIVOTE: DEGENERACIÓN HACIA LA LISTA");
    System.out.println("Primer elemento como pivote");
    System.out.println("Arreglo: YA ORDENADO");
    System.out.println("  N = " + N + "   |   Repeticiones = " + REPETICIONES);
    System.out.println("  Esperado: h tiende a n → O(n²)");
    System.out.println();

    int[] original = generarOrdenado(N);
    long totalNs = 0;
    int alturaFinal = 0;

    for (int r = 1; r <= REPETICIONES; r++) {
      int[] copia = Arrays.copyOf(original, original.length);
      alturaMaxima = 0;
      long inicio = System.nanoTime();
      quickSortPrimero(copia, 0, copia.length - 1);
      long tiempo = System.nanoTime() - inicio;
      totalNs += tiempo;
      alturaFinal = alturaMaxima;
      System.out.printf("  Prueba %2d → %d ns  |  h = %d%n", r, tiempo, alturaFinal);
    }

    double media = totalNs / (double) REPETICIONES;
    System.out.println();
    System.out.printf("  Media     → %.0f ns%n", media);
    System.out.printf("  Altura h  = %d  (≈ n = %d)%n", alturaFinal, N);
    System.out.println();
    System.out.println("  → El árbol de recursión es unilateral (lista).");
    System.out.println("  → La eficiencia se degradó de O(n log n) a O(n²).");
  }

  //  Primer elemento como pivote (iterativo)

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

      swap(arr, izq, der);          // primer elemento → al final como pivote
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

  /** GENERADOR DE ARREGLO **/

  static int[] generarOrdenado(int n) {
    int[] arr = new int[n];
    for (int i = 0; i < n; i++) arr[i] = i;
    return arr;
  }
}
