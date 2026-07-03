package com.craftinginterpreters.lox;

import java.util.ArrayList; //vector
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.craftinginterpreters.lox.TokenType.*;
import static com.craftinginterpreters.lox.Token;

public class Scanner {
        private final String source;
        private final List<Token> tokens = new ArrayList<>();
        private int line = 1;
        private int N = source.length();
        private int i = 0;
        private int s = 0;
        // While ArrayList is a Class, List is an interface
        // interface tell How could program interact with an object
        // implementing with interface instead of class will narrow down allowance of the object
        // and some adjustment flexibility also
        Scanner(String source){
                this.source = source;
        }
        List<Token> scanTokens(){
                while(i<N){
                        scanToken(source.charAt(i));
                        i++;
                }
        }
        void scanToken(char c){
                switch (c) {
                        case '(': addToken(LEFT_PAREN); break;
                        case ')': addToken(RIGHT_PAREN); break;
                        case '{': addToken(LEFT_BRACE); break;
                        case '}': addToken(RIGHT_BRACE); break;
                        case ',': addToken(COMMA); break;
                        case '.': addToken(DOT); break;
                        case '-': addToken(MINUS); break;
                        case '+': addToken(PLUS); break;
                        case ';': addToken(SEMICOLON); break;
                        case '*': addToken(STAR); break;
                        case '/': addToken(SLASH);
                        default: Lox.error(line, "Unexpected character."); break;
                } 
        }
        void addToken(TokenType type){
                addToken(type,null);
                s = i+1;
        }
        void addToken(TokenType type,Object literals){
                String text = source.substring(s,i);
                tokens.add(new Token(type,text,literals,line));
        }

}