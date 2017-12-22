package com.uic.prelimexam.delacruz.whoru;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    public Button btnStart, btnAboutMe;
    EditText editText_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAboutMe = (Button) findViewById(R.id.btnAboutMe);
        btnAboutMe.setEnabled(true);
        btnAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Button Clicked");
                //uicSetSharedPreferenceValue("userInfo", "username", editText_username.getText().toString());
                startActivity(new Intent(MainActivity.this, AboutMe.class));
            }
        });


        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setEnabled(true);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uicSetSharedPreferenceValue("userInfo", "username", editText_user.getText().toString());
                startActivity(new Intent(MainActivity.this, WhoRUActivity.class));
            }
        });

        editText_user = (EditText) findViewById(R.id.editText_user);
        editText_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { /**/ }

            @Override
            public void afterTextChanged(Editable editable) { /**/ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>1){
                    btnStart.setEnabled(true);
                }
            }
        });

        if(uicGetSharedPreferenceValue("userInfo", "username").isEmpty()){
            btnStart.setEnabled(false);
        }
    }


    public void uicSetSharedPreferenceValue(String sharedPrefName, String key, String value){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String uicGetSharedPreferenceValue(String sharedPrefName, String key){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, "");
        return value;
    }



}
