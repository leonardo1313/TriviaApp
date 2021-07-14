package com.optimahorizonapps.triviaapp.data;

import com.optimahorizonapps.triviaapp.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {

    void processFinished(ArrayList<Question> questionArrayList);
}
