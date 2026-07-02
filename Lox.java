package com.craftinginterpreter3s.lox;
//package is like a namespace for all the stuff implemented here
//if user want to use things in this file, they have to type "com.craftinginterpreters.lox.[blahblah]"
//for a shortcut, they could just import com.craftinginterpreters.lox.*

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

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
                else if (args.length ==1) runFile(args[0]);
                else runPrompt();
        }
        private static void runFile(String path) throws IOException {
                Path fileLocation = Paths.get(path);
                // Path object is a usable location of the system path
                // we could use Paths utililty library with get method for turning string into path object
                byte[] bytes = Files.readAllBytes(fileLocation);
                run(new String(bytes, Charset.defaultCharset()));
                // Charset.defaultCharset will return default decoding ring of your system (UTF-8 for modrn architecture)
                // see, constructor in java build nothing, new key word is needed to create an instance of an object
        }
        private static void runPrompt() throws IOException {
                InputStreamReader input = new InputStreamReader(System.in);
                // InputStream is an abstract class in java.io (I might wanna play later)
                // InputStream is a stream of stuff we give the program (via console, in this case)
                // with InputStreamReader, We could translate those chunks of bytes to char
                // InputStreamReader constructor take stream input as an argument and create an inputreader instance for us
                // with further application, we could put a precise decoder in reader's argument too
                BufferedReader reader = new BufferedReader(input);
                // that is buffered optimization
                // instead of translating input char by char, just allocate some RAM space for those input (buffer)
                // then you could iterating translating faster
                // it needs a Reader object as an argument (it's abstract, we usually use its subclass eg. InputStreamReader)
                while(true){
                        System.out.print(">> ");
                        String line = reader.readLine(); // terminate to a line with '\n'
                        if( line == null ) return ;
                        run(line);
                }


        }
        static void error(int line, String message){
                System.out.println(line + " | " + message);
        }
        private static void run(String source) throws IOException {
                Scanner scanner = new Scanner(source);
                List<Token> tokens = scanner.scanTokens();
                //both Scanner and Token will be implemented later (there are a default object named Scanner, not that one alr)

                //debugging
                for(Token token:tokens)
                        System.out.println(token);
        }
        public class Token {
                
        }
        private static class Scanner {
                public List<Token> scanTokens(){

                }
        }
}