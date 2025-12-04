import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;

public class UtilidadesTexto {

    // Regex propio para IDENTIFICADOR: empieza con letra minúscula y puede contener letras minúsculas, dígitos o underscore
    private static final Pattern PATRON_IDENTIFICADOR = Pattern.compile("^[a-z](?:[a-z0-9]|_)*$");

    // Lee el archivo y devuelve la lista de lexemas (separando por todo lo que NO sea letra, dígito o _)
    public static List<String> extraerLexemas(String rutaArchivo) throws IOException {
        String contenido = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
        // Separamos por cualquier secuencia de caracteres que no sean letras, dígitos o underscore
        String[] partes = contenido.split("[^A-Za-z0-9_]+");
        List<String> lexemas = new ArrayList<>();
        for (String p : partes) {
            if (p != null && !p.isEmpty()) {
                lexemas.add(p);
            }
        }
        return lexemas;
    }

    public static boolean esIdentificadorValido(String lexema) {
        // Nota: comprobamos exactamente minúsculas en la primera letra según la especificación
        return PATRON_IDENTIFICADOR.matcher(lexema).matches();
    }
}
