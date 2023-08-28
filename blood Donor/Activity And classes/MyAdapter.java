package gub.app.blooddonor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewholder> {

    Context context;
    ArrayList<Donor> list;

    public MyAdapter(Context context, ArrayList<Donor> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.viewdesign, parent, false);

        return new MyViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewholder holder, int position) {

        Donor donor = list.get(position);
        holder.name.setText(donor.getName());
        holder.mobile.setText(donor.getMobile());
        holder.address.setText(donor.getDonor_address());
        holder.blood_Group.setText(donor.getGroup());
        holder.last_donation.setText(donor.getLast_donation());

    }
    public void filterList(ArrayList<Donor> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        list = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewholder extends RecyclerView.ViewHolder {

        TextView name, mobile, address, blood_Group, last_donation;

        public MyViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_textView);
            mobile = itemView.findViewById(R.id.text_mobile);
            address = itemView.findViewById(R.id.text_address);
            blood_Group = itemView.findViewById(R.id.text_blood_group);
            last_donation = itemView.findViewById(R.id.text_last_Donation);


        }
    }
}
