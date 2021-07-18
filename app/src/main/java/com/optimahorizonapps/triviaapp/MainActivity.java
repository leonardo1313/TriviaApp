package com.optimahorizonapps.triviaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.optimahorizonapps.triviaapp.controller.AppController;
import com.optimahorizonapps.triviaapp.data.AnswerListAsyncResponse;
import com.optimahorizonapps.triviaapp.data.Repository;
import com.optimahorizonapps.triviaapp.databinding.ActivityMainBinding;
import com.optimahorizonapps.triviaapp.model.Question;

import org.json.JSONArray;

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

        questionList = new Repository().getQuestions(questionArrayList ->
                        binding.questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer()));

        binding.nextButton.setOnClickListener(v -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        });

        binding.trueButton.setOnClickListener(v -> {

        });
        binding.falseButton.setOnClickListener(v -> {

        });
    }
}