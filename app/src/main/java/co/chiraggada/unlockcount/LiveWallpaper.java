package co.chiraggada.unlockcount;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class LiveWallpaper extends WallpaperService {

    static int overlayIndex = 0;
    Typeface typeface;
    int xPos = 0, yPos = 0;
    static Bitmap overlayBitmap[] = new Bitmap[5];
    Paint alphaPaint;
    String Counting;
    SharedPreferences preferences;
    Context context;

    @Override
    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }

    private class WallpaperEngine extends Engine {

        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        private Paint paint = new Paint();
        private int width;
        private int height;
        private boolean visible = true;

        public WallpaperEngine() {

            preferences = PreferenceManager.getDefaultSharedPreferences(LiveWallpaper.this);

            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(100);
            paint.setDither(true);

            alphaPaint = new Paint();
            alphaPaint.setAlpha(80);

            handler.post(drawRunner);
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        private void draw() {
//            typeface = ResourcesCompat.getFont(context, R.font.goudysto);
//            paint.setTypeface(typeface);
            Counting = Integer.toString(MainActivity.Count);
            String Message = preferences.getString(Const.Message,"hi");
          Counting = preferences.getString("CountKey", "null");
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            int color = Color.parseColor("#000000" );
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {

                    canvas.drawColor(color);

                    if (overlayIndex > 0) {
                        canvas.drawBitmap(overlayBitmap[overlayIndex - 1], 0, 0, alphaPaint);
                    }
                    xPos = (canvas.getWidth() / 2) - (int) (paint.measureText(Counting) / 2);
                    yPos = (int) ((canvas.getHeight() / 2) - ((paint.ascent() + paint.descent()) / 2));
                    canvas.drawText(Message, xPos, yPos, paint);


                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }

            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, 1000);
            }
        }

    }
}