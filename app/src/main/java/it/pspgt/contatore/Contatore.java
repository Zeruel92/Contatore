package it.pspgt.contatore;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Contatore extends Activity implements View.OnClickListener {

    private String tipo;
    private Button applica, endturn, moneta, dado;
    private EditText danni;
    private TextView giocatore1, giocatore2, lp1, lp2;
    private CheckBox cure;
    private boolean turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatore);
        Intent i = getIntent();
        tipo = i.getExtras().getString("tipo");
        applica = (Button) findViewById(R.id.button);
        endturn = (Button) findViewById(R.id.button2);
        moneta = (Button) findViewById(R.id.button3);
        dado = (Button) findViewById(R.id.button4);
        applica.setOnClickListener(this);
        endturn.setOnClickListener(this);
        moneta.setOnClickListener(this);
        dado.setOnClickListener(this);
        cure = (CheckBox) findViewById(R.id.checkBox);
        danni = (EditText) findViewById(R.id.editText);
        giocatore1 = (TextView) findViewById(R.id.textView);
        giocatore2 = (TextView) findViewById(R.id.textView2);
        giocatore1.setText(i.getExtras().getString("nome1"));
        giocatore2.setText(i.getExtras().getString("nome2"));
        lp1 = (TextView) findViewById(R.id.textView3);
        lp2 = (TextView) findViewById(R.id.textView4);
        turn = true;
        giocatore1.setTextColor(Color.GREEN);
        if (tipo.equals("ygo")) {
            lp1.setText(R.string.ygo);
            lp2.setText(R.string.ygo);
        } else if (tipo.equals("fow")) {
            lp1.setText(R.string.fow);
            lp2.setText(R.string.fow);
        } else {
            lp1.setText(R.string.magic);
            lp2.setText(R.string.magic);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contatore, menu);
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

    @Override
    public void onClick(View v) {
        if (v.equals(endturn)) {
            turn = !turn;
            if (turn) {
                giocatore1.setTextColor(Color.GREEN);
                giocatore2.setTextColor(Color.BLACK);
            } else {
                giocatore1.setTextColor(Color.BLACK);
                giocatore2.setTextColor(Color.GREEN);
            }
        } else if (v.equals(applica)) {
            String testo=danni.getText().toString();
            if(testo.equals("")){
                testo="0";
            }
            if (cure.isChecked()) {
                aggiungi(Integer.parseInt(testo));
            } else {
                rimuovi(Integer.parseInt(testo));
            }
        } else if (v.equals(moneta)) {
            lancio(2);
        } else {
            lancio(6);
        }
    }

    private void aggiungi(int cure) {
        if (turn) {
            int lifepoint = Integer.parseInt(lp1.getText().toString());
            lifepoint += cure;
            String lifes = "" + lifepoint;
            lp1.setText(lifes);
        } else {
            int lifepoint = Integer.parseInt(lp2.getText().toString());
            lifepoint += cure;
            String lifes = "" + lifepoint;
            lp2.setText(lifes);
        }
    }

    private void rimuovi(int danni) {
        if (turn) {
            int lifepoint = Integer.parseInt(lp1.getText().toString());
            lifepoint -= danni;
            if(lifepoint<=0){
                lifepoint=0;
                Toast.makeText(getApplicationContext(),"Ha vinto il giocatore 2",Toast.LENGTH_LONG).show();
                fine();
            }
            String lifes = "" + lifepoint;
            lp1.setText(lifes);
        } else {
            int lifepoint = Integer.parseInt(lp2.getText().toString());
            lifepoint -= danni;
            if(lifepoint<=0){
                lifepoint=0;
                Toast.makeText(getApplicationContext(),"Ha vinto il giocatore 1",Toast.LENGTH_LONG).show();
                fine();
            }
            String lifes = "" + lifepoint;
            lp2.setText(lifes);
        }
    }

    private void lancio(int n) {
        String stringa="E' uscito ";
        Random rnd = new Random();
        int numero =rnd.nextInt(n);
        if(n==2){
            if(numero==0){
                stringa=stringa+"Croce";
            }
            else{
                stringa=stringa+"Testa";
            }
        }
        else{
            numero++;
            stringa=stringa+numero;
        }
        Toast t=Toast.makeText(getApplicationContext(),stringa,Toast.LENGTH_SHORT);
        t.show();
    }
    private void fine(){
        applica.setOnClickListener(null);
        endturn.setOnClickListener(null);
    }
}