/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ulysses.LTA;

import java.io.IOException;
import java.text.ParseException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author UlyssesZhang
 */
public class LoginTimeAnalysis_dev3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, ParseException, InvalidFormatException {
        args = new String[1];
        args[0] = "merge";
        if(args.length != 0){
            if(args[0].equals("merge")) {
                Controller.LaunchMerge("input");
            } else {
                System.out.println("Please inster correct instruction.");
            }
        } else {
            Controller.Launch("input");
        }  
    }
}
