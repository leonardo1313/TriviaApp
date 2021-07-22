package com.optimahorizonapps.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import com.optimahorizonapps.triviaapp.data.Repository;
import com.optimahorizonapps.triviaapp.databinding.ActivityMainBinding;
import com.optimahorizonapps.triviaapp.model.Question;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 0;
    List<Question> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
        });
        binding.falseButton.setOnClickListener(v -> {
            checkAnswer(false);
        });
    }

    private void checkAnswer(boolean chosenAnswer) {
        boolean answer = questionList.get(currentQuestionIndex).isTrue();
        
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
}