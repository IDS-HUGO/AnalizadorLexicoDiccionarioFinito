public class Token {
    private String tipo;
    private String lexema;
    
    /**
     * Constructor de Token
     * @param tipo El tipo de token (ej: KW_SI, IDENTIFICADOR)
     * @param lexema La cadena de caracteres que representa el token
     */
    public Token(String tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }
    
    /**
     * Obtiene el tipo del token
     * @return String con el tipo de token
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Obtiene el lexema del token
     * @return String con el lexema
     */
    public String getLexema() {
        return lexema;
    }
    
    /**
     * Representación en cadena del token
     * @return String formateado como "Token: tipo, Lexema: lexema"
     */
    @Override
    public String toString() {
        return String.format("%-20s %s", tipo, lexema);
    }
}
