package com.example.coursework02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    DatabaseHelper DB;

    ArrayList<String> editList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ListView listViewEdit = findViewById(R.id.listViewEdit1);

        DB = new DatabaseHelper(this);
        Cursor cursor = DB.getData();

        if (cursor.getCount() == 0) {
            Toast.makeText(EditActivity.this, "NO MOVIES ADDED", Toast.LENGTH_LONG).show();         /**get data from database*/
            return;
        }

        int count =1;

        while (cursor.moveToNext()) {
            editList.add(cursor.getString(0));
            ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, editList);
            listViewEdit.setAdapter(listAdapter);
            count++;
        }

        listViewEdit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EditActivity.this,EditActivity2.class);
                String s = (String) parent.getItemAtPosition(position);                        /**clickable list view and go another activity forUpdate inputs*/
                intent.putExtra("indexVal", s);
                startActivity(intent);
            }
        });


    }
}
