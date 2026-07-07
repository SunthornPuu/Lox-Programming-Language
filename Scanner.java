package com.craftinginterpreters.lox;

import JavaDS.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import java.util.Arrays;

import static com.craftinginterpreters.lox.TokenType.*;
import static com.craftinginterpreters.lox.Token;

public class Scanner {
        private final String source;
        Vector<Token> tokens = new Vector<>();
        private int line = 1;
        private int N = source.length();
        private int curr = 0;
        VString runnString;

        // While ArrayList is a Class, List is an interface
        // interface tell How could program interact with an object
        // implementing with interface instead of class will narrow down allowance of the object
        // and some adjustment flexibility also

        Scanner(String source){
                this.source = source;
        }
        Vector<Token> scanTokens(){
                while(curr<N){
                        runnString.push_back(source.charAt(i));
                        scanToken();
                }
        }

        // I design my running state into 4 groups
        // 1: symbol | 1.1 is single 1.2 is unknown
        // 2: string
        // 3: number
        // 4: keyword and identifier
        // then first check type of current initial and then treat it by their type

        void scanToken(){
                float type = checkType();
                scan(type);
        }

        Vector<Character> single_symbols = new Vector<Character>('(', ')','[',']',',','.','-','+',';','*');
        Vector<TokenType> symbols_type = new Vecto<TokenType>(LEFT_PAREN,RIGHT_PAREN,LEFT_BRACE,RIGHT_BRACE,COMMA,DOT,MINUS,PLUS,SEMICOLON,STAR);
        Vector<Character> unknown_symbols = new Vector<Character>('/', '=','>','<','!');

        private float checkType(){
                char c = runnString.back();
                boolean found = false;
                for(int i = 0;i<10;i++) if(single_symbols.get(i) == c)found = 1;
                if(found) return 1.1;
                for(int i = 0;i<5;i++) if(single_symbols.get(i) == c)found = 1;
                if(found) return 1.2;
                if(c == '\"') return 2;
                if(c >= '0' && c <= '9')return 3;
                if(c == '\\' && source.charAt((curr+1)) == 'n') {curr+=2; line++; return 0;}
                if(c == ' ') {curr++; return 0;}
                return 4;
        }

        void scan(float type){
                if(type == 1.1) scan_symbol();
                else if(type == 1.2) scan_usymbol();
                else if(type == 2) scan_string();
                else if(type == 3) scan_number();
                else if(type == 4) scan_ki();
        }

        void scan_symbol(){
                char c = runString.back();
                for(int i=0;i<10;i++)
                        if(c==single_symbols.get(i))
                                tokens.push_back(Token(symbols_type.get(i),""+c,null,line));
                curr++;
                runnString.pop_back();
        }
        void scan_usymbol(){
                char c = runnString.back();
                if(c=='!'){
                        if(source.charAt(curr+1)=='=') {
                                tokens.push_back(Token(BANG_EQUAL,"!=",null,line));
                                curr++;
                        }
                        else tokens.push_back(Token(BANG,"!",null,line));
                }
                else if(c=='>'){
                        if(source.charAt(curr+1)=='=') {
                                tokens.push_back(Token(GREATER_EQUAL,">=",null,line));
                                curr++;
                        }
                        else tokens.push_back(Token(GREATER,">",null,line));
                }
                else if(c=='<'){
                        if(source.charAt(curr+1)=='=') {
                                tokens.push_back(Token(LESS_EQUAL,"<=",null,line));
                                curr++;
                        }
                        else tokens.push_back(Token(LESS,"<",null,line));
                }
                else if(c=='='){
                        if(source.charAt(curr+1)=='=') {
                                tokens.push_back(Token(EQUAL_EQUAL,"==",null,line));
                                curr++;
                        }
                        else tokens.push_back(Token(EQUAL,"=",null,line));
                }
                else{
                        while(1){
                                curr++;
                                if(source.charAt(curr)=='n'&&source.charAt(curr-1)=='\\'){
                                        line++;
                                        break;}
                        }
                }
                runnString.pop_back();
                curr++;
        }
        void scan_string(){
                while(1){
                        curr++;
                        runnString.push_back(source.charAt(curr));
                        if(source.charAt(c)=='\"')break;
                }
                curr++;
                String literal = runnString.toString().substring(1,runnString.size()-1);
                tokens.push_back(Token(STRING,runnString.toString(),f,literal,line));
                runnString.clear();
        }
        void scan_number(){
                while(1){
                        curr++;
                        char c = source.charAt(curr);
                        if(! ((c>='0'&&c<='9')||c=='.') )break;
                        runnString.push_back(c);
                }
                String s = runnString.toString();
                flaot f = Float.parseFloat(s);
                tokens.push_back(Token(NUMBER,s,f,line));
                runnString.clear();
        }
        boolean legal(char c){
                if(c>='A'&&c<='Z')return true;
                if(c>='a'&&c<='z')return true;
                if(c=='_')return true;
                return false;
        }
        Vector<String> keywords = new Vector("and","or","class","if","else","false","true","fun","for","while","nil","print","return","super","this","var");
        Vector<TokenType> keywords_type = new Vector(AND, OR, CLASS, IF, ELSE, FALSE, TRUE, FUN, FOR, WHILE,
        NIL, PRINT, RETURN, SUPER, THIS, VAR);

        void scan_ki(){
                if(!legal(runnString.back()))tokens.push_back(new Token(ERROR,"Unknown syntax: "+runnString.back(),null,line));
                while(1){
                        curr++;
                        if(legal(source.charAt(curr))){
                                runnString.push_back(source.charAt(curr));
                        }
                        else break;
                }
                String str = runnString.toString();
                runnString.clear();
                for(int i=0;i<16;i++)
                        if(str==keywords.get(i)){
                                tokens.push_back(new Token(keywords_type[i],str,null,line));
                        }
                tokens.push_back(new Token(IDENTIFIER,str,null,line));
        }
}