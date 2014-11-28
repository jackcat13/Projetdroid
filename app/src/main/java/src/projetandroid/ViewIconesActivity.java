package src.projetandroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

import model.DBHelper;


public class ViewIconesActivity extends Activity {
    private DBHelper db;
    private ArrayList<String> idPages;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_icones);

        ArrayList<String> listPages = new ArrayList<String>();
        idPages = new ArrayList<String>();

        Cursor pages = db.selectPageQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {

            ArrayList<String> page = new ArrayList<String>();
            idPages.add( pages.getString(pages.getColumnIndex("IDPAGE")) );
            listPages.add( pages.getString(pages.getColumnIndex("NOMPAGE")) );

            pages.moveToNext();
        }
        pages.close();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
