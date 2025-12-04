public class Token {
    private final TokenType tipo;
    private final String lexema;

    public Token(TokenType tipo, String lexema) {
        this.tipo = tipo;
        this.lexema = lexema;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public String getLexema() {
        return lexema;
    }

    @Override
    public String toString() {
        // Formato para el archivo tokens_salida.txt: Token <tab> Lexema
        return tipo + "\t" + lexema;
    }
}
