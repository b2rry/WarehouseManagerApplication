package com.appksa.warehousemanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.appksa.warehousemanager.MainActivity;
import com.appksa.warehousemanager.R;

public class AcceptLoadingFromExternalFileDialogFragment extends DialogFragment {

    public interface LoadingFromExternalFileDialogListener {
        public void onDialogAcceptLoadingClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AcceptLoadingFromExternalFileDialogFragment.LoadingFromExternalFileDialogListener listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AcceptLoadingFromExternalFileDialogFragment.LoadingFromExternalFileDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getString(R.string.load_external_state_file_title_message);
        String message = getString(R.string.load_external_state_file_message);
        String buttonPositiveString = getString(R.string.load_external_action_first_message);
        String buttonNegativeString = getString(R.string.delete_dialog_action_second_message);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(buttonPositiveString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogAcceptLoadingClick(AcceptLoadingFromExternalFileDialogFragment.this);
            }
        });
        builder.setNegativeButton(buttonNegativeString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        return builder.create();
    }
}
