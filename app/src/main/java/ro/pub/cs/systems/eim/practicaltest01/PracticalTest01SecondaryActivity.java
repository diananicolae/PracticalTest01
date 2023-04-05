package ro.pub.cs.systems.eim.practicaltest01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    EditText resultText;

    Button okButton, cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        resultText = findViewById(R.id.result_text);
        okButton = findViewById(R.id.ok_button);
        cancelButton = findViewById(R.id.cancel_button);

        okButton.setOnClickListener(it -> {
            setResult(RESULT_OK);
            finish();
        });

        cancelButton.setOnClickListener(it -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if (extras == null) {
                resultText.setText(String.valueOf(0));
            } else {
                resultText.setText(String.valueOf(extras.getInt(Constants.LEFT_COUNT) + extras.getInt(Constants.RIGHT_COUNT)));
            }
        } else {
            resultText.setText(savedInstanceState.getInt(Constants.RESULT_TEXT));
        }
    }
}