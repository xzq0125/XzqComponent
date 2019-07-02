package com.xzq.module.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xzq.module_base.arouter.Router;
import com.xzq.module_base.base.BasePresenterActivity;
import com.xzq.module_base.mvp.MvpContract;

/**
 * 登录页
 */
@Route(path = Router.Path.LOGIN)
public class LoginActivity extends BasePresenterActivity implements
        MvpContract.ValidateCodeSucceed,
        View.OnClickListener {

    private EditText edtPhone;
    private EditText edtCode;
    private EditText edtNickname;
    private TextView btnGetCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        setToolbar("登录");

        edtPhone = findViewById(R.id.edt_phone);
        edtCode = findViewById(R.id.edt_code);
        edtNickname = findViewById(R.id.edt_nickname);
        btnGetCode = findViewById(R.id.btn_get_code);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_weixin_login).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String phone = edtPhone.getText().toString();
        String code = edtCode.getText().toString();
        presenter.validateCode(phone, code);
    }

    @Override
    public void validateCodeSucceed() {
        String phone = edtPhone.getText().toString();
        String code = edtCode.getText().toString();
        String nickname = edtNickname.getText().toString();
        presenter.login(phone, code, nickname, null);
    }
}
