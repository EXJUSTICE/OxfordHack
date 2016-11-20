package com.xu.hookmeup;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.squareup.picasso.Picasso;
import com.xu.hookmeup.Util.Callback;
import com.xu.hookmeup.Util.CascadeAnimator;

/**
 * Created by marcin on 20.11.16.
 */

public class EventActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView textName, textCategory, textContent;
    ImageView image;

    private CascadeAnimator cascadeAnimator;

    LinearLayout nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_event);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        image = (ImageView) findViewById(R.id.image_hero);

        Picasso.with(this)
                .load(getIntent().getStringExtra("image"))
                .into(image);

        textName = (TextView) findViewById(R.id.text_name);
        textCategory = (TextView) findViewById(R.id.text_category);
        textContent = (TextView) findViewById(R.id.text_content);

        textName.setText(getIntent().getStringExtra("name"));
        textCategory.setText(getIntent().getStringExtra("category"));
        //textContent.setText(getIntent().getStringExtra("description"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        nestedScrollView = (LinearLayout) findViewById(R.id.container_scroll);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cascadeAnimator = new CascadeAnimator(nestedScrollView, 100.0f, 50.0f, 200.0f, 150.0f);
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    cascadeAnimator.animate();
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }

            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cascadeAnimator.revert(new Callback() {
                        @Override
                        public void execute() {
                            supportFinishAfterTransition();
                        }
                    });
                } else {
                    supportFinishAfterTransition();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
