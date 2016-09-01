package com.example.andrea.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

   private View gameLayout; //this is the game screen
   private TextView expression; //aka the problem you are trying to solve
   private Button[] buttons; //this is an array of the possible answer choices
   private int correctAnswerLocation; //this is the location in the array of the correct
   private TextView scoreText;
   private int numCorrect;
   private int numGamesPlayed;
   private TextView timerText;
   private CountDownTimer timer;
   private View title;
   private int[] answers; // this is the array of all possible answers
   private int maxNum;
   private TextView prevScore;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      title = findViewById(R.id.titleLayout);
      prevScore = (TextView) findViewById(R.id.previousScore);
      gameLayout = findViewById(R.id.gameLayout);
      expression = (TextView) findViewById(R.id.expression);
      buttons = new Button[4];
      buttons[0] = (Button) findViewById(R.id.button0);
      buttons[1] = (Button) findViewById(R.id.button1);
      buttons[2] = (Button) findViewById(R.id.button2);
      buttons[3] = (Button) findViewById(R.id.button3);
      scoreText = (TextView) findViewById(R.id.score);
      numCorrect = 0;
      numGamesPlayed = 0;
      answers = new int[4];
      timerText = (TextView) findViewById(R.id.timer);
      maxNum = 11;
      prepareNextRound(maxNum);

   }

   private void createCountDownTimer() {
      timer = new CountDownTimer(30100, 1000) {
         @Override
         public void onTick(long millisUntilFinished) {
            long secUntilFinished = millisUntilFinished / 1000;
            String newTime = Long.toString(secUntilFinished)+ "s";
            if (secUntilFinished < 10)
            {
               newTime = "0" + newTime;
            }
            timerText.setText(newTime);
         }

         @Override
         public void onFinish() {
            numGamesPlayed++;
            String newScore = String.valueOf(numCorrect) + "/" + String.valueOf(numGamesPlayed);
            scoreText.setText(newScore);
            maxNum--;
            prepareNextRound(maxNum);
            Toast.makeText(getApplicationContext(),"Wrong!", Toast.LENGTH_SHORT).show();
            createCountDownTimer();
         }
      };
      timer.start();
   }

   public void start(View view) {
      //starts the game
      title.setVisibility(View.INVISIBLE);
      gameLayout.setVisibility(View.VISIBLE);
      createCountDownTimer();
   }

   public void stop(View view)
   {
      timer.cancel();
      gameLayout.setVisibility(View.INVISIBLE);
      title.setVisibility(View.VISIBLE);
      String previous = "Previous Score: " + scoreText.getText().toString();
      prevScore.setText(previous);
      prevScore.setVisibility(View.VISIBLE);

   }
   public void chooseAnswer(View view) {
      int tag = Integer.parseInt(view.getTag().toString());
      timer.cancel();
      if (tag == correctAnswerLocation) {
         numCorrect++;
         Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT).show();
         maxNum++;
      }
      else {
         Toast.makeText(this,"Wrong!", Toast.LENGTH_SHORT).show();
         maxNum--;
      }
      numGamesPlayed++;
      String newScore = String.valueOf(numCorrect) + "/" + String.valueOf(numGamesPlayed);
      scoreText.setText(newScore);
      prepareNextRound(maxNum);
      createCountDownTimer();

   }


   private void prepareNextRound(int maxNum) {
      Random rand = new Random();
      MathExpression mathExpression = new MathExpression(maxNum);
      int answer = mathExpression.getAnswer();
      expression.setText(mathExpression.getExpression());
      correctAnswerLocation = rand.nextInt(4);
      for (int i=0; i<4; i++) {
         if (i == correctAnswerLocation) {
            answers[i] = answer;
         }
         else {
            int maxSquared = (maxNum - 1) * (maxNum - 1);
            answers[i] = rand.nextInt(maxSquared);
            while (answers[i] == answer) {
               answers[i] = rand.nextInt(maxSquared);
            }
         }
         buttons[i].setText(String.valueOf(answers[i]));
      }
   }



}

