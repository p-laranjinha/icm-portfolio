package ua.laranjinha93179.hw1phonedialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SpeedDialEditor extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_dial_editor);

        intent = getIntent();
        String speed_dial_name = intent.getStringExtra(MainActivity.SPEED_DIAL_NAME_MESSAGE);
        String speed_dial_num = intent.getStringExtra(MainActivity.SPEED_DIAL_NUM_MESSAGE);

        EditText edit_speed_dial_name = findViewById(R.id.editSpeedDialName);
        EditText edit_speed_dial_num = findViewById(R.id.editSpeedDialNum);
        edit_speed_dial_name.setText(speed_dial_name);
        edit_speed_dial_num.setText(speed_dial_num);
    }

    public void onResetClick(View view) {
        ((EditText) findViewById(R.id.editSpeedDialName)).setText("");
        ((EditText) findViewById(R.id.editSpeedDialNum)).setText("");
    }

    public void onSaveClick(View view) {
        intent.putExtra("new_speed_dial_id", intent.getStringExtra(MainActivity.SPEED_DIAL_ID_MESSAGE));
        intent.putExtra("new_speed_dial_name", ((EditText) findViewById(R.id.editSpeedDialName)).getText().toString());
        intent.putExtra("new_speed_dial_num", ((EditText) findViewById(R.id.editSpeedDialNum)).getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}