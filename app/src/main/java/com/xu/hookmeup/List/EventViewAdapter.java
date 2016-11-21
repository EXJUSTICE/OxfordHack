package com.xu.hookmeup.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xu.hookmeup.EventActivity;
import com.xu.hookmeup.Model.Event;
import com.xu.hookmeup.Model.FbPicture;
import com.xu.hookmeup.R;
import com.xu.hookmeup.Service.FbService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by marcin on 19.11.16.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final Context context;
    private final ArrayList<Event> items = new ArrayList<>();

    private final FbService fbService;

    public EventViewAdapter(Context context, FbService fbService, Observable<Event> observable) {
        this.context = context;
        this.fbService = fbService;

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Event event) {
                        items.add(event);

                        EventViewAdapter.this.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventViewHolder holder, int position) {
        final Event item = items.get(position);

        holder.nameText.setText(item.name);
        holder.infoText.setText(item.getPlace().getName());

        //Map<String, String> params = new HashMap<>();
        //params.put("access_token", "EAAJzDEJrRf4BADSY86YGZBStUx276PXvMKZBKrPjuzGjoCuh7crLZALXyLhvAIUor1Y8tbFOiHI58K6HzFOA6lsfXhIiGbiRJe0jCve28eIcZA8SMdMfTHHS3rq4i5UyRtBPIm2ZBQ60XB919oKfTZCIwJoFb9LPYaB9GV0Ww2QwZDZD");

        /*fbService.getPicture(item.id, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Obs", e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(Response picture) {

                    }
                });*/

        Picasso.with(context).load("https://scontent-lhr3-1.xx.fbcdn.net/v/t1.0-9/13728959_225449104521228_5107270690249952713_n.png?oh=a80a4f01765064d7bb2a54f9824b1821&oe=58BEF7E9").into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(context, EventActivity.class);

                intent.putExtra("name", item.name);
                intent.putExtra("image", "https://scontent-lhr3-1.xx.fbcdn.net/v/t1.0-9/13728959_225449104521228_5107270690249952713_n.png?oh=a80a4f01765064d7bb2a54f9824b1821&oe=58BEF7E9");
                intent.putExtra("category", item.getPlace().getName());
                intent.putExtra("street", item.getStreet());
                intent.putExtra("description", item.getDescription());

                final ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, holder.image, "item");

                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
