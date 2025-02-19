package edu.up.raindrops;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    SurfaceView mySurfaceView = findViewById(R.id.surfaceView);
    SeekBar seekBarLeftRight = findViewById(R.id.seekBarLeftRight);
    SeekBar seekBarUpDown = findViewById(R.id.seekBarDownUp);
    TextView labelLeft = findViewById(R.id.labelLeft);
    TextView labelRight = findViewById(R.id.labelRight);
    TextView labelUp = findViewById(R.id.labelUp);
    TextView labelDown = findViewById(R.id.labelDown);
    private List<Raindrop> raindrops = new ArrayList<>();
    private Raindrop mainRaindrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the SurfaceView drawing
        mySurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                generateRaindrops();
                drawRaindrops(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {}
        });

        // Set up SeekBars
        seekBarLeftRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mainRaindrop != null) {
                    mainRaindrop.setX(progress);
                    redraw();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBarUpDown.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mainRaindrop != null) {
                    mainRaindrop.setY(progress);
                    redraw();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // Generate raindrops
    private void generateRaindrops() {
        Random random = new Random();
        int numRaindrops = 6 + random.nextInt(7); // 6 to 12 raindrops

        for (int i = 0; i < numRaindrops; i++) {
            int x = random.nextInt(801); // Random X position
            int y = random.nextInt(801); // Random Y position
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            raindrops.add(new Raindrop(x, y, 30, color));
        }

        // Pick one raindrop to be the main raindrop
        mainRaindrop = raindrops.get(random.nextInt(raindrops.size()));

        // Initialize SeekBar positions based on the main raindrop
        seekBarLeftRight.setProgress(mainRaindrop.getX());
        seekBarUpDown.setProgress(mainRaindrop.getY());
    }

    // Redraw the SurfaceView
    private void redraw() {
        SurfaceHolder holder = SurfaceView.getHolder();
        Canvas canvas = holder.lockCanvas();
        if (canvas != null) {
            canvas.drawColor(Color.WHITE); // Clear the canvas
            for (Raindrop raindrop : raindrops) {
                raindrop.draw(canvas);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    // Raindrop class
    static class Raindrop {
        private int x;
        private int y;
        private final int radius;
        private int color;

        public Raindrop(int x, int y, int radius, int color) {
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.color = color;
        }

        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setColor(color);
            canvas.drawCircle(x, y, radius, paint);
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public void absorb(Raindrop other) {
            // Calculate the average color of two raindrops
            int r = (Color.red(color) + Color.red(other.color)) / 2;
            int g = (Color.green(color) + Color.green(other.color)) / 2;
            int b = (Color.blue(color) + Color.blue(other.color)) / 2;
            this.color = Color.rgb(r, g, b);
        }
    }
    private void drawRaindrops(Canvas canvas) {
        // Loop through each raindrop and draw it
        for (Raindrop raindrop : raindrops) {
            raindrop.draw(canvas);  // Call the draw method on each raindrop
        }
    }
}