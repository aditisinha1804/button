package e.hp.button;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    static  ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.add_note){
            Intent intent = new Intent(getApplicationContext(),noteEditorActivity.class);
            startActivity(  intent);
            return  true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstancesState)
    {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.activity_main);

        ListView listView= (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences=
                getApplicationContext().getSharedPreferences(
                        "e.hp.button", Context.MODE_PRIVATE);
        HashSet<String> set= (HashSet<String>) sharedPreferences.getStringSet("notes",null);

        if(set== null){
            notes.add("Example Note");}
        else {
            notes= new ArrayList(set);
        }
        arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Intent intent = new Intent(getApplicationContext(),noteEditorActivity.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                final int itemToDel=i;

                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Are u sure u want to delete?")
                        .setMessage("do u want").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                notes.remove(itemToDel);

                                arrayAdapter.notifyDataSetChanged();
                                SharedPreferences sharedPreferences=
                                        getApplicationContext().getSharedPreferences(
                                                "e.hp.button", Context.MODE_PRIVATE
                                        );


                                HashSet<String> set= new HashSet(MainActivity.notes);
                                sharedPreferences.edit().putString("notes", String.valueOf(set)).apply();

                            }
                        }

                ).setNegativeButton("no",null).show();




                return true;
            }
        });
    }}