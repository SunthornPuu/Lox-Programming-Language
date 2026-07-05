package com.craftinginterpreters.lox;

import JavaDS.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import static com.craftinginterpreters.lox.TokenType.*;
import static com.craftinginterpreters.lox.Token;

public class Scanner {
        private final String source;
        Vector<String> tokens = new Vector<>();
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
                curr++;
        }

        Vector<Character> single_symbols = new Vector<Character>('(', ')','[',']',',','.','-','+',';','*');
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
                return 4;
        }

        void scan(float type){}

        void scan_usymbol(){
                
        }
        void scan_string(){

        }
        void scan_number(){

        }
        void scan_ki(){

        }
}