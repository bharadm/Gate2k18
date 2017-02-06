package com.lotus.gate2k18;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Sample extends Activity{
    LinearLayout.LayoutParams layoutParams;
    GridLayout gridLayout;
    Button b1[][]=new Button[15][15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_sample);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;
        int height=displayMetrics.heightPixels;
        Toast.makeText(Sample.this, ""+String.valueOf(width), Toast.LENGTH_SHORT).show();
        gridLayout=(GridLayout)findViewById(R.id.sample1);
        gridLayout.setColumnCount(15);gridLayout.setRowCount(15);
        int width1=width+(width/2);
        layoutParams= new LinearLayout.LayoutParams(width,height);
        LinearLayout.LayoutParams gr=new LinearLayout.LayoutParams(width/10,height/10);
        String s="*";
        TextView textView=new TextView(this);
        for(int i=0;i<15;i++){
            for(int j=0;j<15;j++){
                b1[i][j]=new Button(this);
                b1[i][j].setText("*");
                b1[i][j].setWidth(width / 10);b1[i][j].setHeight(height / 10);
                b1[i][j].setLayoutParams(gr);
                gridLayout.addView(b1[i][j]);
            }
        }
        /*
        Button b=new Button(this);
        b.setText("" + s);
        b.setWidth(width / 10);
        b.setHeight(height / 10);
        b.setLayoutParams(gr);
        gridLayout.addView(b);
*/
    }
}