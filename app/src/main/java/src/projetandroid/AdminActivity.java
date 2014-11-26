package src.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class AdminActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button gererPagesButton = (Button) findViewById(R.id.gererPagesButton);
        Button gererIconesButton = (Button) findViewById(R.id.gererIconesButton);

        gererPagesButton.setOnClickListener(gererPagesListener);
        gererIconesButton.setOnClickListener(gererIconesListener);
    }

    private OnClickListener gererIconesListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intentGererPages = new Intent(AdminActivity.this, GererIconesActivity.class);
            startActivity(intentGererPages);
        }
    };

    private OnClickListener gererPagesListener = new OnClickListener() {
        public void onClick(View v) {
            Intent intentGererPages = new Intent(AdminActivity.this, GererPagesActivity.class);
            startActivity(intentGererPages);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
