package zunayed.siddiqui.utdallas.edu.mapapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/****************************************************************************
 * ListActivity class: This class is the java class for the activity which  *
 *                     displays the case data in a table/list format.       *
 *                                                                          *
 * @author Ihfaz Tajwar                                                     *
 ****************************************************************************/
public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize recycle view and layoutManager
        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        fetchData("Bangladesh", "country", String.valueOf(LocalDate.of(2020, 04, 29)));    // District cases
//        fetchData("Dhaka", "city", String.valueOf(LocalDate.now()));            // Dhaka zone cases
    }

    protected void populateList(JSONObject inputJson) {
        // Set the adapter to show the data in the recyclerview and parse json into list
        adapter = new ListAdapter(parseJson(inputJson));
        recyclerView.setAdapter(adapter);
    }

    // This method is used to get the data about covid cases from the API. It uses the AsyncTask class.
    protected void fetchData(String name, String type, String date) {
        // Parameters for the API
        String params = "name="+name+"&type="+type+"&date="+date;

        // Creating a DataFetcher object to be executed as a separate thread
        DataFetcher df = new DataFetcher() {
            //Function that is called from the AsyncTask before the work is started.
            @Override
            public void onPreExecute(){
                //TODO: Show progress bar/spinner
            }

            //Function that is called from the AsyncTask when the background work is done. Updates the UI with the results
            @Override
            public void onPostExecute(JSONObject result){
                if(result != null) {
                    populateList(result);   // Populate the list if a result is fetched
                }
                else{
                    //We didn't get a response we expected.
                    Toast.makeText(getApplicationContext(), "Error! Couldn't fetch data.", Toast.LENGTH_SHORT).show();
                }

                //TODO: Hide the spinner
            }
        };

        // Execute the thread
        df.execute(params);
    }

    // This method is used to parse the JSON from the API into a list of cases
    protected ArrayList<Case> parseJson(JSONObject jsonObject) {
        ArrayList<Case> caseList = new ArrayList<>();

        try {
            if (jsonObject.getString("status").equals("error")) {
                Toast.makeText(getApplicationContext(), "Error! Couldn't find data.", Toast.LENGTH_SHORT).show();
                return null;
            }

            JSONObject payload = jsonObject.getJSONObject("payload");     // JSON object which contains all the data
            String level = payload.getString("level");              // Determines if city or zone
            JSONArray data = payload.getJSONArray("data");          // JSON array which contains the list of city/zone-cases

            // Loop through the JSON objects inside the array
            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i); // Get objects at each array index
                String loc = (level.equals("city") ? "city" : "zone");  // Determines whether it is a city or a Dhaka zone
                caseList.add(new Case(obj.getString(loc), obj.getInt("cases")));    // Adds each case to a list
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        // Sort the list by number of cases in descending order
        Collections.sort(caseList, new Comparator<Case>() {
            @Override
            public int compare(Case obj1, Case obj2) {  // Compare number of cases
                return obj2.getnCase() - obj1.getnCase();
            }
        });

        return caseList;    // Return the list created
    }
}
