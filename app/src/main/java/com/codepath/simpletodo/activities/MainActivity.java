package com.codepath.simpletodo.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.simpletodo.CustomListAdapter;
import com.codepath.simpletodo.R;
import com.codepath.simpletodo.fragments.EditDialogFragment;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main activity class for the To do app
 */
public class MainActivity extends AppCompatActivity implements EditDialogFragment.EditItemListener {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvlItems;
    private final int REQUEST_CODE = 20;
    EditText etNewItem;

    /**
     * Initialize and setting up listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateItems();
        lvlItems = (ListView)findViewById(R.id.lvlItems);
        lvlItems.setAdapter(itemsAdapter);
        etNewItem = (EditText) findViewById(R.id.etNewItem);
        setupListViewListener();
    }

    @Override
    public void onSaveItemDialog(String inputText, int pos) {
        items.remove(pos);
        items.add(pos, inputText);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
    }


    /**
     * Read from file and initialize the adapter
     */
    public void populateItems() {
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
    }

    /**
     * Write the item back in the correct position
     * @param resultCode result code
     * @param requestCode request code sent from sub activity
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getExtras().getString("Edit");
            int position = data.getExtras().getInt("position");
            items.remove(position);
            items.add(position, name);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }



    /**
     * Actions performed while adding an item
     * @param v View parameter
     */
    protected void onAddItem(View v) {
        String editText = etNewItem.getText().toString();
        itemsAdapter.add(editText);
        etNewItem.setText("");
        writeItems();
    }

    /**
     * List view listener which has implementations for long click and on click
     */
    private void setupListViewListener() {
        lvlItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    public boolean onItemLongClick(AdapterView<?> adapterView,
                                                   View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
        lvlItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapterView,
                                            View item, int pos, long id) {
                        showEditDialog(pos);
                    }
                }
        );
    }

    private void showEditDialog(int pos) {
        FragmentManager fm = getSupportFragmentManager();
        EditDialogFragment editNameDialog = EditDialogFragment.newInstance( pos, items.get(pos));
        editNameDialog.show(fm, "fragment_edit_item");
    }

    /**
     * Read items from a todo file
     */
    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException ioe) {
            items = new ArrayList<String>();
        }
    }

    /**
     * Write items to the todofile
     */
    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
