package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.ProdSizeModel;
import com.example.param.R;
import com.paramagro.param.interfaces.EventListnerInterface;

import java.util.List;

public class ProdSizeListAdapter extends RecyclerView.Adapter<ProdSizeListAdapter.ViewHolder> {

    Context context;
    List<ProdSizeModel> list;
    private EventListnerInterface eventListnerInterface;

    public ProdSizeListAdapter(Context context, List<ProdSizeModel> list, EventListnerInterface eventListnerInterface) {
        this.context = context;
        this.list = list;
        this.eventListnerInterface = eventListnerInterface;
    }




    //search bar//
    public void setFilteredList(List<ProdSizeModel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProdSizeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_size_layout,parent,false);
        return new ViewHolder(view,eventListnerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdSizeListAdapter.ViewHolder holder, int position) {


        holder.btn.setText(list.get(position).getPrsize());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        Button btn;

        public ViewHolder(@NonNull View itemView,EventListnerInterface eventListnerInterface) {
            super(itemView);

            btn = itemView.findViewById(R.id.size);
           btn.setOnClickListener(new View.OnClickListener() {
               @Override

               public void onClick(View v) {
                   eventListnerInterface.onItemClick(getAdapterPosition());
               }
           });

        }


    }

}

