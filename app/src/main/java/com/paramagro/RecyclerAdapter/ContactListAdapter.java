package com.paramagro.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paramagro.RecyclerModelClass.ContactListModel;
import com.example.param.R;

import java.util.List;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {


    Context context;
    List<ContactListModel> list;

    public ContactListAdapter(Context context, List<ContactListModel> list) {
        this.context = context;
        this.list = list;

    }

    //search bar//
    public void setFilteredList(List<ContactListModel> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycler_item_contactlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.mobno.setText(list.get(position).getMobno());

        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,mobno;
        Button invite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.frndname);
            mobno=itemView.findViewById(R.id.frndnumber);
            invite = itemView.findViewById(R.id.send);



        }

    }
}

