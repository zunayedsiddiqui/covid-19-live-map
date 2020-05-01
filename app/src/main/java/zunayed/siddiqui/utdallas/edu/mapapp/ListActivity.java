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

        fetchData("Bangladesh", "country", String.valueOf(LocalDate.now()));    // District cases
//        fetchData("Dhaka", "city", String.valueOf(LocalDate.now()));            // Dhaka zone cases
    }

    protected void populateList(JSONObject inputJson) {
        // Set the adapter to show the data in the recyclerview and parse json into list
        adapter = new ListAdapter(parseJson(inputJson));
        recyclerView.setAdapter(adapter);
    }

    protected void fetchData(String name, String type, String date) {
        String params = "name="+name+"&type="+type+"&date="+date;

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
                    populateList(result);
                }
                else{
                    //We didn't get a response we expected.
                    Toast.makeText(getApplicationContext(), "Error!!", Toast.LENGTH_SHORT).show();
                }

                //TODO: Hide the spinner
            }
        };

        df.execute(params);
    }

    protected ArrayList<Case> parseJson(JSONObject jsonObject) {
        ArrayList<Case> caseList = new ArrayList<>();

        try {
            JSONObject payload = jsonObject.getJSONObject("payload");      // JSON object which contains all the data
            String level = payload.getString("level");              // Determines if city or zone
            JSONArray data = payload.getJSONArray("data");          // JSON array which contains the list of city/zone-cases

            for (int i = 0; i < data.length(); i++) {
                JSONObject obj = data.getJSONObject(i);
                String loc = (level.equals("city") ? "city" : "zone");
                caseList.add(new Case(obj.getString(loc), obj.getInt("cases")));
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

        return caseList;
    }
}
