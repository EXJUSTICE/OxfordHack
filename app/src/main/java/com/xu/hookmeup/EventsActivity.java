package com.xu.hookmeup;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.xu.hookmeup.Divider.DividerDecoration;
import com.xu.hookmeup.List.EventViewAdapter;
import com.xu.hookmeup.Model.Event;

import java.util.ArrayList;

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

        getSupportActionBar().setTitle("Events foy You");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_events);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Event ev1 = new Event();
        ev1.name = "Coke in Camden";
        ev1.category = "Party";
        ev1.price = 70;
        ev1.image = "http://drugsdetails.com/wp-content/uploads/2016/06/Cocaine.jpg";

        Event ev2 = new Event();
        ev2.name = "Trip to Minsk";
        ev2.category = "Entertainment";
        ev2.price = 5;
        ev2.image = "http://media.mcclatchydc.com/static/features/Chernobyl30/photos/Chernobyl-Part1_03.JPG";

        Event ev3 = new Event();
        ev3.name = "Friday at Koko";
        ev3.category = "Party";
        ev3.price = 0;
        ev3.image = "https://www.residentadvisor.net/images/clubs/lg/koko.jpg";

        items.add(ev1);
        items.add(ev2);
        items.add(ev3);

        mAdapter = new EventViewAdapter(this, items);

        mRecyclerView.addItemDecoration(new DividerDecoration((int) getResources().getDimension(R.dimen.list_divider_height)));
        mRecyclerView.setAdapter(mAdapter);
    }
}
