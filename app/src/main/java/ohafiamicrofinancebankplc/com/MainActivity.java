package ohafiamicrofinancebankplc.com;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.Telephony;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    String user;
    String dbname;
    String passwd="";
    int count=0;
    DatabaseHelper OhafiaMFBDB;
    Global glob= new Global();
    sqlposting sqlpost;
    Connection conn;
    EditText username;
    sendSMS send=new sendSMS();
    String[] glnoA;
    String[] acnoA;
    String[] amountA;
    String[] DepositorA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        setContentView(R.layout.activity_main);
        OhafiaMFBDB= new DatabaseHelper(this);
        OhafiaMFBDB.insertuser("mobile","pass");
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView view = (TextView) findViewById(R.id.textView);
                 username=(EditText) findViewById(R.id.username) ;
                Cursor dbuser=OhafiaMFBDB.getDbUser(username.getText().toString().toLowerCase() .trim());
                if(dbuser.moveToFirst())
                {
                    dbname=dbuser.getString(0);
                }

                if(username.getText().toString().trim().toLowerCase().compareToIgnoreCase(dbname.toString().toLowerCase())==0 ) {
                    view.setText("Enter password for " + username.getText());
                    user=username.getText().toString();
                    username.setText("");
                    //convert textview to encryted password
                    username.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    count=1;
                }
                else if(count==1){
                    dbuser=OhafiaMFBDB.getDbUserpw(dbname);
                    dbuser.moveToFirst();

                    if(username.getText().toString() .trim().compareToIgnoreCase(dbuser.getString(1))==0 ) {
                        Toast.makeText(MainActivity.this, "Password Correct", Toast.LENGTH_SHORT).show();

                        Intent nextpage= new Intent(MainActivity.this,optionActivity.class);
                        nextpage.putExtra("trxname",dbname);
                        count=0;


                        startActivity(nextpage);
                        username.setInputType(InputType.TYPE_CLASS_TEXT );
                        view.setText("Enter user name here");
                        username.setText("");
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Wrong Password entered,please retry", Toast.LENGTH_LONG).show();
                        count=1;
                    }
                }
                else{
                    view.setText("Invalid User name " + username.getText());
                }
                // Toast.makeText(MainActivity.this, "You click me", Toast.LENGTH_LONG).show();
            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
              case R.id.logout:

                this.finish();
                return true;
              case R.id.report:
                // do your code
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static String getcurrentDateAndTime(){

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String formattedDate = simpleDateFormat.format(c);
        return formattedDate;
    }
    @Override

    protected void onStop(){
        finish();

        super.onStop();

    }
}

