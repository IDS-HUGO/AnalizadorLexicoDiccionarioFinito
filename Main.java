import java.io.IOException;

public class Main {
    // Rutas de los archivos
    private static final String ARCHIVO_DICCIONARIO = "diccionario.txt";
    private static final String ARCHIVO_ENTRADA = "texto_entrada.txt";
    private static final String ARCHIVO_SALIDA = "tokens_salida.txt";
    
    public static void main(String[] args) {
        System.out.println("===============================================================");
        System.out.println("        ANALIZADOR LEXICO - PRACTICA 1                        ");
        System.out.println("        Universidad Politecnica de Chiapas                    ");
        System.out.println("        Lenguajes y Automatas                                 ");
        System.out.println("===============================================================");
        System.out.println();
        
        try {
            // Paso 1: Crear el analizador léxico
            AnalizadorLexico analizador = new AnalizadorLexico();
            
            // Paso 2: Cargar el diccionario de palabras clave
            analizador.cargarDiccionario(ARCHIVO_DICCIONARIO);
            
            // Paso 3: Analizar el texto de entrada
            analizador.analizarTexto(ARCHIVO_ENTRADA);
            
            // Paso 4: Generar el archivo de salida
            analizador.generarArchivoSalida(ARCHIVO_SALIDA);
            
            // Paso 5: Mostrar estadísticas
            analizador.imprimirEstadisticas();
            
            System.out.println("\n===============================================================");
            System.out.println("  ANALISIS COMPLETADO EXITOSAMENTE                           ");
            System.out.println("===============================================================");
            
            // Informacion adicional
            System.out.println("\nExpresion Regular para IDENTIFICADOR:");
            System.out.println("  " + analizador.getRegexIdentificador());
            System.out.println("\nDescripcion:");
            System.out.println("  - Debe comenzar con letra minuscula [a-z]");
            System.out.println("  - Puede continuar con letras, numeros o guiones bajos [a-z0-9_]*");
            System.out.println("  - Ejemplos validos: contador, suma1, valor_total");
            System.out.println("  - Ejemplos invalidos: Contador, 1suma, es-mayor");
            
        } catch (IOException e) {
            System.err.println("ERROR: No se pudo leer o escribir un archivo.");
            System.err.println("Detalle: " + e.getMessage());
            System.err.println("\nVerifique que existan los archivos:");
            System.err.println("  - " + ARCHIVO_DICCIONARIO);
            System.err.println("  - " + ARCHIVO_ENTRADA);
            System.exit(1);
        } catch (Exception e) {
            System.err.println("ERROR INESPERADO: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
