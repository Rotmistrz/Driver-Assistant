package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void invokeRefuelingActivity(View view) {
        final Intent refuelingIntent = new Intent(this, RefuelingActivity.class);

        startActivity(refuelingIntent);
    }

    public void invokeRepairsActivity(View view) {
        final Intent repairsIntent = new Intent(this, RepairsActivity.class);

        startActivity(repairsIntent);
    }

    public void invokeAboutVehicleActivity(View view) {
        final Intent aboutVehicleIntent = new Intent(this, AboutVehicleActivity.class);

        startActivity(aboutVehicleIntent);
    }
}
