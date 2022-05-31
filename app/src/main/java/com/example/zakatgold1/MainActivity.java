package com.example.zakatgold1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    EditText calcWgt, calcCurrentGold;
    Button calcButton,resButton;
    TextView output,output2,output3;
    RadioGroup radioButton;
    int checkgroup;

    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calcWgt = (EditText) findViewById(R.id.calcWgt);
        calcCurrentGold = (EditText) findViewById(R.id.calcCurrentGold);
        calcButton = (Button) findViewById(R.id.calcButton);
        resButton=(Button)findViewById(R.id.resButton);
        output = (TextView) findViewById((R.id.output));
        output2=(TextView)findViewById((R.id.output2));
        output3=(TextView)findViewById((R.id.output3));
        resButton.setOnClickListener(this);
        calcButton.setOnClickListener(this);
        radioButton = (RadioGroup) findViewById(R.id.radioButton);


        //SAVE THE DATA
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);

        float weight1=prefs.getFloat("Weight",0);
        calcWgt.setText(""+weight1);

        float value2=prefs.getFloat("Value",0);
        calcCurrentGold.setText(""+value2);




    }

    //MENU CODE
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //HANDLING THE MENU CODE
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "This is home page", Toast.LENGTH_LONG).show();
                break;

            case R.id.about:
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "This is about page", Toast.LENGTH_LONG).show();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {

        switch (v.getId()) {

            case R.id.calcButton:

                try
                {
                    float weight,value;
                    weight=Float.parseFloat(calcWgt.getText().toString());
                    value = Float.parseFloat(calcCurrentGold.getText().toString());

                    double totalValueGold=weight*value;
                    output.setText("Total value of the gold:RM "+totalValueGold); //for output total value of the gold


                    RadioButton keep=(RadioButton) findViewById(R.id.keep);
                    int selectedId = radioButton.getCheckedRadioButtonId();

                    double minus;  //calculation for gold weight
                    if(keep.isChecked())
                    {
                        minus = weight - 85;
                    }
                    else
                    {
                        minus = weight - 200;
                    }
                    if (selectedId == -1)
                    {
                        Toast.makeText(MainActivity.this, "Please select type of gold",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                 

                    double currValue = Float.parseFloat(calcCurrentGold.getText().toString()); //calculation for zakat payable
                    double  zktPayable= minus*currValue;




                    if(zktPayable>=0)
                    {
                        output2.setText("Zakat Payable:RM "+zktPayable); //calculation for output zakat payable
                        double totalZkt=zktPayable*0.025;
                        output3.setText("Total Zakat:RM "+totalZkt);  //calculation for output total zakat

                    }
                    else
                    {
                        double newPayable=zktPayable-zktPayable;        //for output if zakat payable get -ve
                        output2.setText("Zakat Payable:RM "+newPayable);
                        double totalZkt=newPayable*0.025;
                        output3.setText("Total Zakat:RM "+totalZkt);  //for output total zakat if zakat payble get -ve
                    }

                    SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putFloat("Weight",weight);

                    editor.putFloat("Value",value);
                    editor.apply();


                }
                catch (java.lang.NumberFormatException nfe)
                {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();

                }
                catch (Exception exp)
                {
                    Toast.makeText(this, "Unknown exception" + exp.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("Exception", exp.getMessage());
                }
                break;


            case R.id.resButton:
                calcWgt.getText().clear();
                calcCurrentGold.getText().clear();
                output.setText("");
                output2.setText("");
                output3.setText("");
                radioButton.clearCheck();
                break;

        }

    }



}