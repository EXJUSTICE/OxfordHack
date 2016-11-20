package com.xu.hookmeup.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.xu.hookmeup.EventActivity;
import com.xu.hookmeup.Model.Event;
import com.xu.hookmeup.R;

import java.util.ArrayList;

/**
 * Created by marcin on 19.11.16.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private final Context context;
    private final ArrayList<Event> items = new ArrayList<>();

    public EventViewAdapter(Context context, ArrayList<Event> toAdd) {
        this.context = context;
        this.items.addAll(toAdd);
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
        holder.infoText.setText(item.category + " • £" + item.price);

        Picasso.with(context).load(item.image).into(holder.image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(context, EventActivity.class);

                intent.putExtra("name", item.name);
                intent.putExtra("image", item.image);
                intent.putExtra("category", item.category);
                intent.putExtra("description", "");

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
