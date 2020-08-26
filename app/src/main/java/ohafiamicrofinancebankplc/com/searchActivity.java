package ohafiamicrofinancebankplc.com;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;

import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cursoradapter.widget.SimpleCursorAdapter;



public class searchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    SimpleCursorAdapter adapter;
    ListView listView =null;
    AlertDialog.Builder msg;
    TextView ac_no;
    final  DatabaseHelper OHAFIAMFBDB= new DatabaseHelper(this);
    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 1) {
            Cursor cursor = OHAFIAMFBDB.search(s);


            int[] toViews = {R.id.searchname, R.id.searchAcno, R.id.searchamount};


            msg = new AlertDialog.Builder(this);
            try {
                adapter = new SimpleCursorAdapter(this,
                        R.layout.activity_searcho, cursor, new String[]{"AC_NAME", "AC_NO", "AMOUNT"}, toViews, 0);
                listView.setAdapter(adapter);
            } catch (Exception e) {
                msg.setCancelable(true);
                msg.setMessage(e.getMessage());
                msg.show();
            }
        }//end of if(s.length>1)
        return false;

    }
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView simpleSearchView = (SearchView) findViewById(R.id.simpleSearchView);
        simpleSearchView.setOnQueryTextListener(this);

        listView = (ListView) findViewById(R.id.listView);
        Button deposit_btn = (Button) findViewById(R.id.searchbtndeposit);



    }



    @Override
    public boolean onQueryTextSubmit (String s){
        return false;
    }

    public void post (View v){
         AlertDialog.Builder Build = new AlertDialog.Builder(this);
        //fetching the view parent
        View parentRow = (View) v.getParent();
        //assigning the parent to a view
        ListView listView = (ListView) parentRow.getParent();
// getting the clicked button position
        final int position = listView.getPositionForView(parentRow);

        Cursor items=(Cursor) adapter.getItem(position);

        String acno= items.getString(1).trim();
       // String amt= items.getString(3).trim();
        TextView amt=(TextView) parentRow.findViewById(R.id.searchamount);
        TextView changename=(TextView) parentRow.findViewById(R.id.searchname);
        TextView changeacno=(TextView) parentRow.findViewById(R.id.searchAcno);





        boolean t=false;
        try {

             t = OHAFIAMFBDB.updatedatafull(acno.trim(), amt.getText().toString().trim(),changename.getText().toString(),changeacno.getText().toString());
            //t = OHAFIAMFBDB.Zerodata(amt);
        }
        catch(Exception e){Build.setCancelable(true);
            Build.setTitle("ERROR Reported");
            Build.setMessage(e.getMessage());}
        if (t) {
            Build.setCancelable(true);
            Build.setTitle("Posted");
            String cbal="0";
            Cursor rs=OHAFIAMFBDB.getCashbal();
            if(rs.moveToFirst()) {
                cbal = rs.getString(0);
            }
            Build.setMessage("Transaction Successful \n Cash Bal:"+cbal );
            Build.show();
            amt.setText(amt.getText());


        } else {
            Build.setCancelable(true);
            Build.setTitle("ERROR Reported");
            //Build.setMessage("Transaction not posted");


            Build.show();
        }


        //Toast.makeText(searchActivity.this,"Your acno= "+acno+" and amount= "+amt,Toast.LENGTH_LONG).show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent prevpage= new Intent(searchActivity.this,optionActivity.class);
        startActivity(prevpage);
        this.finish();
    }
}
