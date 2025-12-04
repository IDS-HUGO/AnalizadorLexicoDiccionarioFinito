import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Diccionario {
    private final Map<String, TokenType> mapa = new HashMap<>();

    // rutaArchivo: ruta relativa o absoluta al diccionario.txt
    public Diccionario(String rutaArchivo) {
        cargar(rutaArchivo);
    }

    private void cargar(String rutaArchivo) {
        mapa.clear();
        try {
            List<String> lineas = Files.readAllLines(Paths.get(rutaArchivo));
            for (String linea : lineas) {
                linea = linea.trim();
                if (linea.isEmpty() || linea.startsWith("#")) continue;
                String[] partes = linea.split("\\s+");
                // Esperamos: TOKENTYPE lexema
                if (partes.length >= 2) {
                    String tipoStr = partes[0].trim();
                    String lexema = partes[1].trim();
                    try {
                        TokenType tt = TokenType.valueOf(tipoStr);
                        mapa.put(lexema, tt);
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Aviso: token type desconocido en diccionario: " + tipoStr);
                    }
                } else {
                    System.err.println("Aviso: línea ignorada (formato inválido): " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error leyendo diccionario: " + rutaArchivo + " -> " + e.getMessage());
        }
    }

    public TokenType buscarTipo(String lexema) {
        return mapa.get(lexema);
    }

    public boolean contiene(String lexema) {
        return mapa.containsKey(lexema);
    }

    public Set<String> obtenerLexemas() {
        return Collections.unmodifiableSet(mapa.keySet());
    }
}
