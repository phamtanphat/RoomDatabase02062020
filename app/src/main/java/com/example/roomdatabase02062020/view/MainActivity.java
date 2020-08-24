package com.example.roomdatabase02062020.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.interfaces.OnItemRemoveListener;
import com.example.roomdatabase02062020.interfaces.OnShouldShowForm;
import com.example.roomdatabase02062020.model.entity.WordEnity;
import com.example.roomdatabase02062020.view.adapter.WordAdapter;
import com.example.roomdatabase02062020.viewmodel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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
    TextInputLayout mTextInputLayoutEn,mTextInputLayoutVn;

    // Adapter
    WordAdapter mWordAdapter;
    ArrayAdapter mSpinnerAdapter;

    //Array
    List<String> mArrayFilter;
    List<WordEnity> mArrayWords;
    List<WordEnity> mArrayWordsFilter;

    //View model
    MainViewModel mainViewModel;

    //data
    int mItemPosition = 0;
    Boolean mLoading = false;
    int mIdSelection = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mapView();
        observer();
        event();

      }

    private void init() {
        mainViewModel = new MainViewModel(this);
        getLifecycle().addObserver(mainViewModel);
        mArrayFilter = new ArrayList<>(Arrays.asList("Show All","Show Forgot","Show Memorized"));
        mArrayWords = new ArrayList<>();
        mArrayWordsFilter = new ArrayList<>();
        mWordAdapter = new WordAdapter(mArrayWords);
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
        mTextInputLayoutEn = findViewById(R.id.textinputlayoutEn);
        mTextInputLayoutVn = findViewById(R.id.textinputlayoutVn);

        mSpinner.setAdapter(mSpinnerAdapter);
        mRcvWord.setAdapter(mWordAdapter);

        setDefaultForm();
    }
    private void observer() {
        mainViewModel.getWords().observe(this, wordEniTies  -> {
            if (mArrayWords.size() > 0){
                mArrayWords.clear();
            }
            mArrayWords.addAll(wordEniTies);
            mWordAdapter.setAllWord(wordEniTies);
        });
        mainViewModel.getLoading().observe(this, aBoolean -> {
            mLoading = aBoolean;
            if (aBoolean){
                mViewLoading.setVisibility(View.VISIBLE);
                setEnableTouchScreen(false);
            }else {
                mViewLoading.setVisibility(View.GONE);
                setEnableTouchScreen(true);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mainViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
        mainViewModel.getResultInsert().observe(this, aBoolean -> {
            if (aBoolean){
                mEdtEn.setText("");
                mEdtVn.setText("");
                Toast.makeText(MainActivity.this, "Thêm dữ liệu thành công", Toast.LENGTH_SHORT).show();
            }
        });
        mainViewModel.getResultUpdate().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    mWordAdapter.notifyItemChanged(mItemPosition);
                    Toast.makeText(MainActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mainViewModel.getResultDelete().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && mWordAdapter.getData().size() > 0){
                    mWordAdapter.getData().remove(mItemPosition);
                    mWordAdapter.notifyItemRemoved(mItemPosition);
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void event() {
        mBtnOpen.setOnClickListener(view -> toggleForm(true));
        mBtnCancel.setOnClickListener(view -> toggleForm(false));

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mIdSelection = i;
                mArrayWordsFilter.clear();
                if (mArrayWords.size() > 0){
                    for (int j = 0; j < mArrayWords.size() ; j++) {
                        if (i == 1 && mArrayWords.get(j).getIsmemorized() == 1){
                            mArrayWordsFilter.add(mArrayWords.get(j));
                        }else if (i == 2 && mArrayWords.get(j).getIsmemorized() == 0){
                            mArrayWordsFilter.add(mArrayWords.get(j));
                        }else if (i == 0 ){
                            mArrayWordsFilter.add(mArrayWords.get(j));
                        }
                    }
                    mWordAdapter.setAllWord(mArrayWordsFilter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // refresh
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.callDataWords();
                mSpinner.setSelection(0,false);

            }
        });
        mBtnAdd.setOnClickListener(view -> {
            hideSoftKeyboard(mEdtVn);
            hideSoftKeyboard(mEdtEn);
            String en = mEdtEn.getText().toString();
            String vn = mEdtVn.getText().toString();

            if (en.length() > 0 && vn.length() > 0){
                if (!en.matches(".*\\d.*") && !vn.matches(".*\\d.*")){
                    setEnableErrorTextInputLayout(false);
                    mainViewModel.saveWord(new WordEnity(en,vn,0));
                }else{
                    setEnableErrorTextInputLayout(true);
                    mTextInputLayoutEn.setError("Không chứa giá trị là số");
                    mTextInputLayoutVn.setError("Không chứa giá trị là số");
                }
            }else{
                Toast.makeText(MainActivity.this, "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });
        mWordAdapter.setOnToggleWord(position -> {
            mItemPosition = position;
            mWordAdapter.getData().get(position).setIsmemorized(mWordAdapter.getData().get(position).getIsmemorized() == 0 ? 1 : 0);
            mainViewModel.updateWord( mWordAdapter.getData().get(position));
        });
        mWordAdapter.setOnRemoveWord(new OnItemRemoveListener() {
            @Override
            public void setOnRemove(int position) {
                mItemPosition = position;
                mainViewModel.deleteWord( mWordAdapter.getData().get(position));
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
    public void setEnableErrorTextInputLayout(boolean isShowError){
        if (!isShowError){
            mTextInputLayoutEn.setErrorEnabled(false);
            mTextInputLayoutEn.setErrorEnabled(false);
        }else{
            mTextInputLayoutEn.setErrorEnabled(true);
            mTextInputLayoutEn.setErrorEnabled(true);
        }
    }
    private void hideSoftKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }
}