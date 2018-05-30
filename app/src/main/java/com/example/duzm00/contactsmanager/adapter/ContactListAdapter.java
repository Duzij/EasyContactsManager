package com.example.duzm00.contactsmanager.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duzm00.contactsmanager.R;
import com.example.duzm00.contactsmanager.model.Contact;
import com.example.duzm00.contactsmanager.screen.ContactListFragment;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by duzm00 on 29.05.2018.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private List<Contact> contacts;
    public OnContactItemInteracted listener;

    public interface OnContactItemInteracted{
        void onContactClicked(Contact contact);
    }


    public ContactListAdapter()
    {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.onBind(contact);
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }

        void onBind(final Contact contact){
            TextView title = itemView.findViewById(R.id.title);
            TextView subtitle = itemView.findViewById(R.id.subtitile);
            TextView desc = itemView.findViewById(R.id.description);

            title.setText(contact.getFirstName() + " "+  contact.getLastName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactClicked(contact);
                }
            });

        }
    }
}
