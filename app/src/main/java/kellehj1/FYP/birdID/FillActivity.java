package kellehj1.FYP.birdID;

import androidx.appcompat.app.AppCompatActivity;
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
    private Bitmap mask, original, colored;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill);
        //findViewById(R.id.imageView1).setOnTouchListener(this);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setOnTouchListener(this);

        mask = BitmapFactory.decodeResource(getResources(), R.drawable.test_fill_mask); // Mask Image
        original = BitmapFactory.decodeResource(getResources(), R.drawable.test_fill_outline); // Original Image Without Color
        colored = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);

        cv = new Canvas(colored);
        cv.drawBitmap(original, 0,0, null);
        imageView.setImageBitmap(original);
    }

    int ANTIALIASING_TOLERANCE = 70;

    public boolean onTouch(View arg0, MotionEvent arg1) {
        //mask = BitmapFactory.decodeResource(getResources(), R.drawable.mask);
        int selectedColor = mask.getPixel((int)arg1.getX(),(int)arg1.getY());
        int sG = (selectedColor & 0x0000FF00) >> 8;
        int sR = (selectedColor & 0x00FF0000) >> 16;
        int sB = (selectedColor & 0x000000FF);
        System.out.println("SG :"+((selectedColor & 0x0000FF00) >> 8));
        System.out.println("SR :"+((selectedColor & 0x00FF0000) >> 16));
        System.out.println("SB :"+(selectedColor & 0x000000FF));

        //original = BitmapFactory.decodeResource(getResources(), R.drawable.original);
        //colored = Bitmap.createBitmap(mask.getWidth(), mask.getHeight(), Config.ARGB_8888);

        Point point = new Point((int) arg1.getX(), (int) arg1.getY());
        colored = FloodFill(colored, point, Color.WHITE, Color.RED);
        /*
        for(int x = 0; x < mask.getWidth(); x++){
            for(int y = 0; y < mask.getHeight(); y++){

                int g = (mask.getPixel(x,y) & 0x0000FF00) >> 8;
                int r = (mask.getPixel(x,y) & 0x00FF0000) >> 16;
                int b = (mask.getPixel(x,y) & 0x000000FF);

                //System.out.println("r: "+r+", g: "+g+", b: "+b);


                if(Math.abs(sR - r) < ANTIALIASING_TOLERANCE && Math.abs(sG - g) < ANTIALIASING_TOLERANCE && Math.abs(sB - b) < ANTIALIASING_TOLERANCE)
                    colored.setPixel(x, y, (colored.getPixel(x, y) & 0xFFFF0000));
            }
        }
        /*
         */

        imageView.setImageBitmap(colored);
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