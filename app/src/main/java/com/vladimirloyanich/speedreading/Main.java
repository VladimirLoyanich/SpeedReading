package com.vladimirloyanich.speedreading;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class Main extends ActionBarActivity implements UserDialogFragment.PositiveResult{
    private Button DialogButton;
    private TextView UserNameTextView;
    public boolean enteredEditText=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);




        UserNameTextView = (TextView) findViewById(R.id.TextViewUserName);

        //Button for View dialog set user name
        DialogButton = (Button) findViewById(R.id.ButtonUserName);
        DialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment dialog = new UserDialogFragment();
                dialog.show(getSupportFragmentManager(), "DIALOG_USER_NAME");
            }
        });
    }

    public void onPositiveResult(CharSequence text) {
        if(!text.toString().equals("") ){
            UserNameTextView.setText(text);
            enteredEditText=true;}
        else UserNameTextView.setText(R.string.username);
    }

    public void TestSpeedReading(View view){
        Intent intent = new Intent(this,Speed_reading.class);
        String UserName=UserNameTextView.getText()+"";
        intent.putExtra("com.vladimirloyanich.speedreading.username",UserName);
        startActivity(intent);
    }

    public void Settings(View view){
        Intent intent1 = new Intent(this,Settings.class);
        startActivity(intent1);

    }
/*    @Override
    public boolean onCreateOptionsMenu(Mine_menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return super.onOptionsItemSelected(item);}

*/
}
