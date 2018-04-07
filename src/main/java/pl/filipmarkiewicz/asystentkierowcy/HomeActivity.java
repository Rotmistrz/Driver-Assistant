package pl.filipmarkiewicz.asystentkierowcy;

import android.content.Intent;
import android.net.Uri;
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
        String action = "pl.filipmarkiewicz.asystentkierowcy.intent.action.InvokeAboutVehicle";

        final Intent aboutVehicleIntent = new Intent(action);

        startActivity(aboutVehicleIntent);
    }

    public void invokeAboutAuthor(View view) {
        final Intent aboutAuthorIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://filipmarkiewicz.pl"));

        startActivity(aboutAuthorIntent);
    }
}
