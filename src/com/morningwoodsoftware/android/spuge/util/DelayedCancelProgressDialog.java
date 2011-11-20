package com.morningwoodsoftware.android.spuge.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DelayedCancelProgressDialog
{
    public static void showDialog(Context context, String title,
            String message, String buttonText)
    {
        ProgressDialog cancelDialog = new ProgressDialog(context);
        cancelDialog.setTitle(title);
        cancelDialog.setMessage(message);
        cancelDialog.setButton(buttonText,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Use either finish() or return() to either close the
                        // activity or just the dialog
                        return;
                    }
                });
        cancelDialog.show();
    }
}
