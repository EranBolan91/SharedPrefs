package com.world.bolandian.sharedprefs;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Note extends AppCompatActivity {

    private TextView tvTitle;
    private Button btnNext,btnBack,btnSave,btnNew;
    private EditText etNote;
    private int countNote = 1,max = 1;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        btnBack = (Button)findViewById(R.id.btnBack);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnSave = (Button)findViewById(R.id.btnSave);
        btnNew = (Button)findViewById(R.id.btnNew);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        etNote = (EditText)findViewById(R.id.etNote);
        prefs = getSharedPreferences("Notes", MODE_PRIVATE);

    }


    public void btnPressed(View view) {
        int id = view.getId();
        switch(id){
            case R.id.btnSave:
                save();
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btnBack:
                btnNext.setVisibility(View.VISIBLE);
                countNote--;
                if(countNote == 1){
                    btnBack.setVisibility(View.INVISIBLE);
                }
                if(countNote< max)
                    btnNew.setVisibility(View.INVISIBLE);
                load(countNote);
                break;

            case R.id.btnNew:
                etNote.setText("");
                btnBack.setVisibility(View.VISIBLE);
                countNote++;
                setMax(countNote,max);
                if(countNote< max)
                    btnNew.setVisibility(View.INVISIBLE);
                tvTitle.setText("Note "+ countNote);
                break;

            case R.id.btnNext:
                btnBack.setVisibility(View.VISIBLE);
                if(countNote == max - 1 ) {
                    btnNext.setVisibility(View.INVISIBLE);
                    btnNew.setVisibility(View.VISIBLE);
                }
                countNote++;
                setMax(countNote,max);
                load(countNote);
                break;
        }
    }

    private void save() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Notes"+countNote,etNote.getText().toString());
        editor.apply();
    }

    private void load(int count) {
        String note = prefs.getString("Notes"+count,"");
        etNote.setText(note);
        tvTitle.setText("Note "+count);
    }

    private void setMax(int count, int maxInc){
        if(count > maxInc)
            max = count;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countNote",countNote);
        outState.putInt("max",max);
        outState.putString("etNote",etNote.getText().toString());

        Log.d("EransNote","onSaveInstaceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        countNote = savedInstanceState.getInt("countNote");
        max = savedInstanceState.getInt("max");
        etNote.setText(savedInstanceState.getString("etNote"));
        Log.d("EransNote","onRestoreInstanceState");
        loadAfterCrash(countNote,max,savedInstanceState.getString("etNote"));
    }

    private void loadAfterCrash(int countNote, int savedMax , String note) {
        etNote.setText(note);
        tvTitle.setText("Note "+countNote);
        max = savedMax;
        if(countNote == 1) {
            btnBack.setVisibility(View.INVISIBLE);

        }else if(countNote == max){
            btnNext.setVisibility(View.INVISIBLE);
            btnNew.setVisibility(View.VISIBLE);
        }
        if(countNote != 1 || countNote != max) {
            btnNext.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
