package com.vladimirloyanich.speedreading;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Main extends ActionBarActivity {
    private Button DialogButton,okButton,CancelButton;
    private TextView UserNameTextView;
    public boolean enteredEditText=false;
    private EditText DialogEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        UserNameTextView = (TextView) findViewById(R.id.TextViewUserName);

        //Button for View dialog set user name
        DialogButton = (Button) findViewById(R.id.ButtonUserName);
        DialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewDialogUserName();
            }
        });
    }

    public void viewDialogUserName(){

        // Created a new Dialog
        final Dialog dialog = new Dialog(Main.this);
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout
        dialog.setContentView(R.layout.dialogusername);
        /** Design the dialog in main.xml file */
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        DialogEditText = (EditText) dialog.findViewById(R.id.username);
        okButton = (Button) dialog.findViewById(R.id.OkButton);
        okButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                if(!DialogEditText.getText().toString().equals("") ){
                    UserNameTextView.setText(DialogEditText.getText());
                    enteredEditText=true;}
                else {UserNameTextView.setText(R.string.username);}
                dialog.dismiss();}
        });
        CancelButton = (Button) dialog.findViewById(R.id.CancelButton);
        CancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            dialog.dismiss();}
        });
        // Display the dialog
        dialog.show();
    }

       //filling the username

    //call action Test Speed Reading
    public void TestSpeedReading(View view){
        Intent intent = new Intent(this,Speed_reading.class);
        String UserName=UserNameTextView.getText()+"";
        intent.putExtra("com.vladimirloyanich.speedreading.username",UserName);
        startActivity(intent);
    }
    //call action Settings menu
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
