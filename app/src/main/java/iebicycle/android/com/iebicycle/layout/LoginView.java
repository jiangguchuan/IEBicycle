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

import iebicycle.android.com.iebicycle.HomeActivity;
import iebicycle.android.com.iebicycle.R;

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
                mContext.startActivity(new Intent(mContext, HomeActivity.class));
                break;
            case R.id.btn_register:
                break;
        }
    }
}
