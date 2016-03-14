package com.example.simpleui.simpleui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    ListView listView;
    CheckBox hideCheckBox;
    Spinner spinner;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);
        listView = (ListView)findViewById(R.id.listView);
        spinner = (Spinner)findViewById(R.id.spinner);

        setListView();
        setSpinner();

        sp = getSharedPreferences("setting", Context.MODE_PRIVATE);
        editor = sp.edit();

        editText.setText(sp.getString("editText",""));

        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                editor.putString("editText", editText.getText().toString());
                editor.apply();
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    submit(v);
                    return true;
                }
                return false;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submit(v);
                    return true;
                }

                return false;
            }
        });

        hideCheckBox = (CheckBox)findViewById(R.id.checkBox);

        hideCheckBox.setChecked(sp.getBoolean("hideCheckBox",false));

        hideCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("hideCheckBox", hideCheckBox.isChecked());
                editor.apply();
            }
        });

    }

    public  void submit(View view)
    {
        String text = editText.getText().toString();
        Utils.writeFile(this, "Class0310.txt", text + '\n');
        if (hideCheckBox.isChecked())
        {
            Toast.makeText(this,text,Toast.LENGTH_LONG).show();
            textView.setText("**********");
            editText.setText("**********");
            return;
        }
        editText.setText("");
        textView.setText(text);
        setListView();
    }

    private void setListView()
    {
        //String[] data = {"1","2","3","4","1","2","3","4","1","2","3","4"};
        String[] data = Utils.readFile(this, "Class0310.txt").split("\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);   //
        listView.setAdapter(adapter);
    }
    private void setSpinner()
    {
        //String[] data = {"1","2","3","4","5"};
        String[] data = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, data);   //
        spinner.setAdapter(adapter);
    }
}
