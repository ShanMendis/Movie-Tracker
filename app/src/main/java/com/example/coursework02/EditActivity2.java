package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity2 extends AppCompatActivity {

    EditText tfEditName;
    EditText tfEditYear;
    EditText tfEditDirector;
    EditText tfEditActress;
    EditText tfEditReviews;
    RatingBar ratingBar;
    Button btnUpdate;

    DatabaseHelper DB;

    ArrayList<String> edNameList = new ArrayList<>();
    ArrayList<String> edYearList = new ArrayList<>();
    ArrayList<String> edDirectorList = new ArrayList<>();
    ArrayList<String> edActressList = new ArrayList<>();           /** Array lists for one variables*/
    ArrayList<String> edRatingsList = new ArrayList<>();
    ArrayList<String> edReviewList = new ArrayList<>();
    String index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit2);

        tfEditName = findViewById(R.id.tfEditName);
        tfEditYear = findViewById(R.id.tfEditYear);
        tfEditDirector = findViewById(R.id.tfEditDirector);
        tfEditActress = findViewById(R.id.tfEditActress);
        ratingBar = findViewById(R.id.ratingBar);
        tfEditReviews = findViewById(R.id.tfEditReviews);
        btnUpdate = findViewById(R.id.btnUpdate);



        Intent intent = getIntent();
        index = intent.getStringExtra("indexVal");      //get index from previous activity
        System.out.println(index);

        DB = new DatabaseHelper(this);
        Cursor cursor2 = DB.getData1();            //get data fro database

        while (cursor2.moveToNext()) {
            String s1 = cursor2.getString(0);
            String s2 = cursor2.getString(1);
            String s3 = cursor2.getString(2);
            String s4 = cursor2.getString(3);       /*assign data for String*/
            String s5 = cursor2.getString(4);
            String s6 = cursor2.getString(5);

            if (s1.equals(index)) {
                edNameList.add(s1);
                edYearList.add(s2);
                edDirectorList.add(s3);    /**check with same name and add that names in to array lists*/
                edActressList.add(s4);
                edRatingsList.add(s5);
                edReviewList.add(s6);
                break;
            }


        }
        
            tfEditName.setText(edNameList.get(0));
            tfEditYear.setText(edYearList.get(0));
            tfEditDirector.setText(edDirectorList.get(0));          /*set previous inputs for same fields*/
            tfEditActress.setText(edActressList.get(0));
            tfEditReviews.setText(edReviewList.get(0));
            ratingBar.setRating(Float.parseFloat(edRatingsList.get(0)));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT1 = tfEditName.getText().toString().toLowerCase();
                String yearTXT1 = tfEditYear.getText().toString();
                String directorTXT1 = tfEditDirector.getText().toString().toLowerCase();
                String artistsTXT1 = tfEditActress.getText().toString().toLowerCase();
                String reviewsTXT1 = tfEditReviews.getText().toString().toLowerCase();              /**update values(edit movies) and add like register*/
                String ratingsTXT1 = String.valueOf(ratingBar.getRating());

                Boolean checkinsertdata = false;
                if(nameTXT1.isEmpty() || yearTXT1.isEmpty() || directorTXT1.isEmpty() || artistsTXT1.isEmpty() || reviewsTXT1.isEmpty()||ratingsTXT1.isEmpty()) {
                    if(checkinsertdata==false){
                        Toast.makeText(EditActivity2.this,"Please Fill ALL Fields!!!",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    boolean validation= false;
                    if(Integer.parseInt(yearTXT1)>=1985 && Integer.parseInt(yearTXT1)<=2021){
                            boolean i = DB.updateamovies(nameTXT1,yearTXT1,directorTXT1,artistsTXT1,ratingsTXT1,reviewsTXT1);
                            if (i){
                                Toast.makeText(EditActivity2.this,"Updated...",Toast.LENGTH_SHORT).show();
                                tfEditName.setText("");
                                tfEditYear.setText("");
                                tfEditDirector.setText("");
                                tfEditActress.setText("");
                                tfEditReviews.setText("");
                                ratingBar.setRating(0);
                            }
                    } else{
                        if (!validation) {
                            tfEditYear.setError("Year Range 1985-2021");
                        }
                    }
                }
            }
        });

    }
}