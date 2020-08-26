package ohafiamicrofinancebankplc.com;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="OhafiaMFBDB.db";
    public static final String TABLE_NAME="memtrans";
    public static final String TABLE_NAME2="users";
    public static final String TABLE_NAME3="ConConfig";
    public static final String Col11="ID";
    public static final String Col22="PW";
    public static final String Col1="AC_NO";
    public static final String Col2="AC_NAME";
    public static final String Col3="AMOUNT";
    public static final String Col4="REMARK";
    public static final String Col5="BALANCE";
    public static final String configCol1="ip";
    public static final String configCol2="db";
    public static final String configCol3="user";
    public static final String configCol4="pw";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME+ "(_id integer primary key autoincrement,AC_NO TEXT Unique,AC_NAME TEXT,AMOUNT REAL,REMARK TEXT, BALANCE TEXT )");
        db.execSQL("create table "+ TABLE_NAME2+ "(ID TEXT primary key,PW TEXT)");
        db.execSQL("create table "+ TABLE_NAME3+ "(IP TEXT primary key DEFAULT '192.168.101.2',DB TEXT DEFAULT 'OHAFIAMFBDB',User TEXT DEFAULT 'sa' ,PW TEXT DEFAULT 'Admin123Server')");
        db.execSQL("insert into "+TABLE_NAME3+ " values('192.168.43.233','Bankdatabase','sa','Admin123Server')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table if exists "+TABLE_NAME);
        db.execSQL("Drop table if exists "+TABLE_NAME2);
        db.execSQL("Drop table if exists "+TABLE_NAME3);
        onCreate(db);
    }
    public boolean insertdata(String acno, String acname, String Amount, String Balance){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Col1,acno);
        cv.put(Col2,acname);
        cv.put(Col3, Amount);
        cv.put( Col4,Balance);

        long result=db.insert(TABLE_NAME,null,cv);
        if(result==-1)
            return false;

        else
            return true;
    }
    public boolean updatedata(String accno, String Amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
       // cv.put(Col1,acno);
       // cv.put(Col2,acname);
        cv.put(Col3, Amount);
       // cv.put( Col4,Balance);

        long result=db.update(TABLE_NAME,cv,"AC_NO=?",new String[] {accno});
        if(result==-1)
            return false;

        else
            return true;
    }
    public boolean updatedb(String ip, String dbn,String user, String pw){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
         cv.put(configCol1,ip);
         cv.put(configCol2,dbn);
        cv.put(configCol3, user);
         cv.put(configCol4,pw);

        long result=db.update(TABLE_NAME3,cv,null,null);
        if(result==-1)
            return false;

        else
            return true;
    }
    public String[] getconfig(){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from Conconfig",null);
        String ip="0",dbuser="0",ui="0",pw="0";
        String[] creden;
        if(rs.moveToFirst()){
           ip=rs.getString(0);
           dbuser=rs.getString(1);
           ui=rs.getString(2);
           pw=rs.getString(3);
        }
        creden= new String[]{ip,dbuser,ui,pw};
        return creden;                        }
    public boolean updatedatafull(String accno, String Amount,String changedacname,String changedAccno){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
         cv.put(Col1,changedAccno);
         cv.put(Col2,changedacname);
        cv.put(Col3, Amount);
        // cv.put( Col4,Balance);

        long result=db.update(TABLE_NAME,cv,"AC_NO=?",new String[] {accno});
        if(result==-1)
            return false;

        else
            return true;
    }

    public boolean Zerodata( String Amount){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        Amount="0";
        // cv.put(Col1,acno);
        // cv.put(Col2,acname);
        cv.put(Col3, Amount);
        // cv.put( Col4,Balance);

        long result=db.update(TABLE_NAME,cv,null,null);
        if(result==-1)
            return false;

        else
            return true;
    }

    public boolean insertuser(String ID, String passwd){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put(Col11,ID);
        cv.put(Col22,passwd);


        long result=db.insert(TABLE_NAME2,null,cv);
        if(result==-1)
            return false;

        else
            return true;
    }
    public boolean setDbUserpw(String user, String curpw){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        // cv.put(Col1,acno);
        // cv.put(Col2,acname);
        cv.put("PW", curpw);
        // cv.put( Col4,Balance);

        long result=db.update(TABLE_NAME2,cv,"ID=?",new String[] {user});
        if(result==-1)
            return false;

        else
            return true;
    }
    public boolean setDbUserID(String user, String curID){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put("ID", curID);

       //System.out.println("XXXXXXXXX"+curID+"XXX"+user+" table="+TABLE_NAME2);
        long result=db.update(TABLE_NAME2,cv,"ID=?",new String[] {user});
        if(result==-1)
            return false;

        else
            return true;
    }

    public Cursor getDbUserpw(String user){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from users where id='"+user+"'",null);
        return rs;                        }
    public Cursor getAccname(String acno){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from memtrans where ac_no='"+acno+"'",null);
        return rs;                        }
    public Cursor getAlltrans(String amount){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from memtrans where amount<>'"+amount+"'",null);
        return rs;                        }
    public Cursor getSpecifictrans(String amount){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from memtrans where amount=?",new String[]{amount});
        return rs;                        }
    public String gettransCountAll(String amount){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select count(*) from memtrans where amount<>?",new String[]{amount});
        if(rs.moveToFirst()){
            amount=rs.getString(0);
        }
        return amount;                        }

    public String gettransCountOne(String amount){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select count("+amount+") from memtrans where amount=?",new String[]{amount});
        if(rs.moveToFirst()){
            amount=rs.getString(0);
        }
        return amount;                        }
    public Cursor getDbUser(String user){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from users where id='"+user+"'",null);
        return rs;                        }
    public Cursor getAllacc(){
        SQLiteDatabase db=this.getWritableDatabase();

        Cursor rs=  db.rawQuery("select * from memtrans where Amount<>'0'",null);
        return rs;                        }
    public Cursor getCashbal() {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor rs = db.rawQuery("select sum(Amount) from memtrans where Amount <> ?", new String[] { "0" } );
        Cursor rs = db.rawQuery("select sum(Amount) from memtrans",null );

        return rs;
    }
    public Cursor getAmountBal(String Amount) {
        SQLiteDatabase db = this.getWritableDatabase();

        //Cursor rs = db.rawQuery("select sum(Amount) from memtrans where Amount <> ?", new String[] { "0" } );
        Cursor rs = db.rawQuery("select sum(Amount) from memtrans where Amount=?",new String[]{Amount} );

        return rs;
    }
    public Cursor search(String keyword) {
        //String[] contact = new String[200];
        Cursor cursor=null;
        try {
            SQLiteDatabase db=this.getWritableDatabase();
           // int i=0;
             cursor = db.rawQuery("select *,AC_NAME+' ('+AC_NO+')'+AMOUNT AS NOW from " + TABLE_NAME + " where " + Col2 + " like ?", new String[] { "%" + keyword + "%" });

        } catch (Exception e) {
           cursor = null;
        }
        return cursor;
    }
}

