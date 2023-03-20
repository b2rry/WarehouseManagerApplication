package com.appksa.warehousemanager.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.appksa.warehousemanager.R;
import com.appksa.warehousemanager.SupplyItemActivity;

public class AcceptDeletionSupplyItemDialogFragment extends DialogFragment{

    public interface DeletionSupplyItemDialogListener {
        public void onDialogAcceptDeletionClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    AcceptDeletionSupplyItemDialogFragment.DeletionSupplyItemDialogListener listener;

    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AcceptDeletionSupplyItemDialogFragment.DeletionSupplyItemDialogListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        String title = getString(R.string.delete_supply_item_dialog_title_message);
        String message = getString(R.string.delete_dialog_message_message);
        String buttonPositiveString = getString(R.string.delete_dialog_action_first_message);
        String buttonNegativeString = getString(R.string.delete_dialog_action_second_message);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton(buttonPositiveString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onDialogAcceptDeletionClick(AcceptDeletionSupplyItemDialogFragment.this);
            }
        });
        builder.setNegativeButton(buttonNegativeString, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}