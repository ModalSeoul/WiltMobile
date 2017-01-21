package fm.wilt.mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText username = (EditText) findViewById(R.id.username_input);
        String message = username.getText().toString();
        intent.putExtra("shit", message);
        System.out.println("Fuck");
    }
}
