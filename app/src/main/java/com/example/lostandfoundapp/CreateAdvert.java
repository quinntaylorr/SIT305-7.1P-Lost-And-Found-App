package com.example.lostandfoundapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateAdvert extends AppCompatActivity {

    // Declare variables for all form elements
    private RadioGroup radioGroupType;
    private EditText editName, editPhone, editDescription, editDate, editLocation;
    private Spinner spinnerCategory;
    private Button btnUploadImage, btnSave;
    private ImageView imagePreview;
    private Uri selectedImageUri;
    private DatabaseHelper dbHelper;

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_advert);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DatabaseHelper(this);

        // Match each variable above to its XML id
        radioGroupType = findViewById(R.id.radioGroupType);
        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editLocation = findViewById(R.id.editLocation);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnUploadImage = findViewById(R.id.btnuploadImage);
        btnSave = findViewById(R.id.btnSave);
        imagePreview = findViewById(R.id.showImage);

        // Set up the category Spinner with an ArrayAdapter
        String[] categories = {"Select Category", "Electronics", "Pets", "Wallets", "Other"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        // Set up the Upload Image button click listener
        btnUploadImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            // Start the activity and wait for a result
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Set up the Save button click listener
        btnSave.setOnClickListener(v -> {
            // Get text from each EditText
            String name = editName.getText().toString();
            String phone = editPhone.getText().toString();
            String description = editDescription.getText().toString();
            String date = editDate.getText().toString();
            String location = editLocation.getText().toString();

            // Check which radio button is selected
            int selectedRadioId = radioGroupType.getCheckedRadioButtonId();
            String postType;
            if (selectedRadioId == R.id.radioLost) {
                postType = "Lost";
            } else {
                postType = "Found";
            }
            // Get selected category from Spinner
            String category = spinnerCategory.getSelectedItem().toString();

            // Get the image URI as a string
            String image = "";
            if (selectedImageUri != null) {
                image = selectedImageUri.toString();
            }

            // Generate a timestamp
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

            // Save to database
            dbHelper.insertItem(postType, name, phone, description, date, location, category, image, timestamp);

            // Show a success message
            Toast.makeText(getApplicationContext(), "Item saved", Toast.LENGTH_SHORT).show();

            // Close this screen and go back to home
            finish();
        });
    }

    // Handle the back button in the action bar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Override onActivityResult to handle the image that comes back from the gallery picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            imagePreview.setImageURI(selectedImageUri);
        }
    }
}