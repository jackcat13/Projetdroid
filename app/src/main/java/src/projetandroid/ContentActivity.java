package src.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridLayout;

import model.DBHelper;


public class ContentActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayoutContent);

        DBHelper db = new DBHelper(this);

        int i = 0;
        Cursor buttons = db.selectPageQuery();
        buttons.moveToFirst();
        buttons.moveToNext();
        while(!buttons.isAfterLast()) {

            /*System.out.println(buttons.getString(buttons.getColumnIndex("IDBOUTON")));
            System.out.println(buttons.getString(buttons.getColumnIndex("NOMBOUTON")));*/

            Button b = new Button(this);
            String nomBouton = buttons.getString(buttons.getColumnIndex("NOMPAGE"));
            b.setId( Integer.valueOf(buttons.getString(buttons.getColumnIndex("IDPAGE"))) );
            b.setText( nomBouton );
            b.setPadding(20,20,20,20);
            b.setOnClickListener(pageAccess);

            gl.addView(b);

            buttons.moveToNext();
        }
        buttons.close();

        /*Cursor pages = db.selectPageQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {
            System.out.println(pages.getString(pages.getColumnIndex("IDPAGE")));
            System.out.println(pages.getString(pages.getColumnIndex("NOMPAGE")));
            pages.moveToNext();
        }
        pages.close();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_administration) {
            Intent intentAdmin = new Intent(this, AdminActivity.class);
            startActivity(intentAdmin);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener pageAccess = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent newPage = new Intent(ContentActivity.this, SectionPageActivity.class);
            Bundle b = new Bundle();
            Button button = (Button) v;

            b.putInt("idPage", button.getId());
            b.putString("nomPage", button.getText()+"");
            newPage.putExtras(b);
            startActivity(newPage);
        }
    };
}
