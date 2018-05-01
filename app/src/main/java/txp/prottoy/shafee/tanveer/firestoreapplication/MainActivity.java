package txp.prottoy.shafee.tanveer.firestoreapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AddDialogFragment.OnFragmentInteractionListener {
    private static final String TAG = "main", CONTACTS = "contacts", NAME = "name", NUMBER = "number";
    private FirebaseFirestore firestore;
    private ArrayList<Contact> contacts;
    private ContactListAdapter contactListAdapter;
    private Map<String, Object> contactMap;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        getAll();
    }

    @Override
    public void addData(final String[] values) {
        contactMap = new HashMap<>();
        contactMap.put(NAME, values[0]);
        contactMap.put(NUMBER, values[1]);
        firestore.collection(CONTACTS).add(contactMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                contact = new Contact();
                contact.setName(values[0]);
                contact.setNumber(values[1]);
                contacts.add(contact);
                contactListAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }

    private void initialize() {
        firestore = FirebaseFirestore.getInstance();
        contacts = new ArrayList<>();
        contactListAdapter = new ContactListAdapter(contacts);
        RecyclerView recyclerView = findViewById(R.id.main_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(contactListAdapter);
        findViewById(R.id.main_fb).setOnClickListener(addButtonOcl);
    }

    private void getAll() {
        firestore.collection(CONTACTS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    for(QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        contactMap = document.getData();
                        contact = new Contact();
                        contact.setName((String) contactMap.get(NAME));
                        contact.setNumber((String) contactMap.get(NUMBER));
                        contacts.add(contact);
                        contactListAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    private View.OnClickListener addButtonOcl = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AddDialogFragment.newInstance().show(MainActivity.this.getSupportFragmentManager(), "AddDialogFragment");
        }
    };
}
