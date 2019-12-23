package version.beta.app.media.social.socialmediaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchPeopleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchPeopleEditText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_people);

        final ArrayList<People> peopleArrayList = new ArrayList<>();

        recyclerView            = findViewById(R.id.recyclerView);
        searchPeopleEditText    = findViewById(R.id.editText4);

        firebaseDatabase        = FirebaseDatabase.getInstance();
        databaseReference       = firebaseDatabase.getReference("users");

        final RecyclerView.Adapter adapter = new RecyclerView.Adapter<Holder>() {

            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.find_people_layout, viewGroup, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SearchPeopleActivity.this, PeopleProfileActivity.class));
                    }
                });

                return new Holder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int i) {
                holder.usernameTextView.setText(peopleArrayList.get(i).getUsername());
                holder.usernameTextView1.setText("@" + peopleArrayList.get(i).getUsername());
            }

            @Override
            public int getItemCount() {
                return peopleArrayList.size();
            }
        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        searchPeopleEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
                        String usernameString   = searchPeopleEditText.getText().toString();

                        Query query = databaseReference.orderByChild("username")
                                                       .startAt(usernameString)
                                                       .endAt(usernameString + "\uf8ff")
                                                       .limitToFirst(50);

                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                peopleArrayList.clear();
                                for (DataSnapshot data : dataSnapshot.getChildren()){
                                    peopleArrayList.add(new People(data.child("username").getValue().toString()));
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        return true;
                    }
                }

                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private class Holder extends RecyclerView.ViewHolder {

        private TextView usernameTextView, usernameTextView1;

        private Holder(View itemView) {
            super(itemView);

            usernameTextView        = itemView.findViewById(R.id.textView13);
            usernameTextView1       = itemView.findViewById(R.id.textView17);
        }
    }

    private class People {
        private String username;

        public People(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
