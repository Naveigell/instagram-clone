package version.beta.app.media.social.socialmediaapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<PostList> postListArrayList;
    private RecyclerView recyclerView;
    private PostRecycleView postRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_home);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setTitle("SocialMediaApp");
        toolbar.setTitleTextAppearance(this, R.style.FontFamilyMonserratBlack);
        setSupportActionBar(toolbar);

        postListArrayList = new ArrayList<>();
        postListArrayList.add(new PostList());
        postListArrayList.add(new PostList());
        postListArrayList.add(new PostList());
        postListArrayList.add(new PostList());

        postRecycleView = new PostRecycleView(getApplicationContext(), postListArrayList);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(postRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar_menu, menu);
        return true;
    }

}
