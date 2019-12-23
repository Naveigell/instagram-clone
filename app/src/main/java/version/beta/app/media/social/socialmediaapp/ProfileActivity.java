package version.beta.app.media.social.socialmediaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private Button sendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Context context = getApplicationContext();

        sendMessageButton = findViewById(R.id.button4);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                final View view = getLayoutInflater().inflate(R.layout.send_message_layout, null);
                alertDialogBuilder.setView(view);

                alertDialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText message = view.findViewById(R.id.editText);
                        Toast.makeText(context, message.getText().toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                alertDialogBuilder.create().show();
            }
        });
    }
}
