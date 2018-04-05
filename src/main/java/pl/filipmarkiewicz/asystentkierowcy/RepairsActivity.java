package pl.filipmarkiewicz.asystentkierowcy;

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

import java.util.LinkedList;
import java.util.ListIterator;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;
import pl.filipmarkiewicz.filedatabase.FileDatabaseRow;

public class RepairsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repairs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("Naprawy");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
    }

    public void addRepair(View view) {
        Intent manageIntent = new Intent(this, ManageRepairActivity.class);
        manageIntent.putExtra("TYPE", ManageRepairActivity.TYPE_ADD);
        manageIntent.putExtra("ID", 0);
        manageIntent.putExtra("NAME", "");
        manageIntent.putExtra("EXPECTED_PRICE", 0);

        startActivityForResult(manageIntent, 1);
    }

    public void loadData() {
        FileDatabaseManager fdm = new FileDatabaseManager(Config.REPAIRS_FILE, getApplicationContext());

        try {
            LinkedList<FileDatabaseRow> rows = fdm.read();
            LinkedList<String> plannedList = new LinkedList<String>();
            LinkedList<String> doneList = new LinkedList<String>();

            ListIterator<FileDatabaseRow> it = rows.listIterator(rows.size());

            while (it.hasPrevious()) {
                Repair repair = Repair.createFromFileDatabaseRow(it.previous());

                if (repair.isDone()) {
                    doneList.add(repair.toString());
                } else {
                    plannedList.add(repair.toString());
                }
            }

            ArrayAdapter<String> plannedAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, plannedList);

            //final RepairsActivity that = this;

            ListView plannedListView = (ListView) findViewById(R.id.plannedRepairsList);
            plannedListView.setAdapter(plannedAdapter);

            ListView doneListView = (ListView) findViewById(R.id.doneRepairsList);
            ArrayAdapter<String> doneAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, doneList);

            doneListView.setAdapter(doneAdapter);
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Wystąpił problem podczas odczytu pliku z danymi.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestID, int resultCode, Intent intent) {
        if (resultCode == ManageRepairActivity.RESULT_OK && requestID == 1) {
            loadData();
        } else if (resultCode == ManageRepairActivity.RESULT_FAILED && requestID == 1) {
            Toast.makeText(getApplicationContext(), "Wystąpił problem.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
