package e.hp.button;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

import androidx.appcompat.app.AppCompatActivity;

public class noteEditorActivity extends AppCompatActivity {
int noteId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText= (EditText) findViewById(R.id.editText);
        Intent intent = getIntent();
         noteId = intent.getIntExtra("noteId",-1);
if(noteId != -1){
    editText.setText(MainActivity.notes.get((noteId)) );
        }else {
    MainActivity.notes.add("");
    noteId =MainActivity.notes.size()-1;
    MainActivity.arrayAdapter.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence , int start, int before, int count) {
MainActivity.notes.set(noteId, String.valueOf( charSequence));
           MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=
                        getApplicationContext().getSharedPreferences(
                            "e.hp.button", Context.MODE_PRIVATE
                        );


                HashSet<String> set= new HashSet(MainActivity.notes);
                sharedPreferences.edit().putString("notes", String.valueOf(set)).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
