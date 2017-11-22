package com.example.usmanhussain.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TodoActivity extends AppCompatActivity {

    String[] mTodos; // array of to do items
    int mTodoIndex = 0; // position index
    String TAG = "TodoActivity"; // logger
    String TODO_INDEX = "todoIndex"; // bundle key value pairs

// In case of state change, due to rotating the phone
// store the mTodoIndex to display the same mTodos element post state change
// N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
// the alternative is to abstract view data to a ViewModel which can be in scope in all
// Activity states and more suitable for larger amounts of data

    // override to write the value of mTodoIndex into the Bundle with TODO_INDEX as its key
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(TODO_INDEX, mTodoIndex);     // passes the mtodoindex value to the main method. Overwrites the main method to avoid the onStop
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // check for saved state due to changes such as rotation or back button and restore any saved state such as the to do index
        if (savedInstanceState != null){
            mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);

        // sends the following message to the logger
        Log.d(TAG, " *** Just to say the PC is in onCreate!");

        // instantiates the view from the xml file
        setContentView(R.layout.activity_todo);

        // initialize member TextView so we can manipulate it later
        final TextView TodoTextView;
        TodoTextView = (TextView) findViewById(R.id.textViewTodo);

        // read the array of to do values from res/values/strings.xml
        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);

        // display the first task from mTodo array in the TodoTextView
        TodoTextView.setText(mTodos[mTodoIndex]);

        Button buttonNext;
        buttonNext = (Button) findViewById(R.id.buttonNext); // finds the button from the xml file and sets this to the button object

        Button buttonPrev;
        buttonPrev = (Button) findViewById(R.id.buttonPrev); // finds the button from the xml file and sets this to the button object

        // OnClick listener for the Next button
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {                          // waits for onclick
                mTodoIndex = (mTodoIndex +1) % mTodos.length; // calculates the value of mTodoIndex

                // if mtodoindex + 1 (array value is always calculated from 0 hence +1) diving by number of items in the array.
                // the remainder is the value currently on and stores this in the mtodoindex value

                TodoTextView.setText(mTodos[mTodoIndex]);              // setting the to do item on screen
            }
        });
        // OnClick listener for the Prev button
        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {                   // waits for onclick
                if (--mTodoIndex < 0) {                     // calculates mtodoindex-1 first then checks if this is below 0
                    mTodoIndex = mTodos.length-1;          // if it is below 0, it sets it to the index value of the last item in the array
                                                           // and displays this on the display
                }
                TodoTextView.setText(mTodos[mTodoIndex]);     // setting the to do item on screen
            }
        });
    }
}