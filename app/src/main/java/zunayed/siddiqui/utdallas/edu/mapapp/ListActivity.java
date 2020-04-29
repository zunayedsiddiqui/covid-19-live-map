package zunayed.siddiqui.utdallas.edu.mapapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

/****************************************************************************
 * ListActivity class: This class is the java class for the activity which  *
 *                     displays the case data in a table/list format        *
 *                                                                          *
 *  * @author Ihfaz Tajwar                                                  *
 ****************************************************************************/
public class ListActivity extends AppCompatActivity {

    private ArrayList<Case> cases;  // Arraylist to hold the cases
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Initialize all the variables and objects
        cases = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

//        populateList(null);

        fetchData("Bangladesh", "country", String.valueOf(LocalDate.now()));

    }

    private void populateList(JSONObject jsonObject) {
        // Test data
        cases.add(new Case("Dhaka", 700));
        cases.add(new Case("Gazipur", 150));
        cases.add(new Case("Mymensigh", 25));
        cases.add(new Case("Narayangonj", 90));
        cases.add(new Case("Savar", 20));
        cases.add(new Case("Sylhet", 200));
        cases.add(new Case("Chottogram", 300));


        // TODO: Parse JSON into cases list
        System.out.println(jsonObject);

        // Set the adapter to show the data in the recyclerview
        adapter = new ListAdapter(cases);
        recyclerView.setAdapter(adapter);
    }

    public void fetchData(String name, String type, String date) {
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
}
