package com.example.fishgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button retry_button;
    private TextView game_score;
    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        retry_button = (Button) findViewById(R.id.retry_button);
        game_score = (TextView) findViewById(R.id.game_score);

        score = getIntent().getExtras().get("score").toString();

        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(GameOverActivity.this , MainActivity.class);
                startActivity(mainIntent);
            }
        });

        game_score.setText("Score:\n" + score);
    }
}