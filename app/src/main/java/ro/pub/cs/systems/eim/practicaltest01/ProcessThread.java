package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class ProcessThread extends Thread {

    private final Context context;
    private final double arMean;
    private final double geoMean;

    public ProcessThread(Context context, int leftCount, int rightCount) {
        this.context = context;
        this.arMean = (leftCount + rightCount) / 2;
        this.geoMean = Math.sqrt(Math.pow(leftCount, 2) + Math.pow(rightCount, 2));
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, "Thread.run() was invoked, PID: " + android.os.Process.myPid() + " TID: " + android.os.Process.myTid());

        while (true) {
            sendMessage();
            sleep();
        }
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.ACTION);
        intent.putExtra(Constants.DATA,
                new Date(System.currentTimeMillis()) + " " + arMean + " " + geoMean);

        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(Constants.SLEEP_TIME);
        } catch (InterruptedException interruptedException) {
            Log.e(Constants.TAG, interruptedException.getMessage());
            interruptedException.printStackTrace();
        }
    }

}