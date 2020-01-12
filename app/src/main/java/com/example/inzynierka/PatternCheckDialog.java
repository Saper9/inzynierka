package com.example.inzynierka;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;

public class PatternCheckDialog extends AppCompatDialogFragment {
    String msg;

    public static PatternCheckDialog newInstance(String msgr) {
        PatternCheckDialog f = new PatternCheckDialog();
        Bundle args = new Bundle();
        args.putString("msgr", msgr);
        f.setArguments(args);
        return f;


    }

    @Override


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        msg = getArguments().getString("msgr");
        builder.setTitle("Sprawdzenie poprawnosci").setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}
