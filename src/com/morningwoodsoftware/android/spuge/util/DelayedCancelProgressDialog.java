package com.morningwoodsoftware.android.spuge.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DelayedCancelProgressDialog
{
    private static ProgressDialog cancelDialog;

    public void showDialog(Context context, String title, String message,
            String buttonText, long delayMs, DelayCancelProgressDialogListener l)
    {
        // Start thread
        final DelayThread delayThread = new DelayThread(delayMs, l);

        cancelDialog = new ProgressDialog(context);
        cancelDialog.setTitle(title);
        cancelDialog.setMessage(message);
        cancelDialog.setButton(buttonText,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // Close the dialog
                        delayThread.cancel();
                        return;
                    }
                });
        cancelDialog.show();
        delayThread.start();
    }

    class DelayThread extends Thread
    {
        private long delayMs;
        private DelayCancelProgressDialogListener l;
        private boolean cancel;

        public DelayThread(long delayMs, DelayCancelProgressDialogListener l)
        {
            this.delayMs = delayMs;
            this.l = l;
        }

        public void cancel()
        {
            this.cancel = true;
        }

        @Override
        public void run()
        {
            try
            {
                Thread.sleep(delayMs);

                if (!this.cancel)
                {
                    // Close popup
                    cancelDialog.dismiss();
                    
                    l.onComplete();
                }

            }
            catch (Exception e)
            {

                // TODO
            }
        }

    }
}
