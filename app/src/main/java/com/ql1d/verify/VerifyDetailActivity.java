package com.ql1d.verify;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ql1d.util.CustomDialog;
import com.ql1d.util.CustomToast;
import com.ql1d.view.MyGridView;

public class VerifyDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txt_title;
    private TextView txt_content;
    private TextView txt_address;
    private TextView txt_user;
    private TextView txt_phone;
    private MyGridView grid_img;
    private ImageView img_back;
    private Button btn_dial;
    private Button btn_reject;
    private Button btn_agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_detail);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("情报详情");
        txt_content = (TextView) findViewById(R.id.txt_content);
        txt_address = (TextView) findViewById(R.id.txt_address);
        txt_user = (TextView) findViewById(R.id.txt_user);
        txt_phone = (TextView) findViewById(R.id.txt_phone);
        grid_img = (MyGridView) findViewById(R.id.grid_img);
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(this);
        btn_dial = (Button) findViewById(R.id.btn_dial);
        btn_dial.setOnClickListener(this);
        btn_reject = (Button) findViewById(R.id.btn_reject);
        btn_reject.setOnClickListener(this);
        btn_agree = (Button) findViewById(R.id.btn_agree);
        btn_agree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_dial:
                String phone = txt_phone.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.btn_reject:
                CustomDialog.CreateOKCancelDialogWithQuestion(VerifyDetailActivity.this, "否决确认", "确定否决该情报吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(VerifyDetailActivity.this, "确定",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(VerifyDetailActivity.this, "取消",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_agree:
                CustomDialog.CreateOKCancelDialogWithQuestion(VerifyDetailActivity.this, "通过确认", "确定通过该情报吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(VerifyDetailActivity.this, "确定",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomToast.showMessage(VerifyDetailActivity.this, "取消",
                                Toast.LENGTH_SHORT, CustomToast.CENTER);
                        dialog.dismiss();
                    }
                });
                break;
        }
    }
}
