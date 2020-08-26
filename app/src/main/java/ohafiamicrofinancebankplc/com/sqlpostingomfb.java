package ohafiamicrofinancebankplc.com;



import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class sqlpostingomfb {


    public void postsqlserver(Connection conn,String fsacno,String amount,String name,String username){
        boolean posted;

        String dtt= getcurrentDateAndTime();
        //Check for lengthy ac_name And reduce
        if(name.length()>35){
            name=name.substring(0,35);


        }



        String tranctno="";
        if(dtt.length()<10){dtt=dtt.substring(0,5)+"0"+dtt.substring(5);};// to take care of 01/03/2018
        int trannoCnt=1;
        String StrngtrannoCnt="01";
        String acno="",glno="";
        int n=0;

        try{
            //this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));




                //getting tranctn number

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Random rand = new Random(timestamp.getTime());
                if(fsacno.contains("-")){ //to check for "-" and no "-"
                    glno=fsacno.substring(0,6);
                    acno=fsacno.substring(7);
                }else{
                    glno=fsacno.toString().substring(0,6);
                    acno=fsacno.substring(6);

                }

                if(!amount.equalsIgnoreCase("0.0")){ // amount if
                    //JOptionPane.showMessageDialog(rootPane, amount+glno);
                    int Rnum=rand.nextInt(999999);
                    tranctno=dtt.substring(2, 4)+dtt.substring(5, 7)+dtt.substring(8)+Rnum+StrngtrannoCnt+"DSDA";

                    String[] names=username.trim().split("\\s+");
                    String Lastname=names[0].toUpperCase();
                    String Firstname="";
                    String Username="";
                    if(names.length>1){
                        Firstname=names[1].toUpperCase();
                    }
                    //if(names.length>2){
                    // Middlename=names[2].toUpperCase();
                    //}
                    Username=Lastname+Firstname;
                    if(Username.length()>15){Username=Username.substring(0,15);}
                    // JOptionPane.showMessageDialog(rootPane,  Username);
                    String sql3="insert into memtrans(branch,gl_no,ac_no,trx_no,ses_date,batch_date,amount,err_flag,fin_repo,fx_amount) values(100,'"+glno+"','"+acno+"','"+ tranctno+"','"+dtt+" 00:00:00.000','"+dtt+" 00:00:00.000','"+ BigDecimal.valueOf(Double.parseDouble(amount.replaceAll(",", ""))).negate()+"','','1','0.00')";
                    String sql3b="insert into memtrans(branch,gl_no,ac_no,trx_no,ses_date,batch_date,amount,err_flag,fin_repo,fx_amount) values(100,'101202','3','"+ tranctno+"','"+dtt+" 00:00:00.000','"+dtt+" 00:00:00.000','"+BigDecimal.valueOf(Double.parseDouble(amount.replaceAll(",", "")))+"','','1','0.00')";

                    String sql4="insert into trx_text(branch,trx_no,cheque,teller,trx_code,text,username ,ses_date,fx_code,SUPER_USER) values(100,'"+ tranctno+"','','','FIB','"+"CASH DEP BY "+name.toUpperCase()+"','"+Username+"','"+dtt+" 00:00:00.000','0','NULL')";


                    trannoCnt=trannoCnt+1;
                    if(trannoCnt>99){trannoCnt=1;}
                    if(trannoCnt<10){StrngtrannoCnt="0"+trannoCnt;}
                    else{
                        StrngtrannoCnt=trannoCnt+"";
                    }


                    PreparedStatement pstmt1=conn.prepareStatement(sql3);
                    PreparedStatement pstmt1b=conn.prepareStatement(sql3b);
                    PreparedStatement pstmt2=conn.prepareStatement(sql4);




                    pstmt1.executeUpdate();
                    pstmt1b.executeUpdate();
                    pstmt2.executeUpdate();
                }// end of amount if







            //indicated successful transaction
            posted=true;


        }
        catch(Exception e){

            //JOptionPane.showMessageDialog(rootPane, e.getCause()+" and "+e.getMessage()+"Customer name"+jTablecust.getModel().getValueAt(salcount, 1).toString()+" at S/N "+(n+2) + " of Salary Count= "+(salcount+1)+"\n A/C="+glno+"-"+acno+" Amount="+amount);posted=false;
            }
        //this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));


    }
    public static String getcurrentDateAndTime(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }
}
