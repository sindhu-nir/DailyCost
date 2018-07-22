package com.example.root.dailycost;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Listlayout extends AppCompatActivity {



    private static final String TAG = "ListDataActivity";

    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    String From,To;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listlayout);


        Intent intent = getIntent();
        From=intent.getStringExtra("from");
        To=intent.getStringExtra("to");

        mListView = (ListView) findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }



    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Double sum=0.0;
        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData(From,To);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            //get the value from the database in column 1
            //then add it to the ArrayList

            String date,benson,goldleaf,tea,other,total;
            int id;




            id=data.getInt(0);
            date=data.getString(6);
            benson=data.getString(1);
            goldleaf=data.getString(2);
            tea=data.getString(3);
            other=data.getString(4);
            total=data.getString(5);
            sum=sum+Double.parseDouble(total);


            listData.add(id+"     "+date+"     "+benson+"     "+goldleaf+"     "+tea+"     "+other+"     "+total);
        }

        listData.add("Total Amount :                    "+String.valueOf(sum));
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                //CustomDialogue(name);

                String[] parts = name.split("     ");
                String part1 = parts[0]; // 004



                mDatabaseHelper.deleteRow(part1); //get the id associated with that name


                toastMessage("Delete Successfull");
                finish();
                startActivity(getIntent());

            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
