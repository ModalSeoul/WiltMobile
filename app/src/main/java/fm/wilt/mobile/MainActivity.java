package fm.wilt.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
                sendMessage(v);
            }
        });



        IntentFilter iF = new IntentFilter();
        iF.addAction("com.android.music.metachanged");

        System.out.println("I'm gay");

        registerReceiver(mReceiver, iF);
    }

    public void sendMessage(View view) {

        Thread thread = new Thread(new Runnable() {
            EditText username = (EditText) findViewById(R.id.username_input);
            EditText password = (EditText) findViewById(R.id.password_input);

            @Override
            public void run() {
                Wilt.getSession().login(
                        username.getText().toString(),
                        password.getText().toString());
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        intent.putExtra("shit", message);
//        System.out.println("Fuck");
    }

    // This is really ugly. Java's lame
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        int count = 0;

        @Override
        public void onReceive(Context context, Intent intent) {
            final Intent i = intent;

            if (Wilt.getSession().isAuthed) {
                new Thread() {
                    public void run() {
                        if (count == 0) {
                            Wilt.getSession().postScrobble(
                                    i.getStringExtra("track"),
                                    i.getStringExtra("artist"),
                                    i.getStringExtra("album"));
                        }
                        count++;
                    }
                }.start();

            } else {
                System.out.println("You aren't authed");
            }
        }
    };
}
