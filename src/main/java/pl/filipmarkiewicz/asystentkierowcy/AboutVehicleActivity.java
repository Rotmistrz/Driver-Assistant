package pl.filipmarkiewicz.asystentkierowcy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutVehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_vehicle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("O pojeździe");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadData();
    }

    protected void onRestart(Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    public void loadData() {
        final SimpleRecord[] records = {
                new SimpleRecord("producer", "Producent", "Producent"),
                new SimpleRecord("model", "Model", "Model"),
                new SimpleRecord("vin", "VIN", "VIN"),
                new SimpleRecord("production-year", "Rok produkcji", "Rok produkcji"),
                new SimpleRecord("horse-power", "Ilość koni mechanicznych", "Ilość koni mechanicznych")
        };

        SharedPreferences sp = getSharedPreferences("driver-assistant", MODE_PRIVATE);

        for (int i = 0; i < records.length; i++) {
            SimpleRecord current = records[i];

            String value = sp.getString(current.getCode(), current.getValue());

            current.setValue(value);
        }

        ListView aboutVehicleList = findViewById(R.id.aboutVehicleList);

        final AboutVehicleActivity that = this;

        aboutVehicleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SimpleRecord current = records[position];

                String code = current.getCode();
                String key = current.getKey();
                String value = current.getValue();

                Intent manageIntent = new Intent(that, ManageAboutVehicleDataActivity.class);
                manageIntent.putExtra("CODE", code);
                manageIntent.putExtra("KEY", key);
                manageIntent.putExtra("VALUE", value);

                startActivityForResult(manageIntent, 1);
            }
        });

        aboutVehicleList.setAdapter(new SimpleRecordAdapter(this, records));
    }

    @Override
    protected void onActivityResult(int requestID, int resultCode, Intent intent) {
        if (resultCode == Activity.RESULT_OK && requestID == 1) {
            loadData();
        }
    }
}
