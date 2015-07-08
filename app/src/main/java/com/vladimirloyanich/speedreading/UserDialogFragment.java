package com.vladimirloyanich.speedreading;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Created by Владимир on 24.06.2015.
 */
public class UserDialogFragment extends DialogFragment {

    public interface PositiveResult {
        public void onPositiveResult(CharSequence text);
    }

    private LinearLayout View;
    public boolean enteredEditText=false;
    EditText DialogEditText;

    public String toString() {
        if(!DialogEditText.toString().equals("") ){
            enteredEditText=true;
            return DialogEditText.toString();}
            return "User";

    }
    Main activityListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            activityListener =(Main)getActivity();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement PositiveResult");
        }
    }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater=getActivity().getLayoutInflater();

            View =(LinearLayout)inflater.inflate(R.layout.dialogusername, null);
            dialog.setView(View);
            DialogEditText=(EditText)View.findViewById(R.id.username);

            dialog.setPositiveButton(R.string.DialogButtonOk, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    activityListener.onPositiveResult(DialogEditText.getText());
                }
            });
            dialog.setNegativeButton(R.string.DialogButtonCancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();

                }
            });
        return dialog.create();
    }
}