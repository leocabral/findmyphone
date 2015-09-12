package br.com.findme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupUI();
    }

    private void setupToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        mToolbar.setTitle(getString(R.string.app_name));
    }

    private void setupUI() {
        final Button saveButton = (Button) findViewById(R.id.save_button);
        final EditText editText = (EditText) findViewById(R.id.message);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editText.getText().toString();

                if (message.trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.empty_message_warning, Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences sharedPref = MainActivity.this.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                sharedPref.edit().putString(getString(R.string.preference_message_key), message).commit();
                Toast.makeText(MainActivity.this, R.string.message_saved, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
