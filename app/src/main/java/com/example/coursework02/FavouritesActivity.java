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

public class FavouritesActivity extends AppCompatActivity {
    DatabaseHelper DB;
    ListView favListView;
    Button btnFavSave;
    ArrayList<String> favList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        btnFavSave= findViewById(R.id.btnfavSave);
        favListView= findViewById(R.id.favListView);

        DB= new DatabaseHelper(this);
        Cursor cursor1 = DB.getFavMovie();
        int counter = 0;
        if (cursor1.getCount() == 0){                                                                               //get data from database
            Toast.makeText(FavouritesActivity.this,"NO MOVIES ADDED",Toast.LENGTH_LONG).show();
            return;
        }
        while (cursor1.moveToNext()){
            favList.add(cursor1.getString(0)); //get name from database
            final ListAdapter listAdapter1 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_multiple_choice, favList);
            favListView.setAdapter(listAdapter1);
            favListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);           // multiple selects from checkbox
            counter++;
            for (int i = 0; i < counter ; i++) {
                favListView.setItemChecked(i, true);    //all are checked by default
            }

            btnFavSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SparseBooleanArray checkedList = favListView.getCheckedItemPositions();
                    for (int i=0; i<favListView.getCount(); i++){
                        if (!checkedList.get(i)){
                            String str = favList.get(i);
                            DB.deletefav(str);
                            finish();                                  /* refresh intent again after save*/
                            startActivity(getIntent());
                            Toast.makeText(FavouritesActivity.this,"Remove From Favourites......",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

        }

    }


}