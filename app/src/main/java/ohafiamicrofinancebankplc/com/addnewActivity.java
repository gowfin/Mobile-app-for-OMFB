package ohafiamicrofinancebankplc.com;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class addnewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        Button add_new= (Button) findViewById(R.id.btndaddnew);

       final DatabaseHelper  OhafiaMFBDB= new DatabaseHelper(this);
       final  AlertDialog.Builder Build = new AlertDialog.Builder(this);
        add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 TextView ac_no= (TextView)  findViewById(R.id.acno);
                 TextView amount= (TextView)  findViewById(R.id.amount);
                 TextView name= (TextView)  findViewById(R.id.name);

               boolean t=OhafiaMFBDB.insertdata(ac_no.getText().toString().trim().replaceAll(" ", ""),name.getText().toString(),amount.getText().toString(),amount.getText().toString());

           if(t) {
               Build.setCancelable(true);
               Build.setTitle("Posted");
               Build.setMessage("Customer Added Successfully");
               Build.show();
               name.setText("");
              amount.setText("");
               ac_no.setText("200301");
                 }
                 else{ Build.setCancelable(true);
              Build.setTitle("ERROR Reported");
              Build.setMessage("Customer not added");
               Build.show();}
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent prevpage= new Intent(addnewActivity.this,optionActivity.class);
        startActivity(prevpage);
        finish();
    }
}
