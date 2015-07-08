package com.vladimirloyanich.speedreading;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Владимир on 29.06.2015.
 */
public class Settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingslayout);
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonBigText:
                if (checked)
                    // Pirates are the best
                    Toast.makeText(this,"Big Text Cheked",Toast.LENGTH_SHORT).show();

                    break;
            case R.id.radioButtonNormalText:
                if (checked)
                    // Ninjas rule
                    Toast.makeText(this,"Normal Text Cheked",Toast.LENGTH_SHORT).show();
                    break;
            case R.id.radioButtonSmallText:
                if (checked)
                    // Ninjas rule
                    Toast.makeText(this,"Small Text Cheked",Toast.LENGTH_SHORT).show();
                    break;
        }
    }

}
