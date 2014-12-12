package src.projetandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.speech.tts.*;

import java.util.ArrayList;
import java.util.Locale;

import model.DBHelper;


public class ContentActivity extends Activity {

    private static ArrayList<Button> phrase;
    static TextView phraseView;
    public static TextToSpeech mTts;

    private Button bDelLastWord;
    private Button bResetPhrase;

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setPhrase(new ArrayList<Button>());
        setContentView(R.layout.activity_content);

        phraseView = (TextView) findViewById(R.id.phraseView);
        phraseView.setOnClickListener(speak);

        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status) {}
        });

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
            LinearLayout l = new LinearLayout(this);

            String nomBouton = buttons.getString(buttons.getColumnIndex("NOMPAGE"));
            b.setId( Integer.valueOf(buttons.getString(buttons.getColumnIndex("IDPAGE"))) );
            b.setText(nomBouton);
            b.setPadding(20, 20, 20, 20);
            b.setOnClickListener(pageAccess);
            b.setOnLongClickListener(longClickListener);
            l.addView(b);
            l.setOnDragListener(dragListener);
            gl.addView(l);

            buttons.moveToNext();
        }
        buttons.close();
        bDelLastWord = (Button) findViewById(R.id.delLastWordButton);
        bDelLastWord.setOnClickListener(delLastWord);

        bResetPhrase = (Button) findViewById(R.id.resetPhraseButton);
        bResetPhrase.setOnClickListener(resetPhrase);


        if (mTts.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_COUNTRY_AVAILABLE)
        {
            mTts.setLanguage(Locale.FRANCE);
        }

        mTts.setSpeechRate((float) 0.80); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus lente, une valeur supérieure la rendra plus rapide.
        mTts.setPitch(1); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus grave, une valeur supérieure la rendra plus aigue.



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

    @Override
    protected void onResume()
    {
        super.onResume();

        setTextPhrase();
    }

    public View.OnLongClickListener longClickListener = new View.OnLongClickListener()
    {

        public boolean onLongClick(View view) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    };

    public View.OnDragListener dragListener = new View.OnDragListener()
    {
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    if (v instanceof LinearLayout) {

                        View viewDragged = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) viewDragged.getParent();
                        ViewGroup receiver = (ViewGroup) v;
                        View viewReplaced = (View) ((ViewGroup) v).getChildAt(0);

                        owner.removeView(viewDragged);
                        receiver.removeView(viewReplaced);

                        owner.addView(viewReplaced);
                        receiver.addView(viewDragged);

                        viewDragged.setVisibility(View.VISIBLE);
                        viewReplaced.setVisibility(View.VISIBLE);
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    View viewDragged = (View) event.getLocalState();
                    viewDragged.setVisibility(View.VISIBLE);

                    break;
                default:
                    break;
            }
            return true;
        }
    };

      public View.OnClickListener pageAccess = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent newPage = new Intent(ContentActivity.this, SectionPageActivity.class);
            Bundle b = new Bundle();
            Button button = (Button) v;

            b.putInt("idPage", button.getId());
            b.putString("nomPage", button.getText() + "");
            newPage.putExtras(b);

            startActivity(newPage);
        }
    };

    static private View.OnClickListener speak = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            String phrase = "";

            for(Button e: getPhrase())
                phrase += e.getText() + " ";

            if (mTts.isSpeaking())
                mTts.stop();

            mTts.speak(phrase, TextToSpeech.QUEUE_ADD, null);
        }
    };

    static private View.OnClickListener resetPhrase = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            setPhrase(new ArrayList<Button>());
            phraseView.setText("");
        }
    };

    private View.OnClickListener delLastWord = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            if (phrase.size() > 0)
            {
                phrase.remove(phrase.size() - 1);
                setTextPhrase();
            }
        }
    };

    public static ArrayList <Button> getPhrase()
    {
        return ContentActivity.phrase;
    }

    public static void setPhrase(ArrayList <Button> l)
    {
        ContentActivity.phrase = l;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0x01)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                // Succès, au moins un moteur de TTS à été trouvé, on l'instancie
                mTts = new TextToSpeech(this, (TextToSpeech.OnInitListener) this);
            }
            else
            {
                // Echec, aucun moteur n'a été trouvé, on propose à l'utilisateur d'en installer un depuis le Market
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

        if (mTts.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_COUNTRY_AVAILABLE)
        {
            mTts.setLanguage(Locale.FRANCE);
        }

        mTts.setSpeechRate(1); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus lente, une valeur supérieure la rendra plus rapide.
        mTts.setPitch(1); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus grave, une valeur supérieure la rendra plus aigue.

    }

    private void setTextPhrase()
    {
        String str = "";


        for(Button e: getPhrase())
            str += e.getText() + " ";

        phraseView.setText(str);
    }
}
