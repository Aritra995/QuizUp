package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import org.jetbrains.annotations.NotNull;

public class ExitAppDialog extends AppCompatDialogFragment {
    private ExitAppListener listener;
    @NonNull
    @NotNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Exit QuizUp?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onExitNowYesClicked();
                    }
                });
        return builder.create();
    }
    public interface ExitAppListener{
        void onExitNowYesClicked();
    }

    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        try {
            listener = (ExitAppListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+
                    "must implement ExitAppListener");
        }
    }
}
