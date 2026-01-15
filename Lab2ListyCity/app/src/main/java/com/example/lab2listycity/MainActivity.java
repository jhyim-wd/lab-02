package com.example.lab2listycity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    Button addButton, deleteButton;
    int listViewPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cityList = findViewById(R.id.city_list);

        String[] cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        addButton = findViewById(R.id.button_addCity);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Add a city");

                EditText input = new EditText(MainActivity.this);
                builder.setView(input);
                builder.setPositiveButton("CONFIRM", (dialog, which) -> {
                    String cityName = input.getText().toString();
                    if (!cityName.isEmpty()) {
                        dataList.add(cityName);
                        cityAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this,"Invalid city", Toast.LENGTH_SHORT).show();
                    }
                });
                /*
                   Author: Jeeva https://stackoverflow.com/users/6059583/jeeva
                   Title: EditText in Alert Dialog Android
                   Answer: https://stackoverflow.com/a/55685054
                   Date: 2019-04-15
                   License: CC-BY-SA 4.0 (International)
                */

                builder.setNegativeButton("CANCEL", null);
                builder.show();
            }
        });

        deleteButton = findViewById(R.id.button_deleteCity);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listViewPosition != -1) {
                    dataList.remove(listViewPosition);
                    cityAdapter.notifyDataSetChanged();
                    listViewPosition = -1;
                }
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewPosition = position;
            }
        });
    }

}