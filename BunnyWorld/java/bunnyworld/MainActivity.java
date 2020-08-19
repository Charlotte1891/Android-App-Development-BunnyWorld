/*
 * We have classes: GameManager -> Game -> Page -> Shape
 */
package edu.stanford.cs108.bunnyworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class MainActivity extends AppCompatActivity {

    GameManager game_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enterEdit(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);
        // TODO: programmably change name

    }
}
