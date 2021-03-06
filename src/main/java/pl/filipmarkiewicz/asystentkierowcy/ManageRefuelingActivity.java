package pl.filipmarkiewicz.asystentkierowcy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.filipmarkiewicz.filedatabase.FileDatabaseManager;

public class ManageRefuelingActivity extends AppCompatActivity {
    public static final int TYPE_ADD = 0;
    public static final int TYPE_EDIT = 1;

    public static final int RESULT_OK = 0;
    public static final int RESULT_FAILED = 1;

    private int id;
    private int type;
    private double amount;
    private double price;
    private Calendar date;

    private Intent currentIntent;

    private final FuelType[] fuelTypes = {
            FuelType.UNDEFINED,
            FuelType.PETROL_PLAIN,
            FuelType.DIESEL_PLAIN,
            FuelType.DIESEL_VERVA
    };

    private final String[] fuelTypesNames = {
            Refueling.getFuelTypeName(FuelType.UNDEFINED),
            Refueling.getFuelTypeName(FuelType.PETROL_PLAIN),
            Refueling.getFuelTypeName(FuelType.DIESEL_PLAIN),
            Refueling.getFuelTypeName(FuelType.DIESEL_VERVA)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_refueling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("Dodaj tankowanie");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentIntent = getIntent();

        type = currentIntent.getIntExtra("TYPE", 0);

        TextView idView = (TextView) findViewById(R.id.refuelingId);

        Spinner fuelTypeView = (Spinner) findViewById(R.id.refuelingFuelType);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fuelTypesNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fuelTypeView.setAdapter(adapter);

        if (type == TYPE_EDIT) {
            id = currentIntent.getIntExtra("ID", 0);
            amount = currentIntent.getDoubleExtra("AMOUNT", 0);
            price = currentIntent.getDoubleExtra("PRICE", 0);
            date = Base.parseDate(currentIntent.getStringExtra("DATE"));
        } else {
            idView.setText(0 + "");
        }
    }

    public void submit(View view) {
        int id = Integer.parseInt(((TextView) findViewById(R.id.refuelingId)).getText().toString());
        double amount = Double.parseDouble(((TextView) findViewById(R.id.refuelingAmount)).getText().toString());
        double price = Double.parseDouble(((TextView) findViewById(R.id.refuelingPrice)).getText().toString());

        Spinner fuelTypeView = findViewById(R.id.refuelingFuelType);

        int selectedFuelTypePosition = fuelTypeView.getSelectedItemPosition();

        FuelType fuelType = fuelTypes[selectedFuelTypePosition];

        Refueling result = new Refueling(id, fuelType, amount, price, new GregorianCalendar());

        FileDatabaseManager fdm = new FileDatabaseManager(Config.REFUELINGS_FILE, getApplicationContext());

        int resultState;

        if (result.save(fdm)) {
            resultState = RESULT_OK;
        } else {
            resultState = RESULT_FAILED;
        }

        //currentIntent.putExtra("resultId", result.getId());
        //currentIntent.putExtra("resultAmount", result.getAmount());
        //currentIntent.putExtra("resultPrice", result.getPrice());

        setResult(resultState, currentIntent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}
