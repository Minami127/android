package com.minami.postingapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.minami.postingapp.R;
import com.minami.postingapp.model.Posting;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class PostingAdapter extends RecyclerView.Adapter<PostingAdapter.ViewHolder>{

    ImageView imgPhoto;
    TextView txtContent;
    TextView txtEmail;
    TextView txtCreatedAt;
    ImageView imgLike;

    Context context;
    ArrayList<Posting> postingArrayList;

    public PostingAdapter(Context context, ArrayList<Posting> PostingArrayList) {
        this.context = context;
        this.postingArrayList = PostingArrayList;

        // 서버의 시간(글로벌 표준시)을, 로컬의 시간으로 변환해야한다,
        SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat df= new SimpleDateFormat("yyyy년MM월dd일 HH:mm");
        sf.setTimeZone(TimeZone.getTimeZone("UTC"));
        df.setTimeZone(TimeZone.getDefault());

    }

    @NonNull
    @Override
    public PostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_row, parent, false);
        return new PostingAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(Posting.imgUrl).into(holder.img);

        holder.txtContent.setText( Posting.content);
        holder.txtEmail.setText( Posting.email);


        try {
            Date date = sf.parse(posting.createdAt);
            String localTime = df.format(date);
            holder.txtCreatedAt.setText(localTime);
        }catch (ParseException e){
            throw new RuntimeException(e);
        }
        if(posting.isLike){

        }


    }

    @Override
    public int getItemCount() {
        return postingArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            imgLike = itemView.findViewById(R.id.imgLike);
        }
    }

}
