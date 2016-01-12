package com.example.whhsfbla.fashionnow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Sudharshan on 1/9/2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private List<Post> postList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView vUsername, vTitle;
        protected ProgressBar vProgressBar;
        protected ParseImageView vPicture;
        protected Context context;

        public ViewHolder(View v) {
            super(v);
            vUsername = (TextView) v.findViewById(R.id.txtUsername);
            //vProgressBar = (ProgressBar) v.findViewById(R.id.imgLoad);
            vPicture = (ParseImageView) v.findViewById(R.id.picture);
            vTitle = (TextView) v.findViewById(R.id.txtTitle);
            context = v.getContext();
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CardAdapter(List<Post> posts) {
        postList = posts;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mTextView.setText(mDataset[position]);

        //TODO fix picture display

        Post p = postList.get(position);

        holder.vUsername.setText(p.username);
        holder.vTitle.setText(p.title);
        holder.vPicture.setParseFile(p.img);
        holder.vPicture.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
               // holder.vProgressBar.setVisibility(View.INVISIBLE);
                holder.vPicture.setVisibility(View.VISIBLE);
            }
        });
        /*String imageUrl = p.img.getUrl();
        Uri imageUri = Uri.parse(imageUrl);

        InputStream imageStream = null;
        try {
            imageStream = p.img.getDataStream();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);



        Picasso.with(holder.context).load(imageUri.toString()).into(holder.vPicture, new Callback() {
            @Override
            public void onSuccess() {
                holder.vProgressBar.setVisibility(View.INVISIBLE);
                holder.vPicture.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
                holder.vProgressBar.setVisibility(View.VISIBLE);
                holder.vProgressBar.setVisibility(View.INVISIBLE);
            }
        });*/



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList.size();
    }
}