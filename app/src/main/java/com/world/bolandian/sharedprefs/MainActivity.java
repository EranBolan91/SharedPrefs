package com.world.bolandian.sharedprefs;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnLoginListener, TextWatcher {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private EditText etNote;
    private SharedPreferences prefs;

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Ness","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Ness","onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Ness","onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Ness","onStart");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("score",score);
        Log.d("Ness","onSaveInstaceState");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Ness","onCreate");
        if(savedInstanceState == null){
            //The real start
            //getSupportFragmentManager().beginTransaction().replace(..,new LoginFragment.OnLoginListener());
        }

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        etNote = (EditText)findViewById(R.id.etNote);
        etNote.addTextChangedListener(this);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("note", MODE_PRIVATE);
        load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_settings:
                return true;
            case R.id.action_login:
                new LoginFragment().show(getSupportFragmentManager(),"userLogin");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLogin(String name, boolean isLoggedIn) {
        if(isLoggedIn)
            Snackbar.make(fab,name+ " Logged in",Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
                save();
    }

    private void save() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("note",etNote.getText().toString());
        editor.apply();
    }


    private void load() {
        String note = prefs.getString("note","");
        etNote.setText(note);
    }


    @Override
    public void afterTextChanged(Editable s) {

    }

    private int score = 0 ;

    public void increment(View view){
        score++;
        Toast.makeText(this, "Score "+score, Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        score = savedInstanceState.getInt("score");
        Log.d("Ness","onRestoreInstanceState");
    }
}
