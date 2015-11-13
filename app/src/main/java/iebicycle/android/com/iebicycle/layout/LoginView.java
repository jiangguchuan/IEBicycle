package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import iebicycle.android.com.iebicycle.HomeActivity;
import iebicycle.android.com.iebicycle.R;
import iebicycle.android.com.iebicycle.Test1Activity;
import iebicycle.android.com.iebicycle.TestActivity;

public class LoginView extends LinearLayout implements OnClickListener {

    private Context mContext;
    private EditText mPassword;
    private EditText mAccount;
    private CheckBox mAutoLogin;
    private Button mLogin;
    private Button mRegister;

    public LoginView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.login_view, this);

        mAccount = (EditText) findViewById(R.id.et_account);
        mPassword = (EditText) findViewById(R.id.et_password);
        mAutoLogin = (CheckBox) findViewById(R.id.cb_auto_login);
        mLogin = (Button) findViewById(R.id.btn_login);
        mLogin.setOnClickListener(this);
        mRegister = (Button) findViewById(R.id.btn_register);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//TODO: login logic
//                if (mAccount.getText().toString().equals("1234") &&
//                        mPassword.getText().toString().equals("1234")) {
                    mContext.startActivity(new Intent(mContext, HomeActivity.class));
//                } else {
//                    Toast.makeText(mContext, "请输入正确的用户名和密码", Toast.LENGTH_LONG).show();
//                }
                break;
            case R.id.btn_register:
                mContext.startActivity(new Intent(mContext, Test1Activity.class));
                break;
        }
    }
}
