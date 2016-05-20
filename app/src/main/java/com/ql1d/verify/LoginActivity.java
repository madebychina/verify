package com.ql1d.verify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ql1d.util.CustomToast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_login = null;
    private EditText txt_user = null;
    private EditText txt_pwd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        txt_user = (EditText) findViewById(R.id.txt_user);
        txt_pwd = (EditText) findViewById(R.id.txt_pwd);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String user = txt_user.getText().toString().trim();
        if (null == user || "".equals(user)) {
            CustomToast.showMessage(this, "请输入壹点后台账号",
                    Toast.LENGTH_SHORT, CustomToast.CENTER);
            return;
        }

        String pwd = txt_pwd.getText().toString();
        if (null == pwd || "".equals(pwd)) {
            CustomToast.showMessage(this, "请输入密码",
                    Toast.LENGTH_SHORT, CustomToast.CENTER);
            return;
        }

        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
