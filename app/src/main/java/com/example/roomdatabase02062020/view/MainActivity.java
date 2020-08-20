package com.example.roomdatabase02062020.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.interfaces.OnShouldShowForm;
import com.example.roomdatabase02062020.viewmodel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity implements OnShouldShowForm {

    MainViewModel mainViewModel;
    TextInputEditText mEdtEn,mEdtVn;
    Button mBtnCancel,mBtnAdd,mBtnOpen;
    CardView mCardForm;
    RecyclerView mRcvWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mapView();
        observer();
        event();

        // observer

        // data words
//        mainViewModel.getWords().observe(this, wordEnities  -> {
//            Log.d("BBB",wordEnities.toString());
//        });

        // data id insert
//        mainViewModel.getInsertRowId().observe(this, new Observer<Long>() {
//            @Override
//            public void onChanged(Long aLong) {
//                Toast.makeText(MainActivity.this, aLong + "", Toast.LENGTH_SHORT).show();
//            }
//        });
//         data errors
//        mainViewModel.getError().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Log.d("BBB",s);
//            }
//        });

        // event
        //select all
//        mainViewModel.callDataWords();
        //insert
//        mainViewModel.saveWord(new WordEnity("Three","Ba",0));
        //update
//        mainViewModel.updateWord(wordEnities.get(0));
        // delete
//        mainViewModel.deleteWord(mArrayWords.get(0));
      }

    private void init() {
        mainViewModel = new MainViewModel(this);
        getLifecycle().addObserver(mainViewModel);

    }
    private void mapView() {
        mBtnAdd = findViewById(R.id.buttonAddWord);
        mBtnCancel = findViewById(R.id.buttonCancel);
        mBtnOpen = findViewById(R.id.buttonOpenForm);
        mRcvWord = findViewById(R.id.recyclerview);
        mEdtEn = findViewById(R.id.textinputEn);
        mEdtVn = findViewById(R.id.textinputVn);
        mCardForm = findViewById(R.id.carViewForm);
        setDefaultForm();
    }
    private void observer() {

    }
    private void event() {
        mBtnOpen.setOnClickListener(view -> toggleForm(true));
        mBtnCancel.setOnClickListener(view -> toggleForm(false));
    }

    @Override
    public void toggleForm(Boolean shouldShowForm) {
        if (shouldShowForm){
            mCardForm.setVisibility(View.VISIBLE);
            mBtnOpen.setVisibility(View.GONE);
        }else{
            mCardForm.setVisibility(View.GONE);
            mBtnOpen.setVisibility(View.VISIBLE);
        }
    }
    private void setDefaultForm(){
        mCardForm.setVisibility(View.GONE);
        mBtnOpen.setVisibility(View.VISIBLE);
    }
}