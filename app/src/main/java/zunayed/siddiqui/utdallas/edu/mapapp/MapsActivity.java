package zunayed.siddiqui.utdallas.edu.mapapp;
// resource website: https://google-developer-training.github.io/android-developer-advanced-course-practicals/unit-4-add-geo-features-to-your-apps/lesson-9-mapping/9-1-p-add-a-google-map-to-your-app/9-1-p-add-a-google-map-to-your-app.html
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Initial marker in Bangladesh
        LatLng bangladesh = new LatLng(23.6850, 90.3563);

        // Add a marker in Dhaka
        LatLng dhaka = new LatLng(23.8103, 90.4125);

        // Add a marker in Chottogram
        LatLng chottogram = new LatLng(22.3569, 91.7832);

        // Add a marker in Sylhet
        LatLng sylhet = new LatLng(24.8949, 91.8687);

        // Add a marker in Narayangonj
        LatLng narayangonj = new LatLng(23.6238, 90.5000);

        // Add a marker in Rajshahi
        LatLng rajshahi = new LatLng(24.3745, 88.6042);

        // add markers for the specific locations
        mMap.addMarker(new MarkerOptions().position(dhaka).title("Dhaka COVID-19 Cases: 700"));
        mMap.addMarker(new MarkerOptions().position(chottogram).title("Chottogram COVID-19 Cases: 300"));
        mMap.addMarker(new MarkerOptions().position(sylhet).title("Sylhet COVID-19 Cases: 200"));
        mMap.addMarker(new MarkerOptions().position(narayangonj).title("Narayangonj COVID-19 Cases: 90"));
        mMap.addMarker(new MarkerOptions().position(rajshahi).title("Rajshahi COVID-19 Cases: 165"));

        // set a speicific zoom of the map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bangladesh, 7F)); // Zooming 6times
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_option1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
