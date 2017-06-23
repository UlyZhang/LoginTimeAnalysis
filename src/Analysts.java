/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ulysses.LTA;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author UlyssesZhang
 */
public class Analysts {
    private static List<User> UserList;
    private static double differencetime;
    private static double totaltime;
    
    private static void sort() {
        System.out.println("Sort User ID...");
        
        Collections.sort(UserList, new Comparator<User>(){
            @Override
            public int compare(User lhs, User rhs) {  
                Date t1 = null,t2 = null;
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                    t1 = sdf.parse(lhs.getLogintime());
                    t2 = sdf.parse(rhs.getLogintime());
                } catch (ParseException ex) {
                    Logger.getLogger(Analysts.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (t2.before(t1)) {  return 1;  }  
                return -1;
            }
        });
        
        Collections.sort(UserList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result = Integer.parseInt(o1.getID()) - Integer.parseInt(o2.getID());
                return result;
            }
        });
        
        for (int i=0;i<UserList.size();i++) { System.out.println(UserList.get(i).getID() + " " + UserList.get(i).getLogintime()); }
    }
    
    private static double getDifferenceTime(int i1, int i2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date t1 = sdf.parse(UserList.get(i1).getLogintime());
        Date t2 = sdf.parse(UserList.get(i2).getLogintime());
        Long ut1 = t1.getTime();
        Long ut2 = t2.getTime();
        if(ut2 - ut1 < (20*60*1000)) {
            differencetime = ((Math.abs(ut2 - ut1)/1000)/60);
//            System.out.println("differencetime : "+differencetime);
            return differencetime;
        } else { 
//            System.out.println("differencetime >20");
            differencetime = 1;
            return differencetime; 
        }
    }
    
    public static double getTotalTime(List<User> Users) throws ParseException {
        totaltime = 1;
        differencetime = 0;
        UserList = new ArrayList<>();
        UserList = Users;
        sort();
        for (int i = 0; i < UserList.size()-1; i++) { 
           if (UserList.get(i).getID().equals(UserList.get(i + 1).getID())) {
               totaltime += (getDifferenceTime(i, i+1));
           } else { totaltime += 1; }
        }
        
        totaltime = new BigDecimal(totaltime/60).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("Totaltime : "+totaltime + " hours\n");
        return totaltime;
    }
}
