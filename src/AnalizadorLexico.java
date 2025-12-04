// AnalizadorLexico.java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AnalizadorLexico {

    private final Diccionario diccionario;
    private List<Token> tokens = new ArrayList<>();

    // Constructor recibe la ruta del diccionario
    public AnalizadorLexico(String rutaDiccionario) {
        this.diccionario = new Diccionario(rutaDiccionario);
    }

    // Analiza el archivo de entrada y genera la lista de tokens en memoria
    public void analizar(String rutaTextoEntrada) {
        tokens.clear();
        List<String> lexemas;
        try {
            lexemas = UtilidadesTexto.extraerLexemas(rutaTextoEntrada);
        } catch (IOException e) {
            System.err.println("Error leyendo archivo de entrada: " + rutaTextoEntrada + " -> " + e.getMessage());
            return;
        }

        for (String lexemaRaw : lexemas) {
            // Conservamos lexema original para la salida, pero para buscar en diccionario usamos minúsculas
            String lexema = lexemaRaw;
            String lexemaLower = lexemaRaw.toLowerCase();

            // Paso A: palabra clave (búsqueda O(1))
            TokenType tipo = diccionario.buscarTipo(lexemaLower);
            if (tipo != null) {
                tokens.add(new Token(tipo, lexema));
                continue;
            }

            // Paso B: identificador (regex propia)
            if (UtilidadesTexto.esIdentificadorValido(lexema)) {
                tokens.add(new Token(TokenType.IDENTIFICADOR, lexema));
                continue;
            }

            // Paso C: error léxico
            tokens.add(new Token(TokenType.ERROR_LEXICO, lexema));
        }
    }

    // Guarda tokens en archivo tokens_salida.txt (sobrescribe)
    public void guardarTokens(String rutaSalida) {
        try {
            List<String> lineas = new ArrayList<>();
            lineas.add("Token\tLexema");
            for (Token t : tokens) {
                lineas.add(t.toString());
            }
            Files.write(Paths.get(rutaSalida), lineas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Archivo generado: " + rutaSalida);
        } catch (IOException e) {
            System.err.println("Error al escribir tokens en: " + rutaSalida + " -> " + e.getMessage());
        }
    }

    public List<Token> obtenerTokens() {
        return Collections.unmodifiableList(tokens);
    }

    public Diccionario getDiccionario() {
        return diccionario;
    }
}
