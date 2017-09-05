package shivang.onetouch;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import Object_Structure.NYTimes_Object;
import Remote_API.NYTimes_API;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private ArrayList<NYTimes_Object> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<NYTimes_Object> itemList) {
        Log.v("LL Resp : ", "RecyclerViewAdapter");
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        Log.v("LL Resp : ", "onCreateViewHolder");
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        Log.v("LL Resp : ", "Bind");
        holder.Image.setImageBitmap(itemList.get(position).getBitmap_Image());
        holder.Title.setText(itemList.get(position).getTitle());
        holder.Content.setText(itemList.get(position).getContent());
        //Link Button
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse(itemList.get(position).getLink()));
                myWebLink.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Context ctx=MainActivity.getContext();
                ctx.startActivity(myWebLink);
            }
        });

        holder.Author.setText("by " + itemList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


}