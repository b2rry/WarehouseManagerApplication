package com.appksa.warehousemanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.appksa.warehousemanager.MainActivity;
import com.appksa.warehousemanager.R;

public class AcceptLoadingFromExternalFileDialogFragment extends DialogFragment {
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
                ((MainActivity) requireActivity()).acceptExternalFileLoadingClicked();
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
