package com.lotus.gate2k18;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    GridLayout grid;
    Spinner spinner_section;
    //9,1,3,3,5,5,1,3,4
    /*[9][]*/
    Button b1[][]=new Button[12][11];
    int button_count=000;
    static int page_count=0;
    static int section_count=0;
    int section_num_number[][]=new int[][]{{7,8,8,5,5,3,3,4,4},{6,9},{2,5,5,6},{6,7,5},{3,3,3,6,6},{10,4,3,3,3},{9,8},{4,5,3,10,10},{6,5,6,4,7}};
    int section_number[]=new int[]{9,2,4,3,5,5,2,5,5};
    String [] Section_Topics=new String[]{
            "Engineering Mathematics",
            "Networks",
            "Signals and Systems",
            "Electronic Devices",
            "Analog Circuits",
            "Digital Circuits",
            "Control System",
            "Communications",
            "Electromagnetic"};
    //set to page_linear
    String [][]Section ={
            {"Engineering Mathematics Linear Algebra","Calculus","Differential Equations","Vector Analysis","Complex Analysis","Numerical Methods","Probability and Statistics","Probability distribution functions","Transforms"},
            {"Networks","Steady State"},
            {"Continuous-time signals","Discrete-time signals","LTI systems","LTI systems"},
            {"Carrier transport","Semiconductor Devices","Integrated circuit fabrication process"},
            {"Analog Circuits","Simple diode circuits","Single-stage BJT and MOSFET amplifiers","BJT and MOSFET amplifiers","Sinusoidal oscillators"},
            {"Combinational circuits","Sequential circuits","Data converters","Semiconductor memories","8-bit microprocessor (8085)"},
            {"Control Systems","Bode Plots"},
            {"Random processes","Analog communications","Information theory","Digital communications","Digital Communications"},
            {"Electromagnetic","Plane waves and Properties","Transmission lines","Waveguides","Antennas"}
        };
    String [][]Section_Short=new String [][]{
            {"EMLA","Cal","DE","VA","CA","NM","PS","PDF","T"},
            {"Net1","SS"},
            {"CTS","DTS","LTI","LTII"},
            {"CT","SD","ICFP"},
            {"AC","SDC","SSA","BAMA","SO"},
            {"CC","SC","DC","SM","m8085"},
            {"CS","BP"},
            {"RP","ACC","IT","DCC","DCCC"},
            {"EM","PWP","TL","W","A"}};
    DisplayMetrics displayMetrics;
    TextView sec_tit,pag_tit;
    DatabaseHelper databaseHelper=new DatabaseHelper(this);
    int width1;
    int b_width,t_width;
    int height=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        spinner_section=(Spinner)findViewById(R.id.Spinner_Section);
        displayMetrics=new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width=displayMetrics.widthPixels;height=displayMetrics.heightPixels;
        width1= width*(2);
        b_width= width1-width;t_width=width;
        grid=(GridLayout)findViewById(R.id.fragment_section); grid.setColumnCount(11);grid.setRowCount(12);
        sec_tit=(TextView)findViewById(R.id.tv_linear_section);
        pag_tit=(TextView)findViewById(R.id.tv_linear_page);
        //sec_tit.setText(Section_Topics[0]);
        List<String > list=new ArrayList<String>();
        for(int i=0;i<Section_Topics.length;i++){
            list.add(Section_Topics[i]);
        }
        ArrayAdapter<String> dataAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_section.setAdapter(dataAdapter);
        spinner_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int x=position;
                button_count=000;
                section_count=x;
                page_count=0;
                refresh();
                setting_data();
                //Toast.makeText(MainActivity.this, ""+String.valueOf(x), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        try{
            databaseHelper.createdatabase();
        }catch (IOException ioe){
            throw new Error("Unable to create database");
        }
        try {
            databaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //setting_table();
        setting_data();
    }
    String type1[]={"L","C","N","Pr","Py","R1","R2","R3","M","A"};
    String type2[]={"L","C","N","Gb","Gr","Py","M","R1","R2","R3"};
    public void setting_table(){
        LinearLayout.LayoutParams buttons = new LinearLayout.LayoutParams(b_width/10,80);
        buttons.setMargins(10,10,10,10);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(t_width-30,(height-120)/12);
        TextView t[]=new TextView[12];
        t[0]=new TextView(this);
        t[0].setText("Topic");
        t[0].setLayoutParams(text);
        for(int i=1;i<12;i++){
            if(section_count==0){
                t[i]=new TextView(this);
                t[i].setText(""+type1[i]);t[i].setLayoutParams(buttons);
            }else {
                t[i]=new TextView(this);
                t[i].setText(""+type2[i]);t[i].setLayoutParams(buttons);
            }
        }
    }
    String one="1";
    String two="0";
    String three="2";
    public void setting_data() {
        button_count=000;
        //Toast.makeText(MainActivity.this, ""+section_count+".."+page_count, Toast.LENGTH_SHORT).show();
        pag_tit.setText(Section[section_count][page_count]);
        LinearLayout.LayoutParams buttons = new LinearLayout.LayoutParams(b_width/10,80);
        buttons.setMargins(10, 10, 10, 10);
        LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(t_width-30,(height-120)/12);
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j < 11; j++) {
                if(i==section_num_number[section_count][page_count]){
                    break;
                }else {
                    if (j == 0) {
                        b1[i][j] = new Button(this);
                        String topic = databaseHelper.Retrieve_data(Section_Short[section_count][page_count], 0, i);
                        if(topic==null){
                            break;
                        }
                        //Toast.makeText(MainActivity.this, ""+topic, Toast.LENGTH_SHORT).show();
                        b1[i][j].setText("" + topic);
                        b1[i][j].setPadding(5, 0, 0, 0);
                        b1[i][j].setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                longpress_button(v);
                                return false;
                            }
                        });
                        //b1[i][j].setBackgroundResource(R.drawable.rectangle);
                        b1[i][j].setId(button_count);
                        //b1[i][j].setTextColor(R.color.colorWhite);
                        //b1[i][j].setAlpha((float) 0.3);
                        button_count++;
                        b1[i][j].setLayoutParams(text);
                        grid.addView(b1[i][j]);
                    } else {
                        String status = databaseHelper.Retrieve_data(Section_Short[section_count][page_count], j, i);
                        b1[i][j] = new Button(this);
                        if (status == null) {
                            b1[i][j].setBackgroundResource(R.drawable.emptybox);
                            if(section_count==0) {
                                b1[i][j].setHint(type2[j - 1]);
                            }else if(section_count!=0){
                                b1[i][j].setHint(type1[j-1]);
                            }
                        }else if(status.equals(""+one)){
                            b1[i][j].setBackgroundResource(R.drawable.tickb);
                        }else if(status.equals(""+two)){
                            b1[i][j].setBackgroundResource(R.drawable.focus);
                        }else if(status.equals(""+three)){
                            b1[i][j].setBackgroundResource(R.drawable.emptybox);
                        }
                        b1[i][j].setAlpha((float)0.4);
                        //b1[i][j].setBackgroundResource(R.drawable.emptybox);
                        //b1[i][j].setText(status);
                        if(section_count==0) {
                            b1[i][j].setHint(type2[j - 1]);
                        }else if(section_count!=0){
                            b1[i][j].setHint(type1[j-1]);
                        }
                        b1[i][j].setTextColor(R.color.colorWhite);
                        b1[i][j].setId(button_count);b1[i][j].setClickable(true);
                        b1[i][j].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                button_click(v);
                            }
                        });
                        b1[i][j].setPadding(5, 0, 0, 0);
                        button_count++;
                        b1[i][j].setLayoutParams(buttons);
                        grid.addView(b1[i][j], buttons);
                    }
                }
            }
            button_count+=100-11;
        }
    }
    public void bt_left_section(View v){
        int c=section_count;
        c=c-1;
        if(c<0){
            Toast.makeText(MainActivity.this, "That's End of Section", Toast.LENGTH_SHORT).show();
        }else {
            //sec_tit.setText(Section_Topics[c]);
            section_count=c;
            page_count=0;
            refresh();
            setting_data();
        }
    }
    public void longpress_button(View v){
        Button button=(Button)findViewById(v.getId());
        String text=button.getText().toString();
        Toast.makeText(MainActivity.this, "Developing.", Toast.LENGTH_SHORT).show();
    }
    public void bt_right_section(View v){
        int c=section_count;
        c=c+1;
        if(c>=9){
            Toast.makeText(MainActivity.this, "That's End of Section", Toast.LENGTH_SHORT).show();
        }else {
            //sec_tit.setText(Section_Topics[c]);
            section_count=c;
            page_count=0;
            refresh();
            setting_data();
        }
    }

    public void bt_left_page(View v){
        int c=page_count;
        c=c-1;
        if(c<0){
            Toast.makeText(MainActivity.this, "End of Page", Toast.LENGTH_SHORT).show();
        }else{
            page_count=c;
            refresh();
            setting_data();
        }
    }
    public void bt_right_page(View v){
        int c=page_count;
        c=c+1;
        if(c>=section_number[section_count]){
            Toast.makeText(MainActivity.this, "End of Page", Toast.LENGTH_SHORT).show();
        }else{
            page_count=c;
            refresh();
            setting_data();
        }
    }
    /*public void refresh(){
        for(int i=0;i<=10;i++){
            for(int j=0;j<11;j++){
                ViewGroup layout=(ViewGroup)b1[i][j].getParent();
                if(null!=layout){
                    layout.removeAllViews();
                }else if(null==layout){
                    continue;
                }
            }
        }
    }*/
    public void refresh(){
        grid.removeAllViews();
    }
    public void button_click(View v) {
        Button b=(Button)findViewById(v.getId());
        int sample=0;
        if(section_count==0){
            sample=0;
        }else if(section_count!=0){
            sample=1;
        }
        int x=databaseHelper.Insert_data(Section_Short[section_count][page_count],v.getId(),sample);
        if(x==1){
            b.setBackgroundResource(R.drawable.tickb);
        }else if(x==0){
            b.setBackgroundResource(R.drawable.focus);
        }else if(x==2){
            b.setBackgroundResource(R.drawable.emptybox);
        }
        //Toast.makeText(MainActivity.this, ""+String.valueOf(v.getId())+".."+String.valueOf(x), Toast.LENGTH_SHORT).show();
    }
}