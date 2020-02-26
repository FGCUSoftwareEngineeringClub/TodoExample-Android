package com.tgayle.todolistexample;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MutableLiveData<List<String>> todos = new MutableLiveData<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        todos.setValue(new ArrayList<>());

        final EditText input = findViewById(R.id.editText);
        Button addTodoButton = findViewById(R.id.button);
        LinearLayout todoContainer = findViewById(R.id.todoContainer);

        todos.observe(this, todos -> {
            Log.d("Todos", todos.toString());

            todoContainer.removeAllViews();

            for (int i = 0; i < todos.size(); i++) {
                String todo = todos.get(i);
                TextView thisTodo = new TextView(this);
                thisTodo.setText(i + " " + todo);
                todoContainer.addView(thisTodo);
            }
        });

        addTodoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> currentTodos = todos.getValue();
                currentTodos.add(input.getText().toString());
                todos.setValue(currentTodos);
                input.setText("");
            }
        });
    }
}