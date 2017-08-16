package com.jacksonyang.wolfkiller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class PersonSet extends AppCompatActivity {
    private Button next;
    private SeekBar change;
    private TextView currentperson;
    private int minnum=5;
    private int currentnum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_set);
        next=(Button) findViewById(R.id.nextstep);//下一步的按钮
        change=(SeekBar) findViewById(R.id.changeperson);//滑动条改变人数
        currentperson=(TextView) findViewById(R.id.currentperson);
        currentnum=minnum;
        currentperson.setText(Integer.toString(currentnum));
        change.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentnum=minnum+progress;
                currentperson.setText(Integer.toString(minnum));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog=builder.setMessage("确定人数为"+Integer.toString(currentnum)+"个玩家吗?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(PersonSet.this,RecommendSet.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
                alertDialog.show();
            }
        });
    }
}
