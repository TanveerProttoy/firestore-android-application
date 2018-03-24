package txp.prottoy.shafee.tanveer.firestoreapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tanveer on 3/24/18.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {
    private ArrayList<Contact> contacts;

    public ContactListAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    class ContactListViewHolder extends RecyclerView.ViewHolder {
        private TextView rowName, rowNumber;

        ContactListViewHolder(View view) {
            super(view);
            rowName = view.findViewById(R.id.row_contact_name);
            rowNumber = view.findViewById(R.id.row_contact_number);
        }
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactListViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.row_contact_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.rowName.setText(contact.getName());
        holder.rowNumber.setText(contact.getNumber());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
