package com.fastkites.marksolutionsdashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class PasswordKeyboardAdapter extends BaseAdapter {
    Context context;
    List<String> keys;
    EditText pinDigitsEditText;
    String pin = "";
    OnItemClick onItemClick;
    ProgressDialog progressDialog;

    public PasswordKeyboardAdapter(Context context, OnItemClick onItemClick, List<String> keys, EditText pinDigitsEditText, ProgressDialog progressDialog) {
        this.context = context;
        this.keys = keys;
        this.pinDigitsEditText = pinDigitsEditText;
        this.onItemClick = onItemClick;
        this.progressDialog = progressDialog;
    }

    @Override
    public int getCount() {
        return keys.size();
    }

    @Override
    public Object getItem(int position) {
        return keys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView button;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            button = (TextView) inflater.inflate(R.layout.item_keyboard_key, parent, false);
        } else {
            button = (TextView) convertView;
        }
        String key = keys.get(position);
        button.setText(key);
        button.setOnClickListener(v -> {
            // Handle key press event
            if (key.equals(">")) {
                if (pin.length() > 5) {
                    progressDialog.show();
                    onItemClick.itemClickListener(pin);
                    pin = "";
                }
            } else if (key.equals("x")) {
                if (pin.length() > 0) {
                    pin = pin.substring(0, pin.length() - 1);
                }
            } else {
                if (pin.length() < 6) {
                    pin += key;

                }
            }
            updatePinDigits(pin);
        });
        return button;
    }

    public interface OnItemClick {
        void itemClickListener(String pin);
    }

    private void updatePinDigits(String pin) {
        StringBuilder dummy = new StringBuilder();
        for (int i = 0; i < pin.length(); i++) {
            dummy.append("*");
        }
        pinDigitsEditText.setText(dummy);
    }
}
