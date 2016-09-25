package com.codepath.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Activity to handle the editing of items
 */
public class EditItemActivity extends AppCompatActivity {
    String text;
    int pos;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        editItem();
    }

    /**
     * Edits the item
     */
    public void editItem() {
        text = getIntent().getStringExtra("text");
        pos = getIntent().getIntExtra("position",0);
        etEditText = (EditText) findViewById(R.id.editText);
        etEditText.setText(text);
        etEditText.setSelection(text.length());
    }

    /**
     * Handler to put appropriate parameters to the parent activity
     * @param view View parameter
     */
    public void onSubmit(View view) {
        etEditText = (EditText) findViewById(R.id.editText);
        Intent data = new Intent();
        data.putExtra("Edit", etEditText.getText().toString());
        data.putExtra("position", pos);
        setResult(RESULT_OK, data); // set result code and bundle data for response

        this.finish();
    }
}
