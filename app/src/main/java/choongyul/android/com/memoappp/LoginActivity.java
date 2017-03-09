package choongyul.android.com.memoappp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText loginET;
    Button loginBtn;
    private final String PASSWARD1 = "bless0310";
    private final String PASSWARD2 = "Bless0310";
    private final String PASSWARD3 = "BLESS0310";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginET = (EditText) findViewById(R.id.loginET);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = loginET.getText().toString();
                Log.e("입력한 비밀번호는"," 이것이다." + password);
                if( password.equals(PASSWARD1) || password.equals(PASSWARD2) || password.equals(PASSWARD3) ) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Log.e("로그인액티비티에서 "," 메인액티비티 진입");
                    loginET.setText("");
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}
