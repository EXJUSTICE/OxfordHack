package com.xu.hookmeup;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    HashMap<String, String> keywords;

    TextView recognizeText;
    EditText input;
    String location;
    Button okbutton;
    String eventId;

    String userinput;
    String[] words;
    int count;

    View ring_one, ring_two, ring_three, button_view;
    boolean listening = false;

    Animation anim_scale_one, anim_scale_two, anim_scale_three;

    ArrayList<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Hook Me Up");

        recognizeText = (TextView) findViewById(R.id.recognize_text);

        input = (EditText) findViewById(R.id.userInput);
        okbutton = (Button) findViewById(R.id.okbutton);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = input.getText().toString();
                processlanguage();
                performJsonRequest();
            }


        });

        button_view = findViewById(R.id.button_view);

        ring_one = findViewById(R.id.ring_one);
        ring_two = findViewById(R.id.ring_two);
        ring_three = findViewById(R.id.ring_three);

        anim_scale_one = AnimationUtils.loadAnimation(this, R.anim.scale_first);
        anim_scale_two = AnimationUtils.loadAnimation(this, R.anim.scale_second);
        anim_scale_three = AnimationUtils.loadAnimation(this, R.anim.scale_third);

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listening) {
                    recognizeText.setText("Tap to Listen");
                    animate(false);
                    listening = false;
                } else {
                    recognizeText.setText("Listening...");
                    animate(true);
                    startListening();
                    listening = true;
                }
            }
        });
    }

    void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "What are you up to?");

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            //If Voice recognition is successful then it returns RESULT_OK
            if (resultCode == RESULT_OK) {

                ArrayList<String> textMatchList = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (!textMatchList.isEmpty()) {
                    results = textMatchList;
                    recognizeText.setText(results.get(0));
                }

            } else {
                recognizeText.setText("Sorry, we didn't catch that.");
            }

            listening = false;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void animate(boolean a) {
        if (a) {
            ring_one.startAnimation(anim_scale_one);
            ring_two.startAnimation(anim_scale_two);
            ring_three.startAnimation(anim_scale_three);
        } else {
            ring_one.clearAnimation();
            ring_two.clearAnimation();
            ring_three.clearAnimation();
        }
    }

    public void processlanguage() {
        count = 0;
        userinput = input.getText().toString();
        words = userinput.split("\\\\s+");
        for (int i = 0; i < words.length + 1; i++) {
            checkword(i);
            count += 1;
        }
        //nothing valid inputted, request rephrase

        if (count == 0) {
            input.setText("We didn't understand that. Please rephrase the request.");
        }

    }

    ;

    public void checkword(int i) {

    }

    ;

    public void performJsonRequest() {


    }


    public void fetchEventDetails(String event) {
        eventId = event;
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                event,
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                    }
                }
        ).executeAsync();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
