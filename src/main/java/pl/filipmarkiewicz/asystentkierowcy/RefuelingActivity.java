package pl.filipmarkiewicz.asystentkierowcy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.ListIterator;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

public class RefuelingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refueling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("Tankowania");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**String[] refuelings = {
                "10 marca 2018 | 20,56 l | 90,89 zł",
                "26 lutego 2018 | 40,34 l | 180,12 zł",
                "10 lutego 2018 | 45,78 l | 201,23 zł ",
                "23 stycznia 2018 | 43,56 l | 193,56 zł",
                "2 stycznia 2018 | 35,56 l | 143,23 zł",
                "20 grudnia 2017 | 39,12 l | 176,49zł"
        };**/

        //Refueling first = new Refueling(1, 40.89, 188.90, new GregorianCalendar(2018, 2, 24, 12, 34, 56));
        //Refueling second = new Refueling(2, 30.21, 140.76, new GregorianCalendar(2018, 2, 29, 20, 21, 45));

        //fdm.add(first.toFileDatabaseRow());
        //fdm.add(second.toFileDatabaseRow());

        //fdm.write();

        loadData();
    }

    public void addRefueling(View view) {
        Intent manageIntent = new Intent(this, ManageRefuelingActivity.class);
        manageIntent.putExtra("TYPE", ManageRefuelingActivity.TYPE_ADD);
        manageIntent.putExtra("ID", 0);
        manageIntent.putExtra("AMOUNT", 0);
        manageIntent.putExtra("PRICE", 0);
        manageIntent.putExtra("DATE", "");

        startActivityForResult(manageIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestID, int resultCode, Intent intent) {
        if (resultCode == ManageRefuelingActivity.RESULT_OK && requestID == 1) {
            loadData();
        } else if (resultCode == ManageRefuelingActivity.RESULT_FAILED && requestID == 1) {
            Toast.makeText(getApplicationContext(), "Wystąpił problem.", Toast.LENGTH_LONG).show();
        }
    }

    private void loadData() {
        FileDatabaseManager fdm = new FileDatabaseManager(Config.REFUELINGS_FILE, getApplicationContext());

        try {
            LinkedList<FileDatabaseRow> rows = fdm.read();
            LinkedList<String> list = new LinkedList<String>();
            String current;

            ListIterator<FileDatabaseRow> it = rows.listIterator(rows.size());

            while (it.hasPrevious()) {
                Refueling refueling = Refueling.createFromFileDatabaseRow(it.previous());

                list.add(refueling.toString());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

            final RefuelingActivity that = this;

            ListView simpleList = (ListView) findViewById(R.id.refuelingList);
            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(that, ((TextView) view).getText(),
                            Toast.LENGTH_LONG).show();
                }
            });
            simpleList.setAdapter(adapter);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Wystąpił problem podczas odczytu pliku z danymi.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
