package com.example.os130004.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements Controler.RefreshState{

    private static final String KEY_CONTROLLER = "com.example.os130004.lab1";

    private Button previous;
    private Button next;

    private Controler controler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.buyer);
        final EditText article = (EditText) findViewById(R.id.article);
        final EditText quantity = (EditText) findViewById(R.id.qty);
        final Button button = (Button) findViewById(R.id.add);
        final ListView listView = (ListView) findViewById(R.id.list);
        final RadioButton buyerButton = (RadioButton) findViewById(R.id.radio_name);
        final RadioButton articleButton = (RadioButton) findViewById(R.id.radio_article);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);

        if (savedInstanceState != null) {
            controler = (Controler) savedInstanceState.get(KEY_CONTROLLER);
            controler.setContext(this);
            button.setEnabled(controler.getButtonEnabled());
            if(controler.getSearchType()) {
                articleButton.setSelected(true);
            } else {
                buyerButton.setSelected(true);
            }
            buyerButton.setSelected(!controler.getSearchType());
            articleButton.setSelected(controler.getSearchType());
        } else {
            controler = new Controler(this);
            buyerButton.setSelected(true);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controler.addOrder(name.getText().toString(), article.getText().toString(), quantity.getText().toString());
                button.setEnabled(false);
            }
        });

        View.OnKeyListener keyListener = new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                button.setEnabled(true);
                return false;
            }
        };

        listView.setAdapter(new ArrayAdapter<>(  this, android.R.layout.simple_list_item_1, controler.current()));
        name.setOnKeyListener(keyListener);
        article.setOnKeyListener(keyListener);
        quantity.setOnKeyListener(keyListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTROLLER, controler);
    }

    public void previous(View view) {
        controler.previous();
    }

    public void next(View view) {
        controler.next();
    }

    public void radioButtonClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.radio_name:
                controler.switchToName();
                break;
            case R.id.radio_article:
                controler.switchToArticle();
                break;
        }
    }

    @Override
    public void changedList(String[] stringArray) {
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(  this, android.R.layout.simple_list_item_1, stringArray));
        listView.refreshDrawableState();
    }

    @Override
    public void disablePrevious() {

    }

    @Override
    public void disableNext() {

    }
}