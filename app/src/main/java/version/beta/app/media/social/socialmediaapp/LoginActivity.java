package version.beta.app.media.social.socialmediaapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;

    private ProgressBar loginProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(LoginActivity.this);

        emailEditText       = findViewById(R.id.editText2);
        passwordEditText    = findViewById(R.id.editText3);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users");

        loginProgressBar    = findViewById(R.id.progressBar);
        loginProgressBar.setVisibility(View.INVISIBLE);

        loginButton         = findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginProgressBar.setVisibility(View.VISIBLE);

                final String email  = emailEditText.getText().toString(),
                       password     = passwordEditText.getText().toString();

                Query query = databaseReference.orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        boolean isExist = false;

                        for (DataSnapshot data : dataSnapshot.getChildren()){
                            if (data.child("password").getValue().equals(password)){
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                loginProgressBar.setVisibility(View.INVISIBLE);
                                isExist = true;
                                finish();
                            }
                        }

                        if (!isExist){
                            Toast.makeText(getApplicationContext(), "Account not exist", Toast.LENGTH_SHORT).show();

                            loginProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

        registerButton      = findViewById(R.id.button2);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                finish();
            }
        });
    }
}
