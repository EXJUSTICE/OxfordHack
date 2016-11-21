package com.xu.hookmeup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.xu.hookmeup.Divider.DividerDecoration;
import com.xu.hookmeup.List.EventViewAdapter;
import com.xu.hookmeup.Model.Event;
import com.xu.hookmeup.Model.FbPicture;
import com.xu.hookmeup.Service.FbService;
import com.xu.hookmeup.Service.MahService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Functions;
import rx.schedulers.Schedulers;

/**
 * Created by marcin on 19.11.16.
 */

public class EventsActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private EventViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final ArrayList<Event> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("place")) {
            getSupportActionBar().setTitle("Events in " + getIntent().getStringExtra("place"));
        } else {
            getSupportActionBar().setTitle("Events for You");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_events);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MahService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        MahService mahService = retrofit.create(MahService.class);

        Map<String, String> params = new HashMap<>();
        params.put("event_type", "party");
        params.put("city", getIntent().getStringExtra("place").trim());
        params.put("start_time", "2014-04-23T04:30:45+0000");
        params.put("end_time", "2014-04-23T04:30:45+0000");

        Observable<Event> items = mahService.getEvents(params).flatMap(new Func1<List<Event>, Observable<Event>>() {
            @Override
            public Observable<Event> call(List<Event> events) {
                return Observable.from(events);
            }
        });

        /*Event ev1 = new Event();
        ev1.name = "OxfordHack 2016";
        ev1.category = "Hackathon";
        ev1.price = 0;
        ev1.time = "Now";
        ev1.description = "Official OxfordHack 2016 Hackathon event for the MLH EU 2016 season. Watch this space for owls with information on applications, sponsors, venue information, and more!";
        ev1.image = "https://scontent-lhr3-1.xx.fbcdn.net/v/t1.0-9/13728959_225449104521228_5107270690249952713_n.png?oh=a80a4f01765064d7bb2a54f9824b1821&oe=58BEF7E9";

        Event ev2 = new Event();
        ev2.name = "Magic Party (free party) + Afterparty";
        ev2.category = "PArty";
        ev2.price = 0;
        ev2.time = "In 2 days";
        ev2.description = ""
        ev2.image = "https://scontent-lhr3-1.xx.fbcdn.net/t31.0-8/14859782_1319055078106799_1791814184592811886_o.jpg";

        Event ev3 = new Event();
        ev3.name = "Friday at Koko";
        ev3.category = "Party";
        ev3.price = 0;
        ev3.image = "https://www.residentadvisor.net/images/clubs/lg/koko.jpg";

        items.add(ev1);
        items.add(ev2);
        items.add(ev3);*/

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl(FbService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        FbService fbService = retrofit2.create(FbService.class);

        mAdapter = new EventViewAdapter(this, fbService, items);

        mRecyclerView.addItemDecoration(new DividerDecoration((int) getResources().getDimension(R.dimen.list_divider_height)));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
