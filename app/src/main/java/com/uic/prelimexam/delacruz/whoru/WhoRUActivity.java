package com.uic.prelimexam.delacruz.whoru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class WhoRUActivity extends AppCompatActivity {

    Button button_A, button_B, button_stop;
    EditText editText_question;
    TextView textView_progress;

    DatabaseHelper databaseHelper;
    WhoRU whoRU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_ru);

        databaseHelper = new DatabaseHelper(this);
        whoRU = new WhoRU();

        editText_question = (EditText) findViewById(R.id.editText_question);
        textView_progress = (TextView) findViewById(R.id.textView_progress);
        button_A = (Button) findViewById(R.id.button_A);
        button_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserChoice("A");
            }
        });

        button_B = (Button) findViewById(R.id.button_B);
        button_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserChoice("B");
            }
        });

        button_stop = (Button) findViewById(R.id.button_stop);
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WhoRU.currentQuestion = 0;
                startActivity(new Intent(WhoRUActivity.this, MainActivity.class));
            }
        });

        generateWhoRU();
    }

    public void saveUserChoice(String choice){
        whoRU.answers[whoRU.currentQuestion]=choice;
        whoRU.currentQuestion++;
        if(whoRU.currentQuestion>=whoRU.SIZE){
            saveUserData();
        }else{
            generateWhoRU();
        }
    }

    public void generateWhoRU(){
        int currentIndex = whoRU.currentQuestion;
        String currentUsername = uicGetSharedPreferenceValue("userInfo", "username");
        String question = currentUsername + "!\n" + whoRU.getQuestion(currentIndex);
        editText_question.setText(question);
        button_A.setText(whoRU.getChoice(currentIndex,0));
        button_B.setText(whoRU.getChoice(currentIndex,1));
        String progress = (whoRU.currentQuestion+1) + "/" + whoRU.SIZE;
        textView_progress.setText(progress);
    }

    public void saveUserData(){
        String currentUsername = uicGetSharedPreferenceValue("userInfo", "username");
        String traits = generateTraits();

        boolean insertData = databaseHelper.addData(currentUsername, traits);
        if(insertData) {
            startActivity(new Intent(WhoRUActivity.this, ResultActivity.class));
        }else{
            uicToastMessage("Something wrong in your database!");
        }
    }

    public String generateTraits(){
        String traits = "";


        if(whoRU.isExtrovert()) traits+="E"; //Extrovert
        else traits+="I"; // Introvert

        if(whoRU.isSensing()) traits+="S"; //Sensing
        else traits+="N"; //Intuiting

        if(whoRU.isThinking()) traits+="T"; //Thinking
        else traits+="F"; //Feeling

        if(whoRU.isJudging()) traits+="J"; //Judging
        else traits+="P"; //Perceiving

        return traits;
    }

    public String uicGetSharedPreferenceValue(String sharedPrefName, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public void uicToastMessage(String message){
        Toast.makeText(this, message ,Toast.LENGTH_SHORT).show();
    }
}
