package com.ql1d.verify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.util.CustomToast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout layout_toverify = null;
    private RelativeLayout layout_toreply = null;
    private RelativeLayout layout_verifycomment = null;
    private TextView txt_toverify = null;
    private TextView txt_toreply = null;
    private TextView txt_verifycomment = null;
    private TextView txt_logout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout_toverify = (RelativeLayout) findViewById(R.id.layout_toverify);
        layout_toverify.setOnClickListener(this);
        layout_toreply = (RelativeLayout) findViewById(R.id.layout_toreply);
        layout_toreply.setOnClickListener(this);
        layout_verifycomment = (RelativeLayout) findViewById(R.id.layout_verifycomment);
        layout_verifycomment.setOnClickListener(this);
        txt_toverify = (TextView) findViewById(R.id.txt_toverify);
        txt_toreply = (TextView) findViewById(R.id.txt_toreply);
        txt_verifycomment = (TextView) findViewById(R.id.txt_verifycomment);
        txt_logout = (TextView) findViewById(R.id.txt_logout);
        txt_logout.setVisibility(View.VISIBLE);
        txt_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.layout_toverify:
                CustomToast.showMessage(this, "待审核",
                        Toast.LENGTH_SHORT, CustomToast.CENTER);
                break;
            case R.id.layout_toreply:
                CustomToast.showMessage(this, "待回复",
                        Toast.LENGTH_SHORT, CustomToast.CENTER);
                break;
            case R.id.layout_verifycomment:
                CustomToast.showMessage(this, "待审核评论",
                        Toast.LENGTH_SHORT, CustomToast.CENTER);
                break;
            case R.id.txt_logout:
                Intent intent=new Intent();
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
