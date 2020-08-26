package ohafiamicrofinancebankplc.com;

import java.sql.*;

public class sendSMS {




        public void getAcc(String trxtype,String acc,String gl, double amount,String text,String sesdate,Connection conn) {





            String SQL = "select SUM(amount) bal,RTRIM(LEFT(GETDATE(),22)) dt from memtrans " +
                    " where ac_no='" + acc + "' and gl_no='" + gl + "'";
            String SQL2 = "SELECT  Mobile FROM SMSSET  WHERE Branch = 100 AND GL_No = '" + gl + "' AND AC_No ='" + acc + "' AND DRCRALERT = 1 ";
            String DRCR = "CR", time, mobile, msg;
            String TrxDRCR = "DR";
            if (trxtype.equalsIgnoreCase("Credit")) {
                TrxDRCR = "CR";
            }
            Double Bal;

            if (conn != null) {

                try {


                Statement stmt = conn.createStatement();
                ResultSet current = stmt.executeQuery(SQL);
                /* SMS Settings */
                current.next();

                Bal = current.getDouble("bal");
                time = current.getString("dt");
                if (current.getDouble("bal") <= 0) {
                    DRCR = "CR";
                } else {
                    DRCR = "DR";
                }

                ResultSet current2 = stmt.executeQuery(SQL2);
                if (current2.next()) {
                    mobile = current2.getString("mobile").trim();
                    msg = "OMFB " + trxtype + " Alert A/C No.: " + gl + ".***" + acc.substring(3) + "  Amt: " + amount + " " + TrxDRCR + " Details:" + text + " " + time + " Avail Bal: " + Math.abs(Bal) + " " + DRCR;


                    //msg = msg.substring(0,-160);
                    //JOptionPane.showMessageDialog(null, msg+" and "+sesdate);
                    String SQL3 = "INSERT INTO SMSHIST (BRANCH,Mobile,SCODE,RMessage,RDATE, MSTATUS,SES_DATE,SMESSAGE,SDATE,GL_FR,AC_FR,GL_TO,AC_TO,Cycle,Amount,SentReply,SysInfo) " +
                            " VALUES (100,'" + mobile + "','DRCR','','" + sesdate + "','','" + sesdate + "','" + msg + "','" + sesdate + "','" + gl + "','" + acc + "','','',0,0,0,'')";
                    PreparedStatement pstmt1 = conn.prepareStatement(SQL3);

                    pstmt1.executeUpdate();
                   conn.commit();  //Save insert to smshist
                }

            } catch (SQLException e) {
                System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX" + e.getMessage() + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
            }


        }// end of if (conn!=null)
    }// end of public void smsSend method



} //end of class
