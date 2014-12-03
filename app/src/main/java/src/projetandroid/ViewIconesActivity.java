package src.projetandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import model.DBHelper;


public class ViewIconesActivity extends Activity {
    private DBHelper db;
    private ListView list;
    private ArrayList<String> listId;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_icones);

        setTitle("Gallerie icône");

        listId =new ArrayList<String>();
        db = new DBHelper(this);
        list = (ListView) findViewById(R.id.iconesListView);
        ArrayList<String> listIcones = new ArrayList<String>();

        Cursor pages = db.selectAllBoutonQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {
            listIcones.add(pages.getString(pages.getColumnIndex("NOMBOUTON")) );
            listId.add(pages.getString(pages.getColumnIndex("IDBOUTON")));
            pages.moveToNext();
        }
        pages.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listIcones);
        list.setAdapter(adapter);
        list.setOnItemClickListener(itemClicked);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_icones, menu);
        return true;
    }

    private ListView.OnItemClickListener itemClicked = new ListView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = Integer.valueOf(listId.get(i));
            Bundle b = new Bundle();

            b.putInt("idIcone", id);

            Intent intentModifierIcone = new Intent(ViewIconesActivity.this, ModifierIconeActivity.class);
            intentModifierIcone.putExtras(b);
            startActivity(intentModifierIcone);
        }



    };
}
