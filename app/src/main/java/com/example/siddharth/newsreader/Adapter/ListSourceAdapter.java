package com.example.siddharth.newsreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.siddharth.newsreader.Comman.Comman;
import com.example.siddharth.newsreader.Interface.IconBetterIdeaService;
import com.example.siddharth.newsreader.Interface.ItemClickListener;
import com.example.siddharth.newsreader.ListNews;
import com.example.siddharth.newsreader.Model.IconBetterIdea;
import com.example.siddharth.newsreader.Model.WebSite;
import com.example.siddharth.newsreader.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Siddharth on 18-12-2017.
 */

class ListSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ItemClickListener itemClickListener;
    TextView Source_title;
    CircleImageView Source_image;

    public ListSourceViewHolder(View itemView) {
        super(itemView);
        Source_image= (CircleImageView) itemView.findViewById(R.id.source_image);
        Source_title= (TextView) itemView.findViewById(R.id.source_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}


public class ListSourceAdapter extends RecyclerView.Adapter<ListSourceViewHolder> {
    private Context context;
    private WebSite webSite;
    private IconBetterIdeaService mService;


    public ListSourceAdapter(Context context, WebSite webSite) {
        this.context = context;
        this.webSite = webSite;
        mService= Comman.getIconService();
    }

    @Override
    public ListSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.source_layout,parent,false);
        return new ListSourceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ListSourceViewHolder holder, int position) {
     StringBuilder iconBetterAPI=new StringBuilder(" https://icons.better-idea.org/allicons.json?url=");
        iconBetterAPI.append(webSite.getSources().get(position).getUrl());
        mService.getIconUrl(iconBetterAPI.toString()).enqueue(new Callback<IconBetterIdea>() {
            @Override
            public void onResponse(Call<IconBetterIdea> call, Response<IconBetterIdea> response) {
                if(response.body().getIcons().size() >0)
                {

                    Picasso.with(context).load(response.body().getIcons().get(0).getUrl()).into(holder.Source_image);
                }
            }

            @Override
            public void onFailure(Call<IconBetterIdea> call, Throwable t) {

            }
        });
        holder.Source_title.setText(webSite.getSources().get(position).getName());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position, boolean isLongClick) {
                  Intent intent=new Intent(context, ListNews.class);
                intent.putExtra("source",webSite.getSources().get(position).getId());
                intent.putExtra("sortBy",webSite.getSources().get(position).getSortBysAvailable().get(0));//get detail soet by method
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return webSite.getSources().size();
    }
}
