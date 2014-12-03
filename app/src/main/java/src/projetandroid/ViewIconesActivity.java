package src.projetandroid;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import model.DBHelper;


public class ViewIconesActivity extends Activity {
    private DBHelper db;
    private ListView list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_icones);

        db = new DBHelper(this);
        list = (ListView) findViewById(R.id.iconesListView);
        ArrayList<String> listIcones = new ArrayList<String>();

        Cursor pages = db.selectAllBoutonQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {
            listIcones.add( pages.getString(pages.getColumnIndex("NOMBOUTON")) );
            pages.moveToNext();
        }
        pages.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,listIcones);
        list.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_icones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Bundle b=new Bundle();

        b.putInt("idIcone",id);

        Intent intentAjouterIcone = new Intent(ViewIconesActivity.this, ModifierIconeActivity.class);
        intentAjouterIcone.putExtras(b);
        startActivity(intentAjouterIcone);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
