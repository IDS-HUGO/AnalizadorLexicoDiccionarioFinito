// Main.java
import java.util.List;
import java.util.Scanner;

public class Main {
    // Ajusta las rutas si ejecutas desde otra carpeta
    private static final String RUTA_DICCIONARIO = "diccionario.txt";
    private static final String RUTA_ENTRADA = "texto_entrada.txt";
    private static final String RUTA_SALIDA = "tokens_salida.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        AnalizadorLexico analizador = new AnalizadorLexico(RUTA_DICCIONARIO);

        while (true) {
            System.out.println("\n===== ANALIZADOR LEXICO (Practica 1) =====");
            System.out.println("1) Analizar archivo: " + RUTA_ENTRADA);
            System.out.println("2) Mostrar tokens en consola");
            System.out.println("3) Guardar tokens en: " + RUTA_SALIDA);
            System.out.println("4) Mostrar diccionario cargado");
            System.out.println("5) Mostrar regex de palabras clave (R) y regex identificador");
            System.out.println("0) Salir");
            System.out.print("Opcion: ");

            String op = sc.nextLine().trim();
            switch (op) {
                case "1":
                    analizador.analizar(RUTA_ENTRADA);
                    System.out.println("Analisis terminado. (usa opcion 2 o 3 para ver/guardar resultados)");
                    break;
                case "2":
                    List<Token> tokens = analizador.obtenerTokens();
                    if (tokens.isEmpty()) {
                        System.out.println("No hay tokens. Ejecuta la opcion 1 primero.");
                    } else {
                        System.out.println("\nToken\tLexema");
                        for (Token t : tokens) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "3":
                    analizador.guardarTokens(RUTA_SALIDA);
                    break;
                case "4":
                    System.out.println("Diccionario cargado (lexemas):");
                    for (String lex : analizador.getDiccionario().obtenerLexemas()) {
                        System.out.println(lex);
                    }
                    break;
                case "5":
                    // RegEx para las palabras clave (una sola expresión R)
                    System.out.println("RegEx (R) para palabras clave:");
                    System.out.println("^(?:si|sino|mientras|repite|hasta|inicio|fin|imprimir)$");
                    System.out.println("\nRegEx para IDENTIFICADOR (usada en el programa):");
                    System.out.println("^[a-z](?:[a-z0-9]|_)*$  (empieza con minuscula, puede contener digitos o _)");
                    System.out.println("\n(El DFA pedido en la practica corresponde a un trie con transiciones por letra; dibujalo segun el enunciado.)");
                    break;
                case "0":
                    System.out.println("Saliendo...");
                    System.exit(0);
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }
}
