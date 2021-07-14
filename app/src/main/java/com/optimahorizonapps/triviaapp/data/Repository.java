package com.optimahorizonapps.triviaapp.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.optimahorizonapps.triviaapp.controller.AppController;
import com.optimahorizonapps.triviaapp.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {

                        try {
                            Question question = new Question(response.getJSONArray(i).get(0).toString(),
                                    response.getJSONArray(i).getBoolean(1));
//                            Log.d("REPO", "onCreate: " + response.getJSONArray(i).get(0));
//                            Log.d("REPO", "onCreate: " + response.getJSONArray(i).getBoolean(1));

                            questionArrayList.add(question);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (callBack != null) callBack.processFinished(questionArrayList);


                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
