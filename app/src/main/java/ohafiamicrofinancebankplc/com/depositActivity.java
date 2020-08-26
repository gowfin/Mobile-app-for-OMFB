package ohafiamicrofinancebankplc.com;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;

public class depositActivity extends AppCompatActivity {
 DatabaseHelper OHAFIAMFBDB = new DatabaseHelper(this);
    TextView ac_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        Button deposit_btn= (Button) findViewById(R.id.btndeposit);
        final  AlertDialog.Builder Build = new AlertDialog.Builder(this);


        deposit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ac_no=(TextView) findViewById(R.id.acno);
                TextView name=(TextView) findViewById(R.id.name);
                TextView amount=(TextView) findViewById(R.id.amount);
                boolean t=OHAFIAMFBDB.updatedata(ac_no.getText().toString(),amount.getText().toString());
                if(t) {
                    Build.setCancelable(true);
                    Build.setTitle("Posted");
                    String cbal="0";
                    Cursor rs=OHAFIAMFBDB.getCashbal();
                    if(rs.moveToFirst()) {
                        cbal = rs.getString(0);
                    }
                    Build.setMessage("Transaction Successful \n Cash Bal:"+cbal );

                    Build.show();
                    name.setText("");
                    amount.setText("0");
                     ac_no.setText("200301-");
                     }
                      else{
                    Build.setCancelable(true);
                    Build.setTitle("ERROR Reported");
                    Build.setMessage("Transaction not posted");

                      Build.show();}
            }
        });
}
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent prevpage= new Intent(depositActivity.this,optionActivity.class);
        startActivity(prevpage);
        finish();
    }
    public void getname( View v){

        View parentRow = (View) v.getParent();
       TextView accnametextview= parentRow.findViewById(R.id.name);

        TextView accnotextview= parentRow.findViewById(R.id.acno);

        Cursor rs=OHAFIAMFBDB.getAccname(accnotextview.getText().toString());

        if(rs.moveToFirst()) {
            accnametextview.setText(rs.getString(2).toUpperCase());
        }
        else{
            Toast.makeText(this,"Wrong or invalid Account Number",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder msg= new AlertDialog.Builder(this);
            msg.setMessage("Wrong or invalid Account Number");
            msg.show();
        }
    }
}
