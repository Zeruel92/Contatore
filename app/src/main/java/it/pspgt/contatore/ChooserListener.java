package it.pspgt.contatore;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class ChooserListener implements View.OnClickListener {
    private Context c;
    private RadioGroup g;
    private EditText nome1;
    private EditText nome2;
    public ChooserListener(Context c,RadioGroup g,EditText nome1, EditText nome2){
        this.c=c;
        this.g=g;
        this.nome1=nome1;
        this.nome2=nome2;
    }
    @Override
    public void onClick(View v) {
       Intent i=new Intent(this.c,Contatore.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        int selected=g.getCheckedRadioButtonId();
        int ygo=R.id.radioButton;
        int fow=R.id.radioButton2;
        int magic=R.id.radioButton3;
        if(selected==ygo) {
            i.putExtra("tipo", "ygo");
        }
        else if(selected==fow){
            i.putExtra("tipo","fow");
        }
        else{
            i.putExtra("tipo","magic");
        }
        String nome=nome1.getText().toString();
        i.putExtra("nome1",nome);
        nome=nome2.getText().toString();
        i.putExtra("nome2",nome);
        c.startActivity(i);
    }
}
