package com.example.lostandfoundapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.view.View;

public class AdvertList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdvertAdapter adapter;
    private DatabaseHelper dbHelper;
    private Spinner spinnerFilter;
    private List<Item> allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        // Add back button to action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        // Find views using findViewById
        recyclerView = findViewById(R.id.recyclerView);
        spinnerFilter = findViewById(R.id.spinnerFilter);

        // Set up the RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load all items from the database
        allItems = loadItems();

        // Set up the adapter with the items
        adapter = new AdvertAdapter(this, allItems);
        recyclerView.setAdapter(adapter);

        // Setting up the filter Spinner
        String[] categories = {"All", "Electronics", "Pets", "Wallets", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFilter.setAdapter(categoryAdapter);

        // Set up the Spinner selection listener
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                if (selected.equals("All")) {
                    adapter.updateList(allItems);
                } else {
                    List<Item> filteredList = new ArrayList<>();
                    for (Item item : allItems) {
                        if (item.getCategory().equals(selected)) {
                            filteredList.add(item);
                        }
                    }
                    adapter.updateList(filteredList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // Reload the list every time the screen comes back into view
    @Override
    protected void onResume() {
        super.onResume();
        allItems = loadItems();
        adapter.updateList(allItems);
    }

    // Handle the back button in the action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // This method reads all items from the database and returns them as a List
    private List<Item> loadItems() {
        List<Item> items = new ArrayList<>();
        Cursor cursor = dbHelper.getAllItems();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                String postType = cursor.getString(cursor.getColumnIndexOrThrow("post_type"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));

                // Create a new Item object and add it to the list
                Item item = new Item(id, postType, name, phone, description, date, location, category, image, timestamp);
                items.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return items;
    }
}