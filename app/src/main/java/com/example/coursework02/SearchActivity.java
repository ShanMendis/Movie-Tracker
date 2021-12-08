package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends AppCompatActivity {

DatabaseHelper DB;
EditText editTextTextPersonName;
Button btnSearch;
TextView textViewS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        DB= new DatabaseHelper(this);

        editTextTextPersonName= findViewById(R.id.editTextTextPersonName);
        btnSearch= findViewById(R.id.btnSearch);
        textViewS= findViewById(R.id.textViewS);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor1 = DB.getData1();
                String value = editTextTextPersonName.getText().toString();

                if (cursor1.getCount() != 0){
                    StringBuilder stringBuilder = new StringBuilder();
                    while (cursor1.moveToNext()) {                                                              /*get data fro databse*/
                        String t1 = cursor1.getString(0);
                        String t2 = cursor1.getString(2);
                        String t3 = cursor1.getString(3);

                        if (t1.contains(value) || t2.contains(value) || t3.contains(value)) {
                            stringBuilder.append("Movie : " + t1 + "\n" + "Director :"  + t2 + "\n" + "Artists : " + t3 + "\n\n");        /*equals with name*/
                        }
                    }


                    textViewS.setText(stringBuilder.toString());
                    textViewS.setMovementMethod(new ScrollingMovementMethod());
                } else {
                    Toast.makeText(SearchActivity.this,"NO MOVIES ADDED",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}