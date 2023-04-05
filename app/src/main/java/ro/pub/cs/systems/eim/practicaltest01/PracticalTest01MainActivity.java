package ro.pub.cs.systems.eim.practicaltest01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {
    Button leftButton, rightButton, navigateButton;
    EditText leftText, rightText;
    int leftCount = 0, rightCount = 0;

    private final MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        leftButton = findViewById(R.id.press_me_button);
        rightButton = findViewById(R.id.press_me_too_button);
        leftText = findViewById(R.id.left_edit_text);
        rightText = findViewById(R.id.right_edit_text);

        navigateButton = findViewById(R.id.navigate_button);

        leftButton.setOnClickListener(it -> {
            leftCount++;
            startPracticalTestService();
            leftText.setText(String.valueOf(leftCount));
        });

        rightButton.setOnClickListener(it -> {
            rightCount++;
            startPracticalTestService();
            rightText.setText(String.valueOf(rightCount));
        });

        navigateButton.setOnClickListener(it -> {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
            intent.putExtra(Constants.LEFT_COUNT, leftCount);
            intent.putExtra(Constants.RIGHT_COUNT, rightCount);

            startActivityForResult(intent, Constants.REQUEST_CODE);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.LEFT_COUNT, leftCount);
        outState.putInt(Constants.RIGHT_COUNT, rightCount);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        leftCount = savedInstanceState.getInt(Constants.LEFT_COUNT);
        rightCount = savedInstanceState.getInt(Constants.RIGHT_COUNT);

        leftText.setText(String.valueOf(leftCount));
        rightText.setText(String.valueOf(rightCount));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Result OK", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Result CANCELED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
        getApplicationContext().stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION);
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(messageBroadcastReceiver);
    }

    private void startPracticalTestService() {
        if (leftCount + rightCount == 5) {
            Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
            intent.putExtra(Constants.LEFT_COUNT, leftCount);
            intent.putExtra(Constants.RIGHT_COUNT, rightCount);

            getApplicationContext().startService(intent);
        }
    }

    private static class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.DATA, intent.getStringExtra(Constants.DATA));
        }
    }
}