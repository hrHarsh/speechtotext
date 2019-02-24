package com.example.android.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextToSpeech t1;
    EditText ed1;
    Button b1,b2;
    static final int SPEECH_REQUEST_CODE=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.btnSpeak);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }
    public void buttonclick(View view){
        EditText e=(EditText)findViewById(R.id.editText);
        speech();
    }

    private void speech() {
        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");
        try {
            startActivityForResult(i,100);
        }
        catch (ActivityNotFoundException a){
            Toast.makeText(MainActivity.this,"Device not supported",Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code,int resultcode,Intent i){
        super.onActivityResult(request_code,resultcode,i);
        switch (request_code){
            case 100:if (resultcode==RESULT_OK&& i!=null){
                ArrayList<String> result=i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                ed1.setText(result.get(0));
            }break;
        }
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }


}
