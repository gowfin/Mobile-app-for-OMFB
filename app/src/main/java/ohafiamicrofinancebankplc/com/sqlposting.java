package ohafiamicrofinancebankplc.com;



import java.math.BigDecimal;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class sqlposting {


    public void postsqlserver(Connection conn,String fsacno,String amount,String name,String username){
        boolean posted;

        String dtt= getcurrentDateAndTime();



        String tranctno="";
        if(dtt.length()<10){dtt=dtt.substring(0,5)+"0"+dtt.substring(5);};// to take care of 01/03/2018
        int trannoCnt=1;
        String StrngtrannoCnt="01";
        String acno="",glno="";
        int n=0; String GLcode,ID,Product;
        double   depBal=0.0;
PreparedStatement pstmt;
        try {

            conn.setAutoCommit(false);//for transaction posting

           // for (int ii = 0; ii < lastdepcount; ii++) {

//generating transaction number
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Random rand = new Random(timestamp.getTime());
                int Rnum = rand.nextInt(999999);


                tranctno = dtt.substring(2,4) + dtt.substring(5,7) + dtt.substring(8) + Rnum + "MASD";

                ID = acno;
                Product = fsacno;


                String StrQuery = "select GLCode from Product where productDesc like'%" + Product + "%'";
                Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

                ResultSet current = stmt.executeQuery(StrQuery);

                current.next();

                GLcode = current.getString("GLCode");

                if (Double.parseDouble(amount) == 0.00) {

                } // do not post account with Zero deposit
                  double amnt = Double.parseDouble(amount);
            String query2 = "select * from Deposit  where accountid=RunningBal+" + amnt + " where AccountID='" + ID + "'";
               String query3 = "update Deposit  set RunningBal=RunningBal+" + amnt + " where AccountID='" + ID + "'";
               String  query4 = ("insert into transactn (AccountID,tranid,Amount,DebitGL,CreditGL,Runningbal,ValueDate,DateEffective,CustNO,StmtRef,BranchID,ChequeNbr,CreatedBy,transactionNbr)"
                        + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                conn.setAutoCommit(false);//for transaction posting



                pstmt = conn.prepareStatement(query3);
                pstmt.executeUpdate();

              depBal = depBal + Double.parseDouble(amount);//amount is for deposit while Amount for repayment
                pstmt = conn.prepareStatement(query4);
                pstmt.setString(1, ID);
                pstmt.setString(2, "005");
                pstmt.setString(3, amount);
                pstmt.setString(4, "tellerControlGl"); //cashier drawerid
                pstmt.setString(5, GLcode + "-" + "002");
//int curbal=Integer.parseInt(bal)-Integer.parseInt(amount);
                pstmt.setString(6, depBal + "");
                pstmt.setString(7, dtt+" 00:00:00.000");
                pstmt.setString(8, dtt+" 00:00:00.000");
                pstmt.setString(9, fsacno);
                pstmt.setString(10, "Mobile Dep by cash");
                pstmt.setString(11, "002"); //BranchID
                pstmt.setString(12, "Bulk Trans");
                pstmt.setString(13, username);
                pstmt.setString(14, tranctno);
                pstmt.executeUpdate();

                //JOptionPane.showMessageDialog(this,ii);

           // }
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
