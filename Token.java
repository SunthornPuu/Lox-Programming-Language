// Token is called Lexeme ( a chunk of letter )
// That's why scanning is also called lexical analysis

import java.util.List;

enum TokenType {
        // symbol stuff
        LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, // parentheses braces
        COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,
        BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL,
        GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
        // literals (fixed value)
        IDENTIFIER, STRING, NUMBER, // not that all number in lox will be stored by10^-2 precision
        // keyword
        AND, OR, CLASS, IF, ELSE, FALSE, TRUE, FUN, FOR, WHILE,
        NIL, PRINT, RETURN, SUPER, THIS, VAR, EOF, ERROR

}

// enum is a data type that allow predefining value
// value of that enum type must be defined by that enum

public class Token {
        final TokenType type;
        final String lexeme;
        final Object literal;
        final int line;
        // final keyword make variable can be assigned only once
        Token(TokenType type, String lexeme, Object literal, int line){
                this.type = type;
                this.lexeme = lexeme;
                this.literal = literal;
                this.line = line;
        }
        public String toString(){
                return type + " " + lexeme + " " + literal; // used for debugging
        }

}
