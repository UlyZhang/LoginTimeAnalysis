/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ulysses.LTA;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author UlyssesZhang
 */
public class Loader {
    
    private static List<File> FileList;
    
    private static List<User> UserList;
    
    private static String extensionName;
    
    private static Workbook wb;
    
        public static void Import(String file) {
            final File folder = new File(file);
            Import(folder);
        }
    
        private static void Import(File folder) {
            FileList = new ArrayList<>();
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    Import(fileEntry);
                } else {
                    FileList.add(fileEntry);
                }
            }
        }
        
        public static void ReadAll () throws IOException, ParseException, InvalidFormatException {
            UserList = new ArrayList<>();
            for(int i = 0; i < FileList.size(); i ++) {
                System.out.println("filepath : "+FileList.get(i).getAbsolutePath());
                extensionName = FileList.get(i).toString().substring(FileList.get(i).toString().lastIndexOf("."));
                switch (extensionName) {
                        case ".txt":
                        case ".csv":
                           Read(new BufferedReader(new FileReader(FileList.get(i))));
                            break;
                        case ".xls":
                        case ".xlsx":
                            wb = WorkbookFactory.create(new FileInputStream(FileList.get(i)));
                           Read();
                            break;
                }
            }
        }

        public static void Read (File file) throws IOException, ParseException, InvalidFormatException {
            
            System.out.println("filepath : "+file.getAbsolutePath());
            
            UserList = new ArrayList<>();

            extensionName = file.toString().substring(file.toString().lastIndexOf("."));
                switch (extensionName) {
                        case ".txt":
                        case ".csv":
                           Read(new BufferedReader(new FileReader(file)));
                            break;
                        case ".xls":
                        case ".xlsx":
                            wb = WorkbookFactory.create(new FileInputStream(file));
                           Read();
                            break;
                }
        }
    
        private static void Read(BufferedReader br) throws IOException, ParseException {
            String line;	    
            String[] tmp;
                switch (extensionName) {
                    case ".txt":			
                        while ((line = br.readLine()) != null) { 
                            tmp = line.split("[\\s]+ ");
                            UserList.add(new User(tmp[0].trim(), tmp[1].trim()));
//                            System.out.println(tmp[0].trim()+" "+tmp[1].trim());
                        }
                        break;
                    case ".csv": 
                        while ((line = br.readLine()) != null) { 
                            tmp = line.split(",");
                            UserList.add(new User(tmp[0].trim(), tmp[1].trim()));
//                            System.out.println(tmp[0].trim()+" "+tmp[1].trim());
                        }     
                        break;
                }
        }
    
        private static void Read() throws ParseException {
            Sheet sheet = wb.getSheetAt(0);
                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);
                    if(sheet.getRow(i).getCell(0).getCellType() == 1 && "NULL".equals(sheet.getRow(i).getCell(0).getStringCellValue())){}
                    else {
                        String [] tmp = new String[row.getPhysicalNumberOfCells()];
                            for(int j = 0; j < row.getPhysicalNumberOfCells(); j ++) {
                                Cell cell = row.getCell(j);
//                            System.out.println(cell.getCellType());

                                if(cell.getCellType() == 0) {
                                    if(DateUtil.isCellDateFormatted(cell)) { 
                                        tmp[j] = new SimpleDateFormat("yyyy/MM/dd HH:mm").format(cell.getDateCellValue());
                                    } else {
                                        cell.setCellType(1);
                                        tmp[j] = cell.getStringCellValue();
                                    }
                                }
                            }
//                    System.out.println(tmp[0].trim()+" "+tmp[3].trim());
                        UserList.add(new User(tmp[0].trim(), tmp[3].trim()));
                    }
                }
        }

        public static List<File> getFileList() {
            return FileList;
        }

        public static List<User> getUserList() {
            return UserList;
        }
        
}
