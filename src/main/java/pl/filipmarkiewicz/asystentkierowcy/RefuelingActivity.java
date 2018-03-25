package pl.filipmarkiewicz.asystentkierowcy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class RefuelingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refueling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
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


        FileDatabaseManager fdm = new FileDatabaseManager("refueling.txt", getApplicationContext());

        try {
            LinkedList<String> lines = fdm.read();
            LinkedList<String> list = new LinkedList<String>();
            String current;

            for (String line : lines) {
                Refueling refueling = Refueling.createFromFileLine(line);

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
