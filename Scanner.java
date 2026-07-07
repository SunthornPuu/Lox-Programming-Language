import JavaDS.*;
import java.util.ArrayList;
import java.util.List;

public class Scanner {
        private final String source;
        // Bug #3 fix: Token doesn't implement Comparable, so use ArrayList instead of
        // Vector
        ArrayList<Token> tokens = new ArrayList<>();
        private int line = 1;
        // Bug #4 fix: N can't be initialized here — source is null at field init time
        private int N;
        private int curr = 0;
        // Bug #5 fix: runnString was never initialized
        VString runnString = new VString("");

        // While ArrayList is a Class, List is an interface
        // interface tell How could program interact with an object
        // implementing with interface instead of class will narrow down allowance of
        // the object
        // and some adjustment flexibility also

        Scanner(String source) {
                this.source = source;
                // Bug #4 fix: initialize N here, after source is assigned
                this.N = source.length();
        }

        // Bug #6 fix: used undefined variable 'i' (should be 'curr'), and missing
        // return statement
        List<Token> scanTokens() {
                while (curr < N) {
                        runnString.push_back(source.charAt(curr));
                        scanToken();
                }
                tokens.add(new Token(TokenType.EOF, null, null, line));
                return tokens;
        }

        // I design my running state into 4 groups
        // 1: symbol | 1.1 is single 1.2 is unknown
        // 2: string
        // 3: number
        // 4: keyword and identifier
        // then first check type of current initial and then treat it by their type

        void scanToken() {
                float type = checkType();
                scan(type);
        }

        Vector<Character> single_symbols = new Vector<Character>('(', ')', '{', '}', ',', '.', '-', '+', ';', '*');
        // Bug #7 fix: was 'Vecto' (typo)
        // Bug #1 fix: TokenType constants prefixed with 'TokenType.' since static
        // import from default package is illegal
        Vector<TokenType> symbols_type = new Vector<TokenType>(TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN,
                        TokenType.LEFT_BRACE, TokenType.RIGHT_BRACE, TokenType.COMMA, TokenType.DOT, TokenType.MINUS,
                        TokenType.PLUS, TokenType.SEMICOLON, TokenType.STAR);
        Vector<Character> unknown_symbols = new Vector<Character>('/', '=', '>', '<', '!');

        private float checkType() {
                char c = runnString.back();
                boolean found = false;
                for (int i = 0; i < 10; i++)
                        if (single_symbols.get(i) == c)
                                found = true;
                if (found)
                        return 1.1f;
                found = false;
                for (int i = 0; i < 5; i++)
                        if (unknown_symbols.get(i) == c)
                                found = true;
                if (found)
                        return 1.2f;
                if (c == '"')
                        return 2f;
                if (c >= '0' && c <= '9')
                        return 3f;
                if (c == '\n') {
                        runnString.pop_back();
                        curr++;
                        line++;
                        return 0f;
                }
                if (c == ' ') {
                        runnString.pop_back();
                        curr++;
                        return 0f;
                }
                return 4f;
        }

        void scan(float type) {
                if (type == 1.1f)
                        scan_symbol();
                else if (type == 1.2f)
                        scan_usymbol();
                else if (type == 2f)
                        scan_string();
                else if (type == 3f)
                        scan_number();
                else if (type == 4f)
                        scan_ki();
        }

        void scan_symbol() {
                // Bug #10 fix: was 'runString' (missing one 'n')
                char c = runnString.back();
                for (int i = 0; i < 10; i++)
                        // Bug #11 fix: Token(...) called without 'new'
                        if (c == single_symbols.get(i))
                                tokens.add(new Token(symbols_type.get(i), "" + c, null, line));
                curr++;
                runnString.pop_back();
        }

        void scan_usymbol() {
                char c = runnString.back();
                if (c == '!') {
                        if (curr + 1 < N && source.charAt(curr + 1) == '=') {
                                tokens.add(new Token(TokenType.BANG_EQUAL, "!=", null, line));
                                curr++;
                        } else
                                tokens.add(new Token(TokenType.BANG, "!", null, line));
                } else if (c == '>') {
                        if (curr + 1 < N && source.charAt(curr + 1) == '=') {
                                tokens.add(new Token(TokenType.GREATER_EQUAL, ">=", null, line));
                                curr++;
                        } else
                                tokens.add(new Token(TokenType.GREATER, ">", null, line));
                } else if (c == '<') {
                        if (curr + 1 < N && source.charAt(curr + 1) == '=') {
                                tokens.add(new Token(TokenType.LESS_EQUAL, "<=", null, line));
                                curr++;
                        } else
                                tokens.add(new Token(TokenType.LESS, "<", null, line));
                } else if (c == '=') {
                        if (curr + 1 < N && source.charAt(curr + 1) == '=') {
                                tokens.add(new Token(TokenType.EQUAL_EQUAL, "==", null, line));
                                curr++;
                        } else
                                tokens.add(new Token(TokenType.EQUAL, "=", null, line));
                } else {
                        while (curr + 1 < N) {
                                curr++;
                                if (source.charAt(curr) == '\n') {
                                        line++;
                                        break;
                                }
                        }
                }
                runnString.pop_back();
                curr++;
        }

        void scan_string() {
                while (true) {
                        curr++;
                        runnString.push_back(source.charAt(curr));
                        if (source.charAt(curr) == '"')
                                break;
                }
                curr++;
                String literal = runnString.toString().substring(1, runnString.size() - 1);
                // System.out.println(runnString.toString() + " " + literal);
                tokens.add(new Token(TokenType.STRING, runnString.toString(), literal, line));
                runnString.clear();
        }

        void scan_number() {
                // Bug #12 fix: was 'while(1)'
                while (true) {
                        curr++;
                        char c = source.charAt(curr);
                        if (!((c >= '0' && c <= '9') || c == '.'))
                                break;
                        runnString.push_back(c);
                }
                String s = runnString.toString();
                float f = Float.parseFloat(s);
                // Bug #15 fix: missing 'new'
                tokens.add(new Token(TokenType.NUMBER, s, f, line));
                runnString.clear();
        }

        boolean legal(char c) {
                if (c >= 'A' && c <= 'Z')
                        return true;
                if (c >= 'a' && c <= 'z')
                        return true;
                if (c == '_')
                        return true;
                return false;
        }

        Vector<String> keywords = new Vector<String>("and", "or", "class", "if", "else", "false", "true", "fun", "for",
                        "while", "nil", "print", "return", "super", "this", "var");
        Vector<TokenType> keywords_type = new Vector<TokenType>(TokenType.AND, TokenType.OR, TokenType.CLASS,
                        TokenType.IF, TokenType.ELSE, TokenType.FALSE, TokenType.TRUE, TokenType.FUN, TokenType.FOR,
                        TokenType.WHILE,
                        TokenType.NIL, TokenType.PRINT, TokenType.RETURN, TokenType.SUPER, TokenType.THIS,
                        TokenType.VAR);

        void scan_ki() {
                if (!legal(runnString.back())) {
                        tokens.add(new Token(TokenType.ERROR, "Unknown syntax: " + runnString.back(), null, line));
                        runnString.pop_back();
                        curr++;
                        return;
                }
                while (true) {
                        curr++;
                        if (legal(source.charAt(curr))) {
                                runnString.push_back(source.charAt(curr));
                        } else
                                break;
                }
                String str = runnString.toString();
                runnString.clear();
                boolean isKeyword = false;
                for (int i = 0; i < 16; i++) {
                        if (str.equals(keywords.get(i))) {
                                tokens.add(new Token(keywords_type.get(i), str, null, line));
                                isKeyword = true;
                                break;
                        }
                }
                if (!isKeyword)
                        tokens.add(new Token(TokenType.IDENTIFIER, str, null, line));
        }
}
