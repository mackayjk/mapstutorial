package com.example.mapstutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    PlacesClient placesClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //This method will initialize the places client.
    public void initializePlaces() {
        Places.initialize(getApplicationContext(), "AIzaSyB2Ul315SoD2GRH-xCMvpYUQ3sOlsx7u-Q");
        placesClient = Places.createClient(this);
    }
    public void getRestaurant(View view) {
        initializePlaces();
        //get the place ID
        String placeId = "ChIJf83U3auQTYcRcsZgTfnaQOg";
        //specify the fields to return
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.RATING, Place.Field.TYPES, Place.Field.WEBSITE_URI);
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);

        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
            Place place = response.getPlace();

            Log.v(TAG, "Restaurant found: " + place.getName());
            Log.v(TAG, "Restaurant Address: " + place.getAddress());
            Log.v(TAG, "Restaurant Rating: " + place.getRating());
            Log.v(TAG, "Restaurant Type: " + place.getTypes());
            Log.v(TAG, "Restaurant Website: " + place.getWebsiteUri());
        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                int statusCode = apiException.getStatusCode();
                // Handle error with given status code.
                Log.v(TAG, "Place not found: " + exception.getMessage());
            }
            Log.v(TAG, "This isn't working");
        });
    }

    public void loadRestaurant(View view) throws InterruptedException {
        initializePlaces();
        String placeId = "ChIJf83U3auQTYcRcsZgTfnaQOg";
        RestaurantLoader restaurantLoader = new RestaurantLoader(placesClient);
        Restaurant r = restaurantLoader.getRestaurant(placeId);
        Toast.makeText(this, "testing the toast message." + r.getRestaurantName(), Toast.LENGTH_SHORT).show();

    }


}
