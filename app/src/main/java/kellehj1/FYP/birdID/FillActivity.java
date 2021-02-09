package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.view.View.OnTouchListener;

import kellehj1.FYP.birdID.R;

import java.util.LinkedList;
import java.util.Queue;

public class FillActivity extends AppCompatActivity implements OnTouchListener {
    /**
     * Called when the activity is first created.
     */
    private ImageView imageView;
    private Canvas cv;
    private Bitmap mask, maskScaled, original, originalScaled, coloured;

    int screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        //findViewById(R.id.imageView1).setOnTouchListener(this);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setOnTouchListener(this);

        mask = BitmapFactory.decodeResource(getResources(), R.drawable.test_fill_mask2); // Mask Image
        maskScaled = Bitmap.createScaledBitmap(mask, screenWidth, screenWidth, true);
        original = BitmapFactory.decodeResource(getResources(), R.drawable.test_fill_outline2); // Original Image Without Color
        originalScaled = Bitmap.createScaledBitmap(original, screenWidth, screenWidth, true);
        coloured = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);
        coloured = Bitmap.createScaledBitmap(coloured, screenWidth, screenWidth, true);

        cv = new Canvas(coloured);
        cv.drawBitmap(originalScaled, 0,0, null);
        imageView.setImageBitmap(originalScaled);
    }

    int ANTIALIASING_TOLERANCE = 50;

    public boolean onTouch(View arg0, MotionEvent arg1) {
        int selectedPixel = maskScaled.getPixel((int)arg1.getX(),(int)arg1.getY());
        int redValue = Color.red(selectedPixel);
        int greenValue = Color.green(selectedPixel);
        int blueValue = Color.blue(selectedPixel);

        //original = BitmapFactory.decodeResource(getResources(), R.drawable.original);
        //colored = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);

        Point point = new Point((int) arg1.getX(), (int) arg1.getY());
        coloured = FloodFill(coloured, point, Color.WHITE, Color.RED);

        imageView.setImageBitmap(coloured);
        imageView.invalidate();
        return true;
    }

    private Bitmap FloodFill(Bitmap bmp, Point pt, int targetColor, int replacementColor) {
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);
            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));
                w.x--;
            }
            while ((e.x < bmp.getWidth() - 1)
                    && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);

                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1)
                        && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));
                e.x++;
            }
        }
        return bmp;
    }
}