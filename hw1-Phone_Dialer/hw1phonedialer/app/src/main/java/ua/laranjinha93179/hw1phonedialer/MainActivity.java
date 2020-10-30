package ua.laranjinha93179.hw1phonedialer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;

public class MainActivity extends AppCompatActivity {

    EditText editTextPhone;
    SharedPreferences savedata;
    public static final String SPEED_DIAL_ID_MESSAGE = "ua.laranjinha93179.hw1phonedialer.SPEED_DIAL_ID";
    public static final String SPEED_DIAL_NAME_MESSAGE = "ua.laranjinha93179.hw1phonedialer.SPEED_DIAL_NAME";
    public static final String SPEED_DIAL_NUM_MESSAGE = "ua.laranjinha93179.hw1phonedialer.SPEED_DIAL_NUM";

    View.OnLongClickListener longspeedlistener = (view) -> {
        switch (view.getId()) {
            case R.id.speeddial1:
                speedDialLongClick("1"); break;
            case R.id.speeddial2:
                speedDialLongClick("2"); break;
            case R.id.speeddial3:
                speedDialLongClick("3"); break;
        }
        return true;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        Button s1 = (Button) findViewById(R.id.speeddial1);
        s1.setOnLongClickListener(longspeedlistener);
        Button s2 = (Button) findViewById(R.id.speeddial2);
        s2.setOnLongClickListener(longspeedlistener);
        Button s3 = (Button) findViewById(R.id.speeddial3);
        s3.setOnLongClickListener(longspeedlistener);

        savedata = getPreferences(Context.MODE_PRIVATE);

        s1.setHint(savedata.getString("speed_dial_1_name","Not set"));
        s2.setHint(savedata.getString("speed_dial_2_name","Not set"));
        s3.setHint(savedata.getString("speed_dial_3_name","Not set"));
    }

    public void onNumButtonClick(View view) {
        Button b = (Button) view;
        String num = b.getHint().toString();
        editTextPhone.append(num);
    }

    public void onDelButtonClick(View view) {
        Editable phonetext = editTextPhone.getText();
        if (phonetext.length() > 0)
            editTextPhone.setText(phonetext.subSequence(0,phonetext.length()-1));
    }

    public void onDialButtonClick(View view) {
        makePhoneCall(editTextPhone.getText().toString());
    }

    public void onSpeedDialClick(View view) {
        Button sd = (Button) view;
        String dialnum = "0";
        switch (sd.getId()) {
            case R.id.speeddial1:
                dialnum = "1"; break;
            case R.id.speeddial2:
                dialnum = "2"; break;
            case R.id.speeddial3:
                dialnum = "3"; break;
        }
        String phonenum = savedata.getString("speed_dial_"+dialnum+"_num","");
        if (!phonenum.equals(""))
            makePhoneCall(phonenum);
    }

    public void makePhoneCall(String phonenum) {
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PERMISSION_DENIED) // not sure if this condition is necessary
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},1);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + Uri.encode(phonenum)));
        startActivity(intent);
    }

    public void speedDialLongClick(String i) {
        Intent intent = new Intent(this, SpeedDialEditor.class);
        intent.putExtra(SPEED_DIAL_ID_MESSAGE, i);
        intent.putExtra(SPEED_DIAL_NAME_MESSAGE, savedata.getString("speed_dial_"+i+"_name",""));
        intent.putExtra(SPEED_DIAL_NUM_MESSAGE, savedata.getString("speed_dial_"+i+"_num",""));
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) return;
            SharedPreferences.Editor editor = savedata.edit();
            String id = data.getStringExtra("new_speed_dial_id");
            String name = data.getStringExtra("new_speed_dial_name");
            String num = data.getStringExtra("new_speed_dial_num");
            Button b = null;
            switch (id) {
                case "1":
                    b = (Button) findViewById(R.id.speeddial1); break;
                case "2":
                    b = (Button) findViewById(R.id.speeddial2); break;
                case "3":
                    b = (Button) findViewById(R.id.speeddial3); break;
            }
            if (name.equals("") && num.equals("")) {
                editor.remove("speed_dial_"+id+"_name");
                editor.remove("speed_dial_"+id+"_num");
                b.setHint("Not set");
            } else {
                editor.putString("speed_dial_"+id+"_name", name);
                editor.putString("speed_dial_"+id+"_num", num);
                b.setHint(name);
            }
            editor.apply();
        }
    }
}