package com.codepath.simpletodo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.simpletodo.R;

/**
 * Created by sarun on 9/27/16.
 */
public class EditDialogFragment extends DialogFragment {

    EditText mEditText;
    Button saveBtn;
    int position;

    public interface EditItemListener {
        void onSaveItemDialog(String editItem, int pos);
    }

    public EditDialogFragment() {

    }

    public static EditDialogFragment newInstance( int pos, String text) {
        EditDialogFragment frag = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("pos", pos);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_edit_item, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.editText4);
        mEditText.setText(getArguments().get("text").toString());
        position = Integer.parseInt(getArguments().get("pos").toString());
        saveBtn = (Button) view.findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditItemListener listener = (EditItemListener) getActivity();
                listener.onSaveItemDialog(mEditText.getText().toString(), position);
                dismiss();
            }
        });
        getDialog().setTitle("Edit the item below");
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }



}
