package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class ItemDetail extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private int itemId;
    private ImageView showImage;
    private TextView detailName, detailPostType, detailCategory, detailPhone, detailDescription, detailDate, detailLocation, detailTimestamp;
    private Button btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        // Get the item ID that was passed from the adapter
        itemId = getIntent().getIntExtra("item_id", -1);

        // Find all TextViews and ImageView using findViewById
        detailPostType = findViewById(R.id.postType);
        btnRemove = findViewById(R.id.btnRemove);
        showImage = findViewById(R.id.showImage);
        detailName = findViewById(R.id.detailName);
        detailCategory = findViewById(R.id.detailCategory);
        detailPhone = findViewById(R.id.detailphoneNumber);
        detailDescription = findViewById(R.id.detailDescription);
        detailDate = findViewById(R.id.detailDate);
        detailLocation = findViewById(R.id.detailLocation);
        detailTimestamp = findViewById(R.id.detailtimeStamp);

        // Load the item from the database
        Cursor cursor = dbHelper.getItemById(itemId);

        if (cursor != null && cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String postType = cursor.getString(cursor.getColumnIndexOrThrow("post_type"));
            String category = cursor.getString(cursor.getColumnIndexOrThrow("category"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
            String timestamp = cursor.getString(cursor.getColumnIndexOrThrow("timestamp"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            detailName.setText(name);
            detailPostType.setText(postType);
            detailCategory.setText(category);
            detailPhone.setText(phone);
            detailDescription.setText(description);
            detailDate.setText(date);
            detailLocation.setText(location);
            detailTimestamp.setText(timestamp);

            if (image != null && !image.isEmpty()) {
                showImage.setImageURI(Uri.parse(image));
            }

            cursor.close();
        }

        // Set up the Remove button
        btnRemove.setOnClickListener(v -> {
            dbHelper.deleteItem(itemId);
            Toast.makeText(this, "Item removed", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Handle the back button in the action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}