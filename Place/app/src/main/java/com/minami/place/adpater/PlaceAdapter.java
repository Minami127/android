package com.minami.place.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.minami.place.MapActivity;
import com.minami.place.model.Place;

import java.util.ArrayList;

import com.minami.place.R;
import com.minami.place.model.PlaceList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    Context context;
    ArrayList<Place> placeArrayList;

    public PlaceAdapter(Context context, ArrayList<Place> placeArrayList) {
        this.context = context;
        this.placeArrayList = placeArrayList;
    }

    @NonNull
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_row, parent, false);
        return new PlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeArrayList.get(position);

        if(place.name == null){
            holder.txtName.setText("상점명 없음");
        }else {
            holder.txtName.setText( place.name );
        }

        if(place.vicinity == null){
            holder.txtVicinity.setText( "주소 없음" );
        } else {
            holder.txtVicinity.setText( place.vicinity );
        }
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtName;
        TextView txtVicinity;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtVicinity = itemView.findViewById(R.id.txtVicinity);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    Place place =placeArrayList.get(index);
                    // place 정보를 맵 액티비티에 넘겨주면된다
                    Intent intent =new Intent(context, MapActivity.class);
                    intent.putExtra("place", place);
                    context.startActivity(intent);
                }
            });
        }
    }
}

