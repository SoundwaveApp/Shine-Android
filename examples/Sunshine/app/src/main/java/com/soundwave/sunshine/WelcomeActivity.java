package com.soundwave.sunshine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.soundwave.sunshine.model.User;

public class WelcomeActivity extends AppCompatActivity {

    public static final String USER = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        User user = getIntent().getParcelableExtra(USER);

        TextView firstName = (TextView) findViewById(R.id.first_name);
        firstName.setText(user.getFirstName() + "!");
    }
}
