package com.example.roomdatabase02062020.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.interfaces.OnShouldShowForm;
import com.example.roomdatabase02062020.model.entity.WordEnity;
import com.example.roomdatabase02062020.view.adapter.WordAdapter;
import com.example.roomdatabase02062020.viewmodel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnShouldShowForm {


    //View
    TextInputEditText mEdtEn,mEdtVn;
    Button mBtnCancel,mBtnAdd,mBtnOpen;
    CardView mCardForm;
    RecyclerView mRcvWord;
    Spinner mSpinner;
    SwipeRefreshLayout mSwipeRefreshLayout;
    View mViewLoading;

    // Adapter
    WordAdapter mWordAdapter;
    ArrayAdapter mSpinnerAdapter;

    //Array
    List<String> mArrayFilter;

    //View model
    MainViewModel mainViewModel;

    //data
    Boolean mRefreshed = false;
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
        mArrayFilter = new ArrayList<>(Arrays.asList("Show All","Show Forgot","Show Memorized"));
        mWordAdapter = new WordAdapter(new ArrayList<>());
        mSpinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item, mArrayFilter);

    }
    private void mapView() {
        mBtnAdd = findViewById(R.id.buttonAddWord);
        mBtnCancel = findViewById(R.id.buttonCancel);
        mBtnOpen = findViewById(R.id.buttonOpenForm);
        mRcvWord = findViewById(R.id.recyclerview);
        mEdtEn = findViewById(R.id.textinputEn);
        mEdtVn = findViewById(R.id.textinputVn);
        mCardForm = findViewById(R.id.carViewForm);
        mSpinner = findViewById(R.id.spinner);
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mViewLoading = findViewById(R.id.loadingView);

        mSpinner.setAdapter(mSpinnerAdapter);
        mRcvWord.setAdapter(mWordAdapter);

        setDefaultForm();
    }
    private void observer() {
        mainViewModel.getWords().observe(this, wordEniTies  -> {
            mWordAdapter.setAllWord(wordEniTies);
        });
        mainViewModel.getLoading().observe(this, aBoolean -> {
            if (aBoolean){
                mViewLoading.setVisibility(View.VISIBLE);
                setEnableTouchScreen(false);
            }else {
                mViewLoading.setVisibility(View.GONE);
                setEnableTouchScreen(true);
            }
            mRefreshed = aBoolean;
            mSwipeRefreshLayout.setRefreshing(mRefreshed);
        });
    }
    private void event() {
        mBtnOpen.setOnClickListener(view -> toggleForm(true));
        mBtnCancel.setOnClickListener(view -> toggleForm(false));

        // refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.callDataWords();
            }
        });
        mainViewModel.callDataWords();
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
    private void setEnableTouchScreen(Boolean isEnable){
        if(isEnable){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }
}