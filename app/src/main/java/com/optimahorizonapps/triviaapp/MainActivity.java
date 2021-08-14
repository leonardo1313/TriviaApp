 package com.optimahorizonapps.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.snackbar.Snackbar;
import com.optimahorizonapps.triviaapp.data.Repository;
import com.optimahorizonapps.triviaapp.databinding.ActivityMainBinding;
import com.optimahorizonapps.triviaapp.model.Question;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;
    private int scoreCounter = 0;
    private Score score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        score = new Score();

        questionList = new Repository().getQuestions(questionArrayList -> {
                    binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                    updateCounter(questionArrayList);
        });


        binding.nextButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();
        });

        binding.trueButton.setOnClickListener(v -> {
            checkAnswer(true);
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();
        });
        binding.falseButton.setOnClickListener(v -> {
            checkAnswer(false);
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
            updateQuestion();
        });

    }

    private void checkAnswer(boolean chosenAnswer) {
        boolean answer = questionList.get(currentQuestionIndex).isTrue();
        int snackMessageId;
        if (chosenAnswer == answer) {
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
            addScore();

        } else {
            snackMessageId = R.string.wrong_answer;
            shakeAnimation();
            deductScore();
        }
        Snackbar.make(binding.questionCardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateCounter(ArrayList<Question> questionArrayList) {
        binding.questionNumberTextView.setText(String.format(getString(R.string.questionCount_text),
                currentQuestionIndex + 1, questionArrayList.size()));
    }

    private void updateQuestion() {
        String question = questionList.get(currentQuestionIndex).getAnswer();
        binding.questionTextView.setText(question);
        updateCounter((ArrayList<Question>) questionList);
    }

    private void shakeAnimation() {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake_animation);
        binding.questionCardView.setAnimation(shake );
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionCardView.setCardBackgroundColor(getResources().getColor(R.color.red));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionCardView.setCardBackgroundColor(getResources().getColor(R.color.card_color));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void fadeAnimation() {
        AlphaAnimation fade = new AlphaAnimation(1f, 0f);
        fade.setDuration(500);
        fade.setRepeatCount(1);
        fade.setRepeatMode(Animation.REVERSE);

        binding.questionCardView.setAnimation(fade);

        fade.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionCardView.setCardBackgroundColor(getResources().getColor(R.color.green));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionCardView.setCardBackgroundColor(getResources().getColor(R.color.card_color));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void addScore() {
        scoreCounter++;
        score.setScore(scoreCounter);
        binding.scoreTextView.setText(getResources().getString(R.string.score_text) + " " + score.getScore());
    }
    private void deductScore() {
        if(scoreCounter > 0) {
            scoreCounter--;
        }
        score.setScore(scoreCounter);
        binding.scoreTextView.setText(getResources().getString(R.string.score_text) + " " + score.getScore());
    }


}