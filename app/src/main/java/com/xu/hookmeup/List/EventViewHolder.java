package com.xu.hookmeup.List;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xu.hookmeup.R;

/**
 * Created by marcin on 19.11.16.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {

    public final TextView nameText;
    public final TextView infoText;

    public final ImageView image;

    public EventViewHolder(View itemView) {
        super(itemView);

        this.nameText = (TextView) itemView.findViewById(R.id.text_name);
        this.infoText = (TextView) itemView.findViewById(R.id.text_info);

        this.image = (ImageView) itemView.findViewById(R.id.image_hero);
    }
}
