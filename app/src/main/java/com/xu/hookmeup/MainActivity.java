package com.xu.hookmeup;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    HashMap<String,String>keywords;
    ArrayList<String> categories;

    EditText input;
    String location;
    Button okbutton;
    String eventId;

    String userinput;
    String[]words;
    int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpDatabase();

        input = (EditText) findViewById(R.id.userInput);
        okbutton = (Button) findViewById(R.id.okbutton);

        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = input.getText().toString().toLowerCase();
                processlanguage();
                performJsonRequest();



            }


        });
    }


    public void processlanguage(){
        count=0;
        userinput = input.getText().toString();
        words= userinput.split("\\s+");
        for(int i=0;i<words.length+1;i++){
            checkword(words[i]);

        }
        //nothing valid inputted, request rephrase

        if (count==0){
            input.setText("I didn't understand, please rephase the request");
        }

    };

    public void checkword (String word){
        if (keywords.get(word)!=null){
            categories.add(keywords.get(word));
            count+=1;
        }
        
    };

    public void performJsonRequest(){


    }

    public void setUpDatabase(){
        keywords = new HashMap<String,String>();
        keywords.put("night out","nightlife");
        keywords.put("coffee","cafe");
        keywords.put("eat out","restaurant");
        keywords.put("restaurant", "restaurant");
        keywords.put("party","nightlife");
        keywords.put("concert", "nightlife");
        keywords.put("lunch","restaurant");
        keywords.put("lecture","lecture");
        keywords.put("museum","culture");
        keywords.put("gallery","culture");
        keywords.put("exhibition","culture");
    }


    public void fetchEventDetails(String event){
        eventId=event;
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
