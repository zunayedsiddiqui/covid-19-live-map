package zunayed.siddiqui.utdallas.edu.mapapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ArrayList<Case> cases;  // Arraylist to hold the cases
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize all the variables and objects
        cases = new ArrayList<>();
        recyclerView = findViewById(R.id.rv);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        populateList();

        // Set the adapter to show the data in the recyclerview
        adapter = new ListAdapter(cases);
        recyclerView.setAdapter(adapter);
    }

    private void populateList() {
        // Test data
        cases.add(new Case("Dhaka", 700));
        cases.add(new Case("Gazipur", 150));
        cases.add(new Case("Mymensigh", 25));
        cases.add(new Case("Narayangonj", 90));
        cases.add(new Case("Savar", 20));
        cases.add(new Case("Sylhet", 200));
        cases.add(new Case("Chottogram", 300));


        // TODO: Parse JSON into cases list
    }
}
