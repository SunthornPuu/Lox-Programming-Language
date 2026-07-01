package com.craftinginterpreters.lox;
//package is like a namespace for all the stuff implemented here
//if user want to use things in this file, they have to type "com.craftinginterpreters.lox.[blahblah]"
//for a shortcut, they could just import com.craftinginterpreters.lox.*

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Lox{
        // public and private are used to identify if object/method could be used outside the program (importing, for example)
        // static is used to tell that the object/method could be used without the instance of the parent object created
        public static void main (String[] args) throws IOException{
                // throws keyword is used before the {} of methods
                // throws [exeption nam]: program will throw an error if that exception is true
                // main is always the entry method by standard java
                if (args.length > 1){
                        System.out.println("Usage: jlox [file name].");
                        System.exit(64);
                        // with exit 0 terminal will just kill this program
                        // instead, with exit number, terminal will inform the exit number (developer will know the meaning of each themself)
                }
                else if (args.length = =1) runFile(args[0]);
                else runPrompt();
        }
        private static void runFile(String path) throws IOException {
                Path fileLocation = Paths.get(path);
                // Path object is a usable location of the system path
                // we could use Paths utililty library with get method for turning string into path object
                byte[] bytes = Files.readAllBytes(fileLocation);
                run(new String(bytes, charset()));
        }
}