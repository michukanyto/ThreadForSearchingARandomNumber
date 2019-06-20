package com.appsmontreal.threadrandomnumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String BACK_STACK = "BACK STACK" ;
    private static final String SEARCH_FRAGMENT = "SEARCH FRAGMENT";
    EditText sizeEditTextView;
    EditText searchEditText;
    Button generateButton;
    Button searchButton;
    Random randomNumber;
    List<Integer> numbers;
    private static List<Integer> index;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sizeEditTextView = findViewById(R.id.sizeEditText);
        searchEditText = findViewById(R.id.searchEditText);
        randomNumber = new Random();
        connectButtons();

    }
    public void connectButtons(){
        generateButton = findViewById(R.id.generateButton);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numbers = new ArrayList<>();
                for (int n = 0; n < Integer.valueOf(sizeEditTextView.getText().toString()); n++){
                    numbers.add(randomNumber.nextInt(1000));
                }
                Log.d(TAG, "Array: " + numbers);
            }
        });


        searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExampleRunnable exampleRunnable = new ExampleRunnable();
                new Thread(exampleRunnable).start();
                SearchFragment searchFragment = new SearchFragment();
                addFragment(R.id.resultFrameLayout,searchFragment, SEARCH_FRAGMENT);
            }
        });
    }


    private void addFragment(int containerId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.setCustomAnimations(R.anim.from_left, R.anim.from_left);
        fragmentTransaction.add(containerId, fragment, tag);
        fragmentTransaction.addToBackStack(BACK_STACK);
        fragmentTransaction.commit();
    }

    public class ExampleRunnable implements Runnable{
        private static final String TAG = "ExampleRunnable";


        @Override
        public void run() {
            index = new ArrayList<>();
            for(int n = 0; n < numbers.size(); n++){
                if(Integer.valueOf(searchEditText.getText().toString()) == numbers.get(n)){
                    index.add(n);
                    Log.i(TAG, "run: " + n);
                }
            }
        }


    }

    public static List<Integer> getIndex() {
        return index;
    }


}
