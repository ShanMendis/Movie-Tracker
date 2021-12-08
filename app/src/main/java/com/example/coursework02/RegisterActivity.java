package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
     EditText tfRegName,tfRegYear,tfRegDirector,tfRegArtists,tfRegRatings,tfRegReviews;
     Button btnRegRegister;
     DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tfRegName= findViewById(R.id.tfEditName);
        tfRegYear= findViewById(R.id.tfEditYear);
        tfRegDirector= findViewById(R.id.tfEditDirector);
        tfRegArtists= findViewById(R.id.tfEditActress);
        tfRegRatings= findViewById(R.id.tfRegRatngs);
        tfRegReviews= findViewById(R.id.tfEditReviews);

        btnRegRegister= findViewById(R.id.btnUpdate);

        DB = new DatabaseHelper(this);
        btnRegRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = tfRegName.getText().toString().toLowerCase();
                String yearTXT = tfRegYear.getText().toString();
                String directorTXT = tfRegDirector.getText().toString().toLowerCase();
                String artistsTXT = tfRegArtists.getText().toString().toLowerCase();
                String ratingsTXT = tfRegRatings.getText().toString();
                String reviewsTXT = tfRegReviews.getText().toString().toLowerCase();       //assign data inputs for Strings


                Boolean checkinsertdata = false;
                if(nameTXT.isEmpty() || yearTXT.isEmpty() || directorTXT.isEmpty() || artistsTXT.isEmpty() || ratingsTXT.isEmpty() || reviewsTXT.isEmpty()) {
                    if(checkinsertdata==false){
                        Toast.makeText(RegisterActivity.this,"Please Fill ALL Fields!!!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    boolean validation= false;
                    if(Integer.parseInt(yearTXT)>=1985 && Integer.parseInt(yearTXT)<=2021){
                        if(Integer.parseInt(ratingsTXT)>=0 && Integer.parseInt(ratingsTXT)<=10){        //validate year and ratings
                            boolean i = DB.insertmoviedetails(nameTXT,yearTXT,directorTXT,artistsTXT,ratingsTXT,reviewsTXT);
                            if (i){
                                Toast.makeText(RegisterActivity.this,"New Entry Inserted...",Toast.LENGTH_SHORT).show();
                                tfRegName.setText("");
                                tfRegYear.setText("");
                                tfRegDirector.setText("");          /**if regiser cmplete all fields empty*/
                                tfRegArtists.setText("");
                                tfRegRatings.setText("");
                                tfRegReviews.setText("");
                            }else{
                                Toast.makeText(RegisterActivity.this,"Already Exists!!!",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            if(!validation){
                                tfRegRatings.setError("Ratings Range 0-10");
                            }
                        }
                    } else{
                        if (!validation) {
                            tfRegYear.setError("Year Range 1985-2021");
                        }
                    }

                }
            }
        });
    }
}
