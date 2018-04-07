package pl.filipmarkiewicz.asystentkierowcy;

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

public class ManageRepairActivity extends AppCompatActivity {
    public static final int TYPE_ADD = 0;
    public static final int TYPE_EDIT = 1;

    public static final int RESULT_OK = 0;
    public static final int RESULT_FAILED = 1;

    private int type;

    private int id;
    private String name;
    private double expectedPrice;
    private boolean isDone;
    private double finalPrice;
    private String description;
    private Calendar date;

    private Intent currentIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_repair);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        toolbar.setTitle("Zarządzaj naprawą");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentIntent = getIntent();

        type = currentIntent.getIntExtra("TYPE", 0);

        TextView idView = (TextView) findViewById(R.id.repairId);

        if (type == TYPE_EDIT) {
            id = currentIntent.getIntExtra("ID", 0);
            expectedPrice = currentIntent.getDoubleExtra("EXPECTED_PRICE", 0);
            name = currentIntent.getStringExtra("NAME");
        } else {
            idView.setText(0 + "");
        }
    }

    public void submit(View view) {
        int id = Integer.parseInt(((TextView) findViewById(R.id.repairId)).getText().toString());
        double expectedPrice = Double.parseDouble(((TextView) findViewById(R.id.repairExpectedPrice)).getText().toString());
        String name = ((TextView) findViewById(R.id.repairName)).getText().toString();

        Repair result = new Repair(id, false, name, expectedPrice);

        FileDatabaseManager fdm = new FileDatabaseManager(Config.REPAIRS_FILE, getApplicationContext());

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
