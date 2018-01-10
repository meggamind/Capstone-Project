package com.example.aniket.capstone_project.ui.post;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.aniket.capstone_project.R;

import timber.log.Timber;

/**
 * Created by aniket on 1/7/18.
 */

public abstract class BaseTaskService extends Service {

    static final int PROGRESS_NOTIFICATION_ID = 0;
    static final int FINISHED_NOTIFICATION_ID = 1;

    private static final String TAG = "MyBaseTaskService";
    private int mNumTasks = 0;

    public void taskStarted() {
        changeNumberOfTasks(1);
    }

    public void taskCompleted() {
        changeNumberOfTasks(-1);
    }

    private synchronized void changeNumberOfTasks(int delta) {
        Timber.d("changeNumberOfTasks:" + mNumTasks + ":" + delta);
        mNumTasks += delta;

        // If there are no tasks left, stop the service
        if (mNumTasks <= 0) {
            Timber.d("stopping");
            stopSelf();
        }
    }

    /**
     * Show notification with a progress bar.
     */
    protected void showProgressNotification(String caption, long completedUnits, long totalUnits) {
        int percentComplete = 0;
        if (totalUnits > 0) {
            percentComplete = (int) (100 * completedUnits / totalUnits);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_file_upload_white_24dp)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(caption)
                .setProgress(100, percentComplete, false)
                .setOngoing(true)
                .setAutoCancel(false);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(PROGRESS_NOTIFICATION_ID, builder.build());
    }

    /**
     * Show notification that the activity finished.
     */
    protected void showFinishedNotification(String caption, Intent intent, boolean success) {
        // Make PendingIntent for notification
        Timber.d("Aniket4, in showFinishedNotification");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* requestCode */, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        int icon = success ? R.drawable.ic_check_white_24 : R.drawable.ic_error_white_24dp;

        Timber.d("Aniket4: " + pendingIntent.toString());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(caption)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        manager.notify(FINISHED_NOTIFICATION_ID, builder.build());
    }

    /**
     * Dismiss the progress notification.
     */
    protected void dismissProgressNotification() {
       Timber.d("Aniket4, dimissProgressNotification");
        NotificationManager manager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        manager.cancel(PROGRESS_NOTIFICATION_ID);
    }
}
