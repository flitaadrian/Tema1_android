package eu.ase.tema1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startQuizz(View view) {
        Intent intent=new Intent(MainActivity.this, StartQuizz.class);
        startActivity(intent);
        finish();
    }
}