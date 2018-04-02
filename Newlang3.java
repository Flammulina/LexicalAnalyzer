/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newlang3;

import java.io.*;
import java.util.*;
/**
 *
 * @author c0115058
 */
public class Newlang3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LexicalAnalyzer la = new LexicalAnalyzerImpl("sample1.txt");
        while(true){
        try{
            LexicalUnit lu = la.get();
            System.out.println(lu);
            //if() break;
            //return new LexiculUnit
            //PushBackReader unread()
            if(lu.getType()==LexicalType.EOF)break;
        }catch(Exception e){
            e.printStackTrace();
            //System.out.println("erorr");
            break;
        }
    }
    }
    
}
