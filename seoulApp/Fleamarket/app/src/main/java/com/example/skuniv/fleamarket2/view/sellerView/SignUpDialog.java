package com.example.skuniv.fleamarket2.view.sellerView;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.skuniv.fleamarket2.R;
import com.example.skuniv.fleamarket2.databinding.SignUpBinding;
import com.example.skuniv.fleamarket2.model.jsonModel.SignUpJson;
import com.example.skuniv.fleamarket2.viewModel.AdminSellerViewModel.MainCommand;
import com.google.firebase.iid.FirebaseInstanceId;

public class SignUpDialog extends Dialog {
    Context context;
    SignUpBinding signUpBinding;
    MainCommand mainCommand;
    String sex="";
    SignUpDialog signUpDialog;

    public SignUpDialog(@NonNull Context context, MainCommand mainCommand) {
        super(context);
        this.context = context;
        this.mainCommand = mainCommand;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = (SignUpBinding)
                DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.sign_up, null, false);
        setContentView(signUpBinding.getRoot());
        mainCommand.setSignUpDialog(this);

        signUpDialog = this;
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(signUpDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        Window window = signUpDialog.getWindow();
        window.setAttributes(lp);

        signUpBinding.sexRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.male){
                    sex = "male";
                } else if(i == R.id.female){
                    sex = "female";
                }
            }
        });

        signUpBinding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 연동 시
                if(signUpBinding.idText.getText().toString().equals("")){
                    Toast.makeText(context,"아이디를 입력하세요",Toast.LENGTH_SHORT).show();
                } else if(signUpBinding.pwText.getText().toString().equals("")){
                    Toast.makeText(context,"비밀번호를 입력하세요",Toast.LENGTH_SHORT).show();
                } else if(signUpBinding.nameText.getText().toString().equals("")){
                    Toast.makeText(context,"이름을 입력하세요",Toast.LENGTH_SHORT).show();
                } else if(signUpBinding.emailText.getText().toString().equals("")){
                    Toast.makeText(context,"이메일을 입력하세요",Toast.LENGTH_SHORT).show();
                } else{
                    SignUpJson signUpJson = new SignUpJson(signUpBinding.idText.getText().toString(),signUpBinding.pwText.getText().toString(),
                            signUpBinding.nameText.getText().toString(),sex,signUpBinding.emailText.getText().toString(), FirebaseInstanceId.getInstance().getToken());
                    mainCommand.signUp(signUpJson);
                    //sex,signUpBinding.nameText.getText().toString(), signUpBinding.emailText.getText().toString());
                    System.out.println("아이디 : " + signUpBinding.idText.getText().toString() + "  비밀번호 : " + signUpBinding.pwText.getText().toString() +
                            "  성별 : " + sex + "  이름 : " + signUpBinding.nameText.getText().toString() + "  이메일 : " + signUpBinding.emailText.getText().toString());
                }
                // test
                //mainCommand.signUptest();
            }
        });
    }
}
