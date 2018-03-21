package pl.filipmarkiewicz.asystentkierowcy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAboutVehicleDataActivity extends AppCompatActivity {
    Intent currentIntent;

    String code;
    String key;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_about_vehicle_data);

        currentIntent = getIntent();

        code = currentIntent.getStringExtra("CODE");
        key = currentIntent.getStringExtra("KEY");
        value = currentIntent.getStringExtra("VALUE");

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle(key);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView keyView = findViewById(R.id.manageKey);
        keyView.setText(key);

        EditText valueView = findViewById(R.id.manageValue);
        valueView.setText(value);
    }

    public void submit(View view) {
        EditText valueView = findViewById(R.id.manageValue);
        String modifiedValue = valueView.getText().toString();

        SharedPreferences sp = getSharedPreferences("driver-assistant", MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();

        spe.putString(code, modifiedValue);
        spe.commit();

        currentIntent.putExtra("resultValue", modifiedValue);

        setResult(Activity.RESULT_OK, currentIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
