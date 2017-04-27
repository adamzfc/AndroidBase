package com.adamzfc.androidbase.test.sqlite;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.adamzfc.androidbase.R;

import java.util.List;

/**
 * Created by adamzfc on 4/27/17.
 */

public class TestDbActivity extends Activity {
    PostsDatabaseHelper mDatabaseHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_db);
        mDatabaseHelper = PostsDatabaseHelper.getInstance(this);
        findViewById(R.id.bt_create_db).setOnClickListener(v -> createDb());
        findViewById(R.id.bt_get_data).setOnClickListener(v -> getData());
    }

    private void getData() {
        List<Post> posts = mDatabaseHelper.getAllPosts();
        for (Post post : posts) {
            Log.d("TestDbActivity", post.toString());
        }
    }

    private void createDb() {
        // Create sample data
        User sampleUser = new User();
        sampleUser.userName = "Steph";
        sampleUser.profilePictureUrl = "https://i.imgur.com/tGbaZCY.jpg";

        Post samplePost = new Post();
        samplePost.user = sampleUser;
        samplePost.text = "Won won";
        mDatabaseHelper.addPost(samplePost);
    }
}
