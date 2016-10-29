package com.example.os130004.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
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

        final EditText buyer = (EditText) findViewById(R.id.buyer);
        final EditText article = (EditText) findViewById(R.id.article);
        final EditText quantity = (EditText) findViewById(R.id.qty);

        addButton = (Button) findViewById(R.id.add);
        buyerButton = (RadioButton) findViewById(R.id.radio_buyer);
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
            addButton.setEnabled(controller.buttonAddState());
        } else {
            controller = new Controller(this);
            addButton.setEnabled(false);
        }


        previous.setEnabled(controller.previousButtonState());
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.previous();
            }
        });
        next.setEnabled(controller.nextButtonState());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.next();
            }
        });

        buyerButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    controller.switchToBuyer();
                }
            }
        });

        articleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controller.switchToArticle();
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.addOrder(buyer.getText().toString(), article.getText().toString(), Integer.parseInt(quantity.getText().toString()));
                controller.disableButtonAdd();
                addButton.setEnabled(false);
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

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
        buyer.addTextChangedListener(textWatcher);
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

    @Override
    public void refresh() {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, controller.current()));
        previous.setEnabled(controller.previousButtonState());
        next.setEnabled(controller.nextButtonState());
        buyerButton.setChecked(!controller.searchType());
        articleButton.setChecked(controller.searchType());
    }
}
