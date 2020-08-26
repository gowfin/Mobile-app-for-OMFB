package ohafiamicrofinancebankplc.com;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AllClientActivity extends AppCompatActivity {
    DatabaseHelper OHAFIAMFBDB;
    String totalposted;
    String alltrans;
    String transcount;
    String[] clients = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_all_client);
        OHAFIAMFBDB = new DatabaseHelper(this);
        Cursor total = OHAFIAMFBDB.getCashbal();
        if (total.moveToFirst()) {
            totalposted = total.getString(0);
            alltrans = totalposted;
        }
        total.close();
        transcount = OHAFIAMFBDB.gettransCountAll("0");
        Cursor All = OHAFIAMFBDB.getAlltrans("0");
        if (All.moveToFirst()) {
            clients = new String[All.getCount() + 1]; //add 1 to accommodate for the total column
            clients[0] = "Total transactions posted****" + totalposted + "**(" + transcount + ")**";
            for (int i = 0; i < All.getCount(); i++) {
                clients[i + 1] = All.getString(2) + "(" + All.getString(1) + ")  " + "=N=" + All.getString(3);
                All.moveToNext();
            } //end of for stmt
        }
        ; //end of if stmt
        All.close();

        ListView clientlist = (ListView) findViewById(R.id.listViewall);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, clients);
        clientlist.setAdapter(adapter);

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent prevpage = new Intent(AllClientActivity.this, optionActivity.class);
        startActivity(prevpage);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuviewtrans, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewamount:
                final AlertDialog.Builder Me = new AlertDialog.Builder(this);
                Me.setTitle("Enter an amount.");
                final EditText inputx = new EditText(this);
                inputx.setInputType(InputType.TYPE_CLASS_TEXT);
                Me.setView(inputx);
                Me.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cursor total = OHAFIAMFBDB.getAmountBal(inputx.getText().toString().trim());
                        if (total.moveToFirst()) {
                            totalposted = total.getString(0);
                            alltrans = totalposted;
                        }
                        total.close();
                        transcount = OHAFIAMFBDB.gettransCountOne(inputx.getText().toString().trim());
                        Cursor All = OHAFIAMFBDB.getSpecifictrans(inputx.getText().toString().trim());
                        if (All.moveToFirst()) {
                            clients = new String[All.getCount() + 1]; //add 1 to accommodate for the total column
                            clients[0] = "Total transactions posted****" + totalposted + "**(" + transcount + ")**";
                            for (int i = 0; i < All.getCount(); i++) {
                                clients[i + 1] = All.getString(2) + "(" + All.getString(1) + ")  " + "=N=" + All.getString(3);
                                All.moveToNext();
                            } //end of for stmt
                        }
                        //end of if stmt
                        All.close();

                        ListView clientlist = (ListView) findViewById(R.id.listViewall);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AllClientActivity.this, R.layout.support_simple_spinner_dropdown_item, clients);
                        clientlist.setAdapter(adapter);


                    }

                });
                Me.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                Me.show();
                return true;
            default:
                return true;
        }
    }
}
