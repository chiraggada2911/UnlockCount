package co.chiraggada.unlockcount;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static TextView mText;
    private static String TAG = "hugo";

    public static String CountKey;
    public static int Count;

    public static SharedPreferences mPreferences;
    private String sharedPrefFile = "co.chiraggada.unlockcount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerReceiver(new PhoneUnlockedReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));
        mText = findViewById(R.id.text);
        mPreferences = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        Count = mPreferences.getInt(CountKey,0);
        mText.setText(String.format("%s",Count));
        final Button button = findViewById(R.id.go);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,new ComponentName(MainActivity.this,LiveWallpaper.class));
                startActivity(intent);
            }
        });
    }

    public static void cha(int i){

        mText.setText(Integer.toString(i));
        Count = i;
        Log.d(TAG, "onCreate: "+ Count);

    }


    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(CountKey,Count);
        preferencesEditor.commit();
    }
}
