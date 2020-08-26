package ohafiamicrofinancebankplc.com;

import android.app.AlertDialog;
import android.content.Context;
import android.os.StrictMode;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class Global {
    String Classes="net.sourceforge.jtds.jdbc.Driver";
    public Connection getConnect(String ip, String dbn, String ui,String pw ){
        //Declare path for the config file
        String path="config.auc",aline,Server=ip,Dbname=dbn,user=ui,pwd=pw;


/*
        try{
            File file=new File(path);
            FileReader file_to_read=new FileReader(file);
            BufferedReader bf=new BufferedReader(file_to_read);

            aline=bf.readLine();
            // while((aline=bf.readLine())!=null){

            //JOptionPane.showMessageDialog(null,aline);

            //}
            int count =0,lasti=0;
            bf.close();
            for(int i=0; i<=aline.length();i++){
                if( aline.substring(i, i+1).equalsIgnoreCase(";") && count==0){
                    Server=aline.substring(0, i);
                    count=1;
                    lasti=i+1;//to omit ";"
                }
                else if( aline.substring(i, i+1).equalsIgnoreCase(";") && count==1){
                    Dbname=aline.substring(lasti, i);
                    count=2;
                    lasti=i+1;//to omit ";"

                }
                else if( aline.substring(i, i+1).equalsIgnoreCase(";") && count==2){
                    user=aline.substring(lasti, i);
                    count=3;
                    lasti=i+1;//to omit ";"

                }
                if( count==3){// count ==3 means only pwd left
                    pwd=aline.substring(lasti);
                    count=0;
                    aline="";//to remove the selected parameter

                }
            }
            // JOptionPane.showMessageDialog(null,aline+": "+Server+Dbname+user+pwd);

        }catch(Exception ex){
            //JOptionPane.showMessageDialog(null,ex.getMessage());

        } */
        String url =  "jdbc:jtds:sqlserver://"+Server+":1433/"+Dbname;

        Connection conn=null;

        try
        {
            StrictMode.ThreadPolicy policy  = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName(Classes);
            conn = DriverManager.getConnection(url, user, pwd);


        }catch(Exception e){
           System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"+e.getMessage()+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        return conn;
    }


}
