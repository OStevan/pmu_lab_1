package com.example.os130004.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity implements Controller.Refreshable {

    private static final String KEY_CONTROLLER = "com.example.os130004.lab1";

    private Button previous;
    private Button next;
    private Button addButton;
    private ListView listView;
    private RadioButton buyerButton;
    private RadioButton articleButton;

    private Controller controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText name = (EditText) findViewById(R.id.buyer);
        final EditText article = (EditText) findViewById(R.id.article);
        final EditText quantity = (EditText) findViewById(R.id.qty);

        addButton = (Button) findViewById(R.id.add);
        buyerButton = (RadioButton) findViewById(R.id.radio_name);
        articleButton = (RadioButton) findViewById(R.id.radio_article);

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);

        listView = (ListView) findViewById(R.id.list);

        if (savedInstanceState != null) {

            controller = (Controller) savedInstanceState.get(KEY_CONTROLLER);
            assert controller != null;
            controller.setRefreshable(this);

            addButton.setEnabled(controller.buttonAddState());

            buyerButton.setChecked(!controller.searchType());
            articleButton.setChecked(controller.searchType());

        } else {
            controller = new Controller(this);
        }

        addButton.setEnabled(controller.buttonAddState());

        previous.setEnabled(controller.previousButtonState());
        next.setEnabled(controller.nextButtonState());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.addOrder(name.getText().toString(), article.getText().toString(), Integer.parseInt(quantity.getText().toString()));
                controller.disableButtonAdd();
                addButton.setEnabled(false);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.enableButtonAdd();
                addButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.current()));
        name.addTextChangedListener(textWatcher);
        article.addTextChangedListener(textWatcher);
        quantity.addTextChangedListener(textWatcher);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CONTROLLER, controller);
    }

    public void previous(View view) {
        controller.previous();
    }

    public void next(View view) {
        controller.next();
    }

    public void radioButtonClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.radio_name:
                controller.switchToName();
                break;
            case R.id.radio_article:
                controller.switchToArticle();
                break;
        }
    }

    @Override
    public void refresh() {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.current()));
        previous.setEnabled(controller.previousButtonState());
        next.setEnabled(controller.nextButtonState());
        buyerButton.setChecked(!controller.searchType());
        articleButton.setChecked(controller.searchType());
    }
}
