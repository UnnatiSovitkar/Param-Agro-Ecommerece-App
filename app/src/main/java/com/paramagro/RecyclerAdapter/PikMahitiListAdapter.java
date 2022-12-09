package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.PikmahitiModel;
import com.example.param.R;
import com.paramagro.param.VegetablesDetails;
import com.bumptech.glide.Glide;

import java.util.List;

public class PikMahitiListAdapter extends RecyclerView.Adapter<PikMahitiListAdapter.ViewHolder> {

    Context context;
    List<PikmahitiModel> list;

    public PikMahitiListAdapter(Context context, List<PikmahitiModel> list) {
        this.context = context;
        this.list = list;
    }

    //search bar//
    public void setFilteredList(List<PikmahitiModel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PikMahitiListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_pikmahiti_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PikMahitiListAdapter.ViewHolder holder, int position) {

        holder.txtpiktitle.setText(list.get(position).getName());
//        holder.txtpikimg.setImageResource(list.get(position).getImg());
        String i=list.get(position).getId();
        String n=list.get(position).getName();
        String d=list.get(position).getDesc();
        String img=list.get(position).getImg();




        Glide.with(context)
                .load(list.get(position).getImg())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.txtpikimg);

        holder.txtly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(v.getContext(), VegetablesDetails.class);
                in.putExtra("id",i);
                in.putExtra("name",n);
                in.putExtra("desc",d);
                in.putExtra("img",img);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(in);

            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtpiktitle;
        ImageView txtpikimg;
        LinearLayout txtly;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtpiktitle = itemView.findViewById(R.id.vegtitle);
            txtpikimg = itemView.findViewById(R.id.vegimg);
            txtly=itemView.findViewById(R.id.linlay);


        }
    }
}

