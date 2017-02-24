package platform.zonetry.com.voicerun.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import platform.zonetry.com.voicerun.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        if(view.getId()==R.id.start_button_activity_main){
            startActivity(new Intent(this, RunActivity.class));
        }
    }
}
