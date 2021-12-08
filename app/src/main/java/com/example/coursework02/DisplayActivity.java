package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    ArrayList<String> displayList = new ArrayList<>();
    Button btnaddFavouites;
    DatabaseHelper DB;
    ListView listViewDisplayMovies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);


        btnaddFavouites= findViewById(R.id.btnaddFavouites);
        listViewDisplayMovies= findViewById(R.id.listViewDisplayMovies);

        DB = new DatabaseHelper(this);
        display();  //call display method
        //display all registered movies

        btnaddFavouites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean insert= false;                       //boolean value for insert data

               ArrayList<String> tickedMoviesList = new ArrayList<>();          //arrayList for selected movies

                SparseBooleanArray checkedList = listViewDisplayMovies.getCheckedItemPositions();  //get checked movies from display list view
                for (int i=0; i<listViewDisplayMovies.getCount(); i++){
                    if (checkedList.get(i)){                                /*checked movies add to elected movies*/
                        tickedMoviesList.add(displayList.get(i));
                    }
                }
                for(int i=0; i<tickedMoviesList.size(); i++){
                    String nameM = tickedMoviesList.get(i);
                    insert=DB.insertfav(nameM);                 /*selected movies add to database*/
                }
                if (insert){
                    Toast.makeText(DisplayActivity.this,"Added To Favourites",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(DisplayActivity.this,"Already inn",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void display(){

        Cursor cursor = DB.getData();
        int count = 1;
        if (cursor.getCount() == 0){
            Toast.makeText(DisplayActivity.this,"NO MOVIES ADDED!!",Toast.LENGTH_LONG).show();
            return;
        }
                                                                       /** get data from database
                                                                                            but get by name*/
        while (cursor.moveToNext()){
            displayList.add(count  +". "+ cursor.getString(0));
            ListAdapter listAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice, displayList);
            listViewDisplayMovies.setAdapter(listAdapter);
            count++;
            listViewDisplayMovies.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
    }

}