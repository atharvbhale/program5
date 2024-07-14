package com.example.program5;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    EditText editText1;
    ClipboardManager myClipboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link those objects with their respective id's that we have given in .XML file
        editText = findViewById(R.id.contextEditText1);
        editText1 = findViewById(R.id.contextEditText2);

        // Register context menu for both EditText views
        registerForContextMenu(editText);
        registerForContextMenu(editText1);

        // Initialize ClipboardManager
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // Set menu header title
        menu.setHeaderTitle("Choose an action");

        // Add menu items
        menu.add(0, v.getId(), 0, "Copy");
        menu.add(0, v.getId(), 0, "Paste");
        menu.add(0, v.getId(), 0, "Cut");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        View view = findViewById(item.getItemId());
        int id = view.getId();
        if (id == R.id.contextEditText1 || id == R.id.contextEditText2) {
            switch (item.getTitle().toString()) {
                case "Copy":
                    copyText((EditText) view);
                    return true;
                case "Paste":
                    pasteText((EditText) view);
                    return true;
                case "Cut":
                    cutText((EditText) view);
                    return true;
                default:
                    return super.onContextItemSelected(item);
            }
        }
        return super.onContextItemSelected(item);
    }

    private void copyText(EditText editText) {
        ClipData clip = ClipData.newPlainText("text", editText.getText().toString());
        myClipboard.setPrimaryClip(clip);
    }

    private void pasteText(EditText editText) {
        ClipData clip = myClipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            ClipData.Item item = clip.getItemAt(0);
            editText.setText(item.getText().toString());
        }
    }

    private void cutText(EditText editText) {
        ClipData clip = ClipData.newPlainText("text", editText.getText().toString());
        editText.setText("");
        myClipboard.setPrimaryClip(clip);
    }
}
