package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class RatingsActivity extends AppCompatActivity {
    DatabaseHelper DB;
    ArrayList<String> movieList = new ArrayList<>();
    String movieTitle;
    String picture_url;
    ImageView imageView;
    ListView listViewRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        DB = new DatabaseHelper(this);

        new Thread(new MovieDetailsRunnable(movieTitle)).start();
        display();
    }

    class MovieDetailsRunnable implements Runnable {
        String titleRequested;

        public MovieDetailsRunnable(String movieTitle) {
            titleRequested = movieTitle;
        }

        @Override
        public void run() {
            StringBuilder stb = new StringBuilder();
            StringBuilder movie = new StringBuilder();

            try {
                // make the connection and receive the input stream
                URL url = new URL("https://imdb-api.com/en/API/SearchTitle/k_12345678/" +
                        titleRequested.trim());
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                BufferedReader bf = new BufferedReader(new InputStreamReader(con.getInputStream()));
                // read all lines in the string builder
                String line;
                while ((line = bf.readLine()) != null) {
                    stb.append(line);
                }

                // do the JSON parsing
                JSONObject json = new JSONObject(stb.toString());
                JSONArray jsonArray = json.getJSONArray("movies");

                // find the matching title entry and extract movie
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_movie = jsonArray.getJSONObject(i);
                    String movieTitle = json_movie.getString("strMovie");
                    if (movieTitle.toLowerCase().equals(titleRequested.toLowerCase())) {
                        movie.append(json_movie.getString("strInstructions"));
                        picture_url = json_movie.getString("strMovieThumb");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            /*Bitmap movie_picture = getBitmap();

            // update the text view with the recipe
            runOnUiThread(() -> {
                tv2.setText(movie);
                imageView.setImageBitmap(movie_picture);

            });*/
        }
    }

    // retrieve a bitmap image from the URL in JSON
    Bitmap getBitmap() {
        Bitmap bitmap = null;
        try {
            URL url = new URL(picture_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedInputStream bfstream = new BufferedInputStream(con.getInputStream());

            bitmap = BitmapFactory.decodeStream(bfstream);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return bitmap;
    }

    private void display() {
        Cursor cursor = DB.getData();

        if (cursor.getCount() == 0) {
            Toast.makeText(RatingsActivity.this, "No movies available!", Toast.LENGTH_LONG).show();
            return;
        }
        while (cursor.moveToNext()) {
            movieList.add(cursor.getString(0));
            listViewRatings = findViewById(R.id.listViewRatings);
            listViewRatings.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, movieList));
            listViewRatings.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    public void findMovieInIMDB(View view) {
        listViewRatings = findViewById(R.id.listViewRatings);
        SparseBooleanArray selected = listViewRatings.getCheckedItemPositions();
        for (int i = 0; i < listViewRatings.getCount(); i++) {
            if (selected.get(i)) {

            }
        }
    }
}
