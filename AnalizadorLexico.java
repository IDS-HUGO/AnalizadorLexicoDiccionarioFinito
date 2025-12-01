import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Clase AnalizadorLexico
 * Implementa la primera fase de un compilador (Scanner/Analizador Léxico).
 * 
 * Responsabilidades:
 * 1. Leer el diccionario de palabras clave
 * 2. Construir el DFA para reconocer palabras clave
 * 3. Procesar el texto de entrada palabra por palabra
 * 4. Clasificar cada palabra como: KW (palabra clave), IDENTIFICADOR o ERROR_LEXICO
 * 5. Generar el archivo de salida con los tokens identificados
 */
public class AnalizadorLexico {
    private DFA dfa;
    private Map<String, String> diccionario;
    private Pattern patternIdentificador;
    private List<Token> tokens;
    
    // Expresión Regular para IDENTIFICADOR:
    // ^[a-z][a-z0-9_]*$ 
    // - Debe comenzar con una letra minúscula
    // - Puede continuar con letras minúsculas, números o guiones bajos
    // - Longitud mínima de 1 carácter
    private static final String REGEX_IDENTIFICADOR = "^[a-z][a-z0-9_]*$";
    
    /**
     * Constructor del Analizador Léxico
     * Inicializa las estructuras de datos necesarias
     */
    public AnalizadorLexico() {
        this.dfa = new DFA();
        this.diccionario = new HashMap<>();
        this.patternIdentificador = Pattern.compile(REGEX_IDENTIFICADOR);
        this.tokens = new ArrayList<>();
    }
    
    /**
     * Carga el diccionario de palabras clave desde un archivo
     * @param rutaArchivo Ruta del archivo diccionario.txt
     * @throws IOException Si hay error al leer el archivo
     */
    public void cargarDiccionario(String rutaArchivo) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo));
        String linea;
        
        System.out.println("=== Cargando Diccionario ===");
        
        while ((linea = reader.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty() || linea.startsWith("#")) {
                continue; // Ignorar líneas vacías o comentarios
            }
            
            // Formato esperado: TIPO_TOKEN lexema
            // Ejemplo: KW_SI si
            String[] partes = linea.split("\\s+");
            if (partes.length >= 2) {
                String tipoToken = partes[0];
                String lexema = partes[1];
                diccionario.put(lexema, tipoToken);
                System.out.println("  Cargada: " + lexema + " -> " + tipoToken);
            }
        }
        
        reader.close();
        System.out.println("Diccionario cargado: " + diccionario.size() + " palabras clave\n");
    }
    
    /**
     * Analiza un archivo de texto y genera los tokens
     * @param rutaEntrada Ruta del archivo texto_entrada.txt
     * @throws IOException Si hay error al leer el archivo
     */
    public void analizarTexto(String rutaEntrada) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(rutaEntrada));
        String linea;
        
        System.out.println("=== Analizando Texto de Entrada ===");
        
        while ((linea = reader.readLine()) != null) {
            // Dividir la línea en palabras (tokens léxicos)
            String[] palabras = linea.trim().split("\\s+");
            
            for (String palabra : palabras) {
                if (palabra.isEmpty()) {
                    continue;
                }
                
                Token token = clasificarPalabra(palabra);
                tokens.add(token);
                System.out.println("  " + token);
            }
        }
        
        reader.close();
        System.out.println("Análisis completado: " + tokens.size() + " tokens identificados\n");
    }
    
    /**
     * Clasifica una palabra según el proceso de análisis léxico:
     * PASO A: Verificar si es palabra clave usando el DFA
     * PASO B: Verificar si es identificador válido usando regex
     * PASO C: Si no cumple ninguna condición, es un error léxico
     * 
     * @param palabra La palabra a clasificar
     * @return Token con la clasificación correspondiente
     */
    private Token clasificarPalabra(String palabra) {
        // PASO A: Verificar si es palabra clave usando el DFA
        String tipoTokenDFA = dfa.reconocer(palabra);
        if (tipoTokenDFA != null) {
            return new Token(tipoTokenDFA, palabra);
        }
        
        // PASO B: Verificar si es identificador válido
        Matcher matcher = patternIdentificador.matcher(palabra);
        if (matcher.matches()) {
            return new Token("IDENTIFICADOR", palabra);
        }
        
        // PASO C: Error léxico (no es palabra clave ni identificador válido)
        return new Token("ERROR_LEXICO", palabra);
    }
    
    /**
     * Genera el archivo de salida con los tokens identificados
     * @param rutaSalida Ruta del archivo tokens_salida.txt
     * @throws IOException Si hay error al escribir el archivo
     */
    public void generarArchivoSalida(String rutaSalida) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(rutaSalida));
        
        // Escribir encabezado de la tabla
        writer.write(String.format("%-20s %s%n", "Token", "Lexema"));
        writer.write("========================================\n");
        
        // Escribir cada token
        for (Token token : tokens) {
            writer.write(token.toString() + "\n");
        }
        
        writer.close();
        System.out.println("=== Archivo de Salida Generado ===");
        System.out.println("Archivo creado: " + rutaSalida);
        System.out.println("Total de tokens: " + tokens.size() + "\n");
    }
    
    /**
     * Obtiene la lista de tokens generados
     * @return Lista de tokens
     */
    public List<Token> getTokens() {
        return tokens;
    }
    
    /**
     * Obtiene estadísticas del análisis léxico
     * @return Mapa con conteos de cada tipo de token
     */
    public Map<String, Integer> obtenerEstadisticas() {
        Map<String, Integer> estadisticas = new HashMap<>();
        
        for (Token token : tokens) {
            String tipo = token.getTipo();
            estadisticas.put(tipo, estadisticas.getOrDefault(tipo, 0) + 1);
        }
        
        return estadisticas;
    }
    
    /**
     * Imprime estadísticas del análisis
     */
    public void imprimirEstadisticas() {
        Map<String, Integer> stats = obtenerEstadisticas();
        
        System.out.println("=== Estadísticas del Análisis ===");
        System.out.println("Total de tokens: " + tokens.size());
        
        int palabrasClave = 0;
        int identificadores = stats.getOrDefault("IDENTIFICADOR", 0);
        int errores = stats.getOrDefault("ERROR_LEXICO", 0);
        
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            if (entry.getKey().startsWith("KW_")) {
                palabrasClave += entry.getValue();
            }
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nResumen:");
        System.out.println("  Palabras Clave: " + palabrasClave);
        System.out.println("  Identificadores: " + identificadores);
        System.out.println("  Errores Léxicos: " + errores);
    }
    
    /**
     * Obtiene la expresión regular utilizada para identificadores
     * @return String con el patrón regex
     */
    public String getRegexIdentificador() {
        return REGEX_IDENTIFICADOR;
    }
}
