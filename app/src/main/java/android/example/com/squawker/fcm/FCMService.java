package android.example.com.squawker.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.example.com.squawker.MainActivity;
import android.example.com.squawker.R;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.example.com.squawker.provider.SquawkContract.COLUMN_AUTHOR;
import static android.example.com.squawker.provider.SquawkContract.COLUMN_AUTHOR_KEY;
import static android.example.com.squawker.provider.SquawkContract.COLUMN_DATE;
import static android.example.com.squawker.provider.SquawkContract.COLUMN_MESSAGE;
import static android.example.com.squawker.provider.SquawkProvider.SquawkMessages.CONTENT_URI;

/**
 * Created by sudharti on 11/14/17.
 */
public class FCMService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Map<String, String> data = remoteMessage.getData();
        String message = data.get("message");

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1234, intent, PendingIntent.FLAG_ONE_SHOT);

        Notification notification =
                new Notification.Builder(this)
                        .setAutoCancel(true)
                        .setContentText(message)
                        .setSmallIcon(R.drawable.ic_duck)
                        .setStyle(new Notification.InboxStyle())
                        .setContentIntent(pendingIntent)
                        .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1203 /* ID of notification */, notification);

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AUTHOR, data.get("author"));
        cv.put(COLUMN_AUTHOR_KEY, data.get("authorKey"));
        cv.put(COLUMN_MESSAGE, data.get("message"));
        cv.put(COLUMN_DATE, data.get("date"));

        ContentResolver contentResolver = this.getContentResolver();
        contentResolver.insert(CONTENT_URI, cv);
    }
}
