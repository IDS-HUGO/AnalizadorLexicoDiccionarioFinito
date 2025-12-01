import java.util.HashMap;
import java.util.Map;

public class DFA {
    // Clase interna para representar un estado del DFA
    private static class Estado {
        Map<Character, Estado> transiciones;
        String tipoToken; // null si no es estado final
        
        public Estado() {
            this.transiciones = new HashMap<>();
            this.tipoToken = null;
        }
        
        public boolean esFinal() {
            return tipoToken != null;
        }
    }
    
    private Estado estadoInicial;
    
    /**
     * Constructor que inicializa el DFA con todas las palabras clave
     */
    public DFA() {
        estadoInicial = new Estado();
        construirDFA();
    }
    
    /**
     * Construye el DFA agregando todas las palabras clave y sus transiciones
     * Implementa la estructura descrita en el diagrama de transición del PDF
     */
    private void construirDFA() {
        // Agregar palabra "si" -> KW_SI
        agregarPalabra("si", "KW_SI");
        
        // Agregar palabra "sino" -> KW_SINO
        agregarPalabra("sino", "KW_SINO");
        
        // Agregar palabra "mientras" -> KW_MIENTRAS
        agregarPalabra("mientras", "KW_MIENTRAS");
        
        // Agregar palabra "repite" -> KW_REPITE
        agregarPalabra("repite", "KW_REPITE");
        
        // Agregar palabra "hasta" -> KW_HASTA
        agregarPalabra("hasta", "KW_HASTA");
        
        // Agregar palabra "inicio" -> KW_INICIO
        agregarPalabra("inicio", "KW_INICIO");
        
        // Agregar palabra "fin" -> KW_FIN
        agregarPalabra("fin", "KW_FIN");
        
        // Agregar palabra "imprimir" -> KW_IMPRIMIR
        agregarPalabra("imprimir", "KW_IMPRIMIR");
    }
    
    /**
     * Agrega una palabra al DFA creando estados y transiciones
     * @param palabra La palabra clave a agregar
     * @param tipoToken El tipo de token asociado a esta palabra
     */
    private void agregarPalabra(String palabra, String tipoToken) {
        Estado estadoActual = estadoInicial;
        
        // Crear transiciones para cada carácter de la palabra
        for (char c : palabra.toCharArray()) {
            if (!estadoActual.transiciones.containsKey(c)) {
                estadoActual.transiciones.put(c, new Estado());
            }
            estadoActual = estadoActual.transiciones.get(c);
        }
        
        // Marcar el último estado como final y asignar tipo de token
        estadoActual.tipoToken = tipoToken;
    }
    
    /**
     * Reconoce una cadena usando el DFA
     * @param cadena La cadena a reconocer
     * @return El tipo de token si es una palabra clave válida, null si no lo es
     */
    public String reconocer(String cadena) {
        Estado estadoActual = estadoInicial;
        
        // Procesar cada carácter de la cadena
        for (char c : cadena.toCharArray()) {
            if (!estadoActual.transiciones.containsKey(c)) {
                // No hay transición definida -> cadena rechazada
                return null;
            }
            estadoActual = estadoActual.transiciones.get(c);
        }
        
        // Verificar si terminamos en un estado final
        if (estadoActual.esFinal()) {
            return estadoActual.tipoToken;
        }
        
        return null; // No es estado final -> cadena rechazada
    }
    
    /**
     * Verifica si una cadena es una palabra clave válida
     * @param cadena La cadena a verificar
     * @return true si es palabra clave, false si no
     */
    public boolean esPalabraClave(String cadena) {
        return reconocer(cadena) != null;
    }
}
