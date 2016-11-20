package com.xu.hookmeup;

import android.animation.Animator;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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
import com.xu.hookmeup.Util.Callback;
import com.xu.hookmeup.Util.CascadeAnimator;
import com.xu.hookmeup.Util.Interpolators;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String FB_ACCESS_TOKEN = "EAAJzDEJrRf4BAJLYOZA20DNpyIYCo3CeC0xsY5ka5vnENNuj45N3X9TXSNTvgCjDCO9bin5dwmNQiTyBqpxm1ukEP4b4rUjSeRB9ZCZBdDBV3vzqRDSKsTqZCULYXhK9b3rSkHfgMekGuNpSGQRZCfzlRrHMaLj3Pko9iSMLspTXqt5LsnPOWjYtBlRkrZAoMZD";
    HashMap<String, String> keywords;
    ;
    ArrayList<String> responses = new ArrayList<String>();
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
    List<View> viewsToAnimate = new ArrayList<>();

    ArrayList<String> results;

    private CascadeAnimator cascadeAnimator;

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

        viewsToAnimate.add(button_view);
        viewsToAnimate.add(ring_one);
        viewsToAnimate.add(ring_two);
        viewsToAnimate.add(ring_three);

        cascadeAnimator = new CascadeAnimator(viewsToAnimate, 100.0f, 10.0f, 400.0f, 150.0f);

        recognizeText.setAlpha(0.0f);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                recognizeText.animate().alpha(1.0f).setDuration(300).setInterpolator(Interpolators.FOSIInterpolator).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        cascadeAnimator.animateList(new Callback() {
                            @Override
                            public void execute() {
                                animate(true);
                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            }
        }, 1000);

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listening) {
                    recognizeText.setText("Tap to Listen");
                    listening = false;
                } else {
                    recognizeText.setText("Listening...");
                    startListening();
                    listening = true;
                }
            }
        });
        new GetContacts().execute();
    }

    void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
                .getPackage().getName());

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

                    Intent intent = new Intent(this, EventsActivity.class);
                    startActivity(intent);
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

    //19112016 20:41 Rest of networking ode
    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            //make request to url and get response, MOD THIS CHRIS
            String url = "https://graph.facebook.com/search?q=" + location + "&type=event&access_token=" + FB_ACCESS_TOKEN;
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    //Getting JSON Array node, is this correct node chris????????
                    JSONArray events = jsonObj.getJSONArray("data");

                    //loop through events in node

                    for (int i = 0; i < events.length(); i++) {
                        JSONObject c = events.getJSONObject(i);
                        String id = c.getString("id");//???
                        System.out.println(id);
                        //add in whatever you want here, chris, derived from the object
                        responses.add(id);
                    }

                } catch (final JSONException e) {
                    //DISPLAY ERROR OR LOG
                }

            } else {
                //DISPLAY ERROR OR LOG

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (int i = 0; i < responses.size(); ++i) {
                new GetContactsExtra().execute(responses.get(i));
            }
        }

    }

    private class GetContactsExtra extends AsyncTask<String, Void, Void> {
        ArrayList<GraphResponse> grs = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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
                            grs.add(response);
                        }
                    }
            ).executeAsync();
        }

        @Override
        protected Void doInBackground(String... params) {
            String id = params[0];
            fetchEventDetails(id);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (GraphResponse gr : grs) {
                System.out.println(gr.getRawResponse());
            }
        }

    }

}
