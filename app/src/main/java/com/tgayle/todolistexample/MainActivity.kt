package com.tgayle.todolistexample

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    // Create a special object that holds a list of Strings that Android can observe and watch the
    // changes of.
    val todos = MutableLiveData<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Give the todos an initial value with no items.
        todos.value = ArrayList()


        // In Java, we had to use findViewById to get access to the views. Kotlin has a feature
        // called 'synthetics' that let you access views as if they were normal variables.
//        val input = findViewById<EditText>(R.id.inputTextBox)
//        val addTodoButton = findViewById<Button>(R.id.addTodoButton)
//        val todoContainer = findViewById<LinearLayout>(R.id.todoContainer)

        // Tell the activity to listen to changes to our todos.
        todos.observe(this, Observer { listOfTodos ->
            // Prefer Log.d, Log.e, and Log.i instead of System.out.println on Android.
            Log.d("Todos", listOfTodos.toString())
            // Remove all the todos currently on the screen.
            todoContainer.removeAllViews()

            // Go through each string in the list and add it to the container we made.
            // Kotlin offers many functions to simplify common Java code. Instead of a normal
            // for-loop, we can use this forEachIndexed which gives us the current index and the
            // current item at once.
            listOfTodos.forEachIndexed { index, todo ->
                // Create a TextView with a context. A 'context' contains special information about
                // the current activity such as themes, styles, colors, and information about the
                // system.
                val thisTodo = TextView(this)

                // Kotlin automatically uses the real getters and setters when you access them
                // like this.
                // $ in a is 'string interpolation' which automatically inserts the variable in the string.
                thisTodo.text = "$index $todo"

                // Add the new TextView to the screen.
                todoContainer.addView(thisTodo)
            }
        })

        // Set a listener on the "Add" button that will be called whenever the button is pressed.
        addTodoButton.setOnClickListener {
            // Grab the current list of todos.
            val currentTodos = todos.value!!
            // Add the item the user just made to the list.
            currentTodos.add(inputTextBox.text.toString())
            // Use setValue here so Android knows to alert the activity that the list changed.
            todos.setValue(currentTodos)
            // Clear the input box where the user typed.
            inputTextBox.setText("")
        }
    }
}