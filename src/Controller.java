package com.ulysses.LTA;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author UlyssesZhang
 */
public class Controller {
    
    private static List<File> FileList;
    
    private static List<User> UserList;
    
        public static void Launch(String path) throws IOException, ParseException, InvalidFormatException {
            Loader.Import(path);
            FileList = new ArrayList<>();
            FileList = Loader.getFileList();
                for(int i = 0; i < FileList.size(); i ++) {
                    Loader.Read(FileList.get(i));
                    UserList = new ArrayList<>();
                    UserList = Loader.getUserList();
                    Analysts.getTotalTime(UserList);
                }
                
                
        }
        public static void LaunchMerge(String path) throws IOException, ParseException, InvalidFormatException {
            FileList = new ArrayList<>();
            UserList = new ArrayList<>();
            Loader.Import(path);
            Loader.ReadAll();
            UserList = Loader.getUserList();
            Analysts.getTotalTime(UserList);
        }
}
