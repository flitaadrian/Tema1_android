package eu.ase.tema1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class StartQuizz extends AppCompatActivity {
    TextView Timer;
    TextView Result;
    ImageView ShowImage;
    HashMap<String, Integer> map = new HashMap<>();
    ArrayList<String> qList = new ArrayList<>();
    int index;
    Button btn1, btn2, btn3, btn4;
    TextView Points;
    int points;
    CountDownTimer countDownTimer;
    long millisUntilFinished;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_quizz);
        Timer = findViewById(R.id.Timer);
        Result = findViewById(R.id.Result);
        ShowImage = findViewById(R.id.Show_Image);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        Points = findViewById(R.id.Points);

        index = 0;

        qList.add("Capacity");
        qList.add("Hour");
        qList.add("EventType");
        qList.add("GreenCertificate");
        qList.add("Penalty");
        map.put(qList.get(0), R.drawable.restaurant);
        map.put(qList.get(1), R.drawable.clock);
        map.put(qList.get(2), R.drawable.eventtype);
        map.put(qList.get(3), R.drawable.greencertificate);
        map.put(qList.get(4), R.drawable.fine);
        Collections.shuffle(qList);
        millisUntilFinished = 20000;
        points = 0;
        startQuizz();
    }

    private void startQuizz() {
        millisUntilFinished = 20000;
        Timer.setText("" + (millisUntilFinished / 2000) + "s");
        Points.setText(points + " / " + qList.size());
        generateQuestions(index);
        countDownTimer=new CountDownTimer(millisUntilFinished,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Timer.setText(""+(millisUntilFinished / 2000) + "s");
            }

            @Override
            public void onFinish() {
                index++;
                if (index > qList.size() - 1) {
                    ShowImage.setVisibility(View.GONE);
                    btn1.setVisibility(View.GONE);
                    btn2.setVisibility(View.GONE);
                    btn3.setVisibility(View.GONE);
                    btn4.setVisibility(View.GONE);
                    Intent intent = new Intent(StartQuizz.this, GameOver.class);
                    intent.putExtra("points", points);
                    startActivity(intent);
                    // Finish StartGame Activity
                    finish();
                } else {
                    countDownTimer = null;
                    startQuizz();
                }
            }
        }.start();
    }

    private void generateQuestions(int index) {
        ArrayList<String> qListTemp = (ArrayList<String>) qList.clone();
        String correctAnswer = qList.get(index);
        qListTemp.remove(correctAnswer);
        Collections.shuffle(qListTemp);
        ArrayList<String> newList = new ArrayList<>();
        newList.add(qListTemp.get(0));
        newList.add(qListTemp.get(1));
        newList.add(qListTemp.get(2));
        newList.add(correctAnswer);
        Collections.shuffle(newList);
        btn1.setText(newList.get(0));
        btn2.setText(newList.get(1));
        btn3.setText(newList.get(2));
        btn4.setText(newList.get(3));
        ShowImage.setImageResource(map.get(qList.get(index)));
    }


    public void nextQuestion(View view) {
        btn1.setBackgroundColor(Color.parseColor("#2196f3"));
        btn2.setBackgroundColor(Color.parseColor("#2196f3"));
        btn3.setBackgroundColor(Color.parseColor("#2196f3"));
        btn4.setBackgroundColor(Color.parseColor("#2196f3"));
        btn1.setEnabled(true);
        btn2.setEnabled(true);
        btn3.setEnabled(true);
        btn4.setEnabled(true);
        countDownTimer.cancel();
        index++;
        if (index > qList.size() - 1){
            ShowImage.setVisibility(View.GONE);
            btn1.setVisibility(View.GONE);
            btn2.setVisibility(View.GONE);
            btn3.setVisibility(View.GONE);
            btn4.setVisibility(View.GONE);
            Intent intent = new Intent(StartQuizz.this, GameOver.class);
            intent.putExtra("points", points);
            startActivity(intent);
            finish();
        } else {
            countDownTimer = null;
            startQuizz();
        }
    }

    public void answerSelected(View view) {
        view.setBackgroundColor(Color.parseColor("#17243e"));
        btn1.setEnabled(false);
        btn2.setEnabled(false);
        btn3.setEnabled(false);
        btn4.setEnabled(false);
        countDownTimer.cancel();
        String answer = ((Button) view).getText().toString().trim();
        String correctAnswer = qList.get(index);
        if(answer.equals(correctAnswer)){
            points++;
            Points.setText(points + " / " + qList.size());
            Result.setText("Congrats");
        } else {
            Result.setText("Wrong");
        }
    }
};


