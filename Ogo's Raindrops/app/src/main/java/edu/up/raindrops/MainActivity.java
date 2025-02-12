package edu.up.raindrops;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    // initialize variables
    public Random random = new Random();
    public int offsetX = 0;
    public int offsetY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize variables and views
        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        SeekBar seekBarDownUp = findViewById(R.id.seekBarDownUp);
        SeekBar seekBarLeftRight = findViewById(R.id.seekBarLeftRight);
        TextView labelDown = findViewById(R.id.labelDown);
        TextView labelUp = findViewById(R.id.labelUp);
        TextView labelLeft = findViewById(R.id.labelLeft);
        TextView labelRight = findViewById(R.id.labelRight);

        // Set up SeekBar listeners for moving the raindrops
        // I used API for this class because I was confused on drawing the raindrops.
        seekBarDownUp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                offsetY = progress;
                surfaceView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // I used API for this class because I was confused on drawing the raindrops.
        seekBarLeftRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                offsetX = progress;
                surfaceView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Set up SurfaceView to draw raindrops
        // I used API for this class because I was confused on drawing the raindrops.
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                drawRaindrops(holder);
            }

            private void createRaindrops() {
                int numRaindrops = random.nextInt(7) + 6;  // Random between 6 and 12 raindrops
                ArrayList<Raindrop> raindrops = new ArrayList<>();
                for (int i = 0; i < numRaindrops; i++) {
                    int x = random.nextInt(800);
                    int y = random.nextInt(800);
                    int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    raindrops.add(new Raindrop(x, y, color));
                }
            }

            // I used API for this class because I was confused on drawing the raindrops.
            private class drawRaindrops(SurfaceHolder holder) {
                final Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.WHITE);  // Clear canvas

                    Paint paint = new Paint();
                    for (Raindrop raindrop : raindrops) {
                        paint.setColor(raindrop.color);
                        canvas.drawCircle(raindrop.x + offsetX, raindrop.y + offsetY, 30, paint);
                    }

                    holder.unlockCanvasAndPost(canvas);
                }
            }
            private class Raindrop {

                // initialize variables
                public final int x;
                public final int y;
                public final int color;

                Raindrop(int x, int y, int color) {
                    this.x = x;
                    this.y = y;
                    this.color = color;
                }

            }
}
    }
}