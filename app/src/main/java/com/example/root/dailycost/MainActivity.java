package com.example.root.dailycost;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper mDatabaseHelper;

     Button btnAdd,btnCancel,bensonRefreshButton,goldLeafREfreshButton,teaRefreshButton,otherRefreshButton;
    ImageButton bensonImageButton,teaImageButton,goldLeafImageBUtton,otherImageButton;
     EditText editText;


    String Date;

    TextView bensonPriceTextView,goldLeafTextView,teaTextView,otherTextView,dateTimeTextView;

    Double benson=11.0,goldLeaf=8.0,tea=6.0,other,total=0.00;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bensonRefreshButton = (Button) findViewById(R.id.bensonPriceReset_button);
        goldLeafREfreshButton = (Button) findViewById(R.id.goldLeafPriceReset_button);
        teaRefreshButton= (Button) findViewById(R.id.teaPriceReset_button);
        otherRefreshButton= (Button) findViewById(R.id.otherPriceReset_button);

        bensonImageButton=(ImageButton)findViewById(R.id.bensonImane_button);
        goldLeafImageBUtton=(ImageButton)findViewById(R.id.goldLeafImane_button);
        teaImageButton=(ImageButton)findViewById(R.id.teaImane_button);
        otherImageButton=(ImageButton)findViewById(R.id.otherImane_button);

        bensonPriceTextView=(TextView)findViewById(R.id.bensonPrice_textView);
        goldLeafTextView=(TextView)findViewById(R.id.goldLeafPrice_textView);
        teaTextView=(TextView)findViewById(R.id.teaPrice_textView);
        dateTimeTextView=(TextView)findViewById(R.id.dateTime_textView);
        otherTextView=(TextView)findViewById(R.id.otherPrice_textView);

        mDatabaseHelper = new DatabaseHelper(this);



        Calendar cal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = df.format(cal.getTime());

        String[] parts=dateStr.split(" ");
        Date=parts[0];

        String s = dateTimeTextView.getText().toString();

        dateTimeTextView.setText(dateStr);
        //editText_dateTime.setEnabled(false);


    }

    public void bensonRefresh(View V){

        bensonPriceTextView.setText("0.0");
    }

    public void goldLeafRefresh(View V){

        goldLeafTextView.setText("0.0");
    }
    public void teaRefresh(View V){

        teaTextView.setText("0.0");
    }

    public void otherRefresh(View V){

        otherTextView.setText("0.0");
    }



    public void OnotherImageClick(View v){

        final Dialog dialog = new Dialog(MainActivity.this);



        dialog.setContentView(R.layout.custom);
       // dialog.setTitle("Daily Cost Record");

        final EditText editText = (EditText) dialog.findViewById(R.id.editText);

        Button btnSave = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.cancel);





        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    otherTextView.setText(editText.getText().toString());

                    dialog.dismiss();




                //finish();startActivity(getIntent());




            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // finish();startActivity(getIntent());
                dialog.dismiss();


            }
        });
        dialog.show();

    }



    public void OnteaImageClick(View v){

        Double value=Double.parseDouble(teaTextView.getText().toString());
        teaTextView.setText(String.valueOf(value+tea));
    }

    public void OnGoldLeafImageClick(View v){

        Double value=Double.parseDouble(goldLeafTextView.getText().toString());
        goldLeafTextView.setText(String.valueOf(value+goldLeaf));
    }

    public void OnBensonImageClick(View v){

        Double value=Double.parseDouble(bensonPriceTextView.getText().toString());
        bensonPriceTextView.setText(String.valueOf(value+benson));
    }

    public void viewTodaysSpends(View v){

//        Intent intent=new Intent(MainActivity.this,Listlayout.class);
//        startActivity(intent);



         final Dialog dialog = new Dialog(MainActivity.this);



        dialog.setContentView(R.layout.custom2);




        final EditText FromeditText = (EditText) dialog.findViewById(R.id.from_editText);

        final EditText ToeditText = (EditText) dialog.findViewById(R.id.to_editText);

        Button btnSave = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.cancel);




        //totalTextView.setText("Total : "+String.valueOf(total));




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String from,to;
                from=FromeditText.getText().toString();
                to=ToeditText.getText().toString();

                Cursor data = mDatabaseHelper.getData(from,to);

                if(data.moveToNext()) {

                    Intent intent = new Intent(MainActivity.this, Listlayout.class);
                    intent.putExtra("from", from);
                    intent.putExtra("to", to);
                    startActivity(intent);
                }

                else
                    toastMessage("No data Found");


                //finish();startActivity(getIntent());




            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // finish();startActivity(getIntent());
                dialog.dismiss();


            }
        });
        dialog.show();

    }


    public void OntotalButtonClick(View v) {

        final Dialog dialog = new Dialog(MainActivity.this);



        dialog.setContentView(R.layout.custom);




        final EditText editText = (EditText) dialog.findViewById(R.id.editText);

        Button btnSave = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button btnCancel = (Button) dialog.findViewById(R.id.cancel);

        Double b,g,t,o;
        b=Double.parseDouble(bensonPriceTextView.getText().toString());
        g=Double.parseDouble(goldLeafTextView.getText().toString());
        t=Double.parseDouble(teaTextView.getText().toString());
        o=Double.parseDouble(otherTextView.getText().toString());


        total=b+g+t+o;
        //totalTextView.setText("Total : "+String.valueOf(total));

        editText.setText("Total : "+String.valueOf(total));
        editText.setEnabled(false);



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String benson,goldLeaf,tea,other,totalAmount;
                benson=bensonPriceTextView.getText().toString();
                goldLeaf=goldLeafTextView.getText().toString();
                tea=teaTextView.getText().toString();
                totalAmount=String.valueOf(total);
                other=otherTextView.getText().toString();


                boolean result=(mDatabaseHelper.addData(benson,goldLeaf,tea,other,totalAmount,Date));

                if(result){
                    dialog.dismiss();
                    toastMessage("Inserted  Successful : "+Date);

                }

                    //finish();startActivity(getIntent());




            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // finish();startActivity(getIntent());
                dialog.dismiss();


            }
        });
        dialog.show();
    }



    public void deleteAllRecords(View v){

        mDatabaseHelper.deleteAll();
        toastMessage("All RECORDS DELETE SUCCESSFULL");

    }


    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
