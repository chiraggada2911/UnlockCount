package co.chiraggada.unlockcount;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneUnlockedReceiver extends BroadcastReceiver {

    String TAG = "TEST";

    @Override

    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            MainActivity c = new MainActivity();
            //phone was unlocked, do stuff here
            Log.d(TAG, "onReceive: phone unlocked ");
            c.Count = c.Count+1;
            Log.d(TAG,Integer.toString(c.Count));
            MainActivity.cha(c.Count);
        }
    }


}
