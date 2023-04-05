package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class PracticalTest01Service extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int leftCount = intent.getIntExtra(Constants.LEFT_COUNT, 0);
        int rightCount = intent.getIntExtra(Constants.RIGHT_COUNT, 0);

        ProcessThread processThread = new ProcessThread(this, leftCount, rightCount);
        processThread.start();

        return Service.START_REDELIVER_INTENT;
    }
}
