package zunayed.siddiqui.utdallas.edu.mapapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaunchingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launching);
    }

    // This is the onClick method for the map view button and takes the user to the MapsActivity
    public void toMap(View view) {
        startActivity(new Intent(LaunchingActivity.this, MapsActivity.class));
    }

    // This is the onClick method for the list view button and takes the user to the ListActivity
    public void toList(View view) {
        startActivity(new Intent(LaunchingActivity.this, ListActivity.class));
    }
}
