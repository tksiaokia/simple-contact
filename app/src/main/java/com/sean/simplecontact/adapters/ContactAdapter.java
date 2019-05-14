package com.sean.simplecontact.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sean.simplecontact.R;
import com.sean.simplecontact.databinding.ContactCellBinding;
import com.sean.simplecontact.models.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>  {
    public interface ContactAdapterCallback{
        void onItemClicked(Contact contact);
    }
    private Context mContext;
    private ArrayList<Contact> contacts;
    private ContactAdapterCallback callback;


    public ContactAdapter(Context mContext,ContactAdapterCallback callback) {
        this.mContext = mContext;
        this.callback = callback;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ContactCellBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.contact_cell,viewGroup,false);
        ContactAdapter.ViewHolder vh = new ContactAdapter.ViewHolder(binding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.clearData();//clear existing binded data
        if(contacts != null && contacts.size() > i){
            viewHolder.bindListing(contacts.get(i));
        }

    }

    @Override
    public int getItemCount() {
        return contacts != null ? contacts.size() : 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ContactCellBinding binding;

        public ViewHolder(ContactCellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindListing(final Contact contact){
            binding.lblName.setText(contact.getDisplayName());

            binding.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(callback != null)
                        callback.onItemClicked(contact);
                }
            });
        }

        public void clearData(){
            binding.lblName.setText("");
        }
    }
}
