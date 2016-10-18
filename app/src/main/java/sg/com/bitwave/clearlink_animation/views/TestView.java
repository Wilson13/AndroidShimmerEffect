package sg.com.bitwave.clearlink_animation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import sg.com.bitwave.clearlink_animation.R;

public class TestView extends View implements View.OnTouchListener {

    private static final String TAG = "MaskView";

    private Canvas bitmapCanvas;
    private Bitmap bitmapImage;
    private Bitmap renderBitmap;

    private Paint maskPaint;
    private Paint imagePaint;
    private Paint eraserPaint;

    private Drawable srcImage;

    private LinearGradient shader;
    private Rect rect;

    private Handler animateHandler;
    private Runnable animateRunnable;

    private boolean drawCircle = true;
    private int mPosX = 0;
    private int mPosY = 0;
    private int h;
    private int w;
    private int x = 0;
    private int y = 0;
    private int colors[] = {0x00000000, 0xff000000, 0x00000000};
    //private int colors[] = new int[3];

    private float positions[] = {0, 0.5f, 1f};

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.setOnTouchListener(this);
        // real work here
        Log.i(TAG, "in constructor: " + R.styleable.MaskView_srcImage);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaskView, 0, 0);
        srcImage = a.getDrawable(R.styleable.MaskView_srcImage);
        bitmapImage = ((BitmapDrawable)srcImage).getBitmap();

        animateHandler = new Handler();
        animateRunnable = new Runnable() {
            @Override
            public void run() {
                animateMask();
                animateHandler.postDelayed(this, 0);
            }
        };

        // The last drawn is the source. If this bitmap is painted on empty canvas, the empty canvas is the dst.
        imagePaint = new Paint();
        //imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //imagePaint.setAlpha(255);
        //imagePaint.setColor(Color.TRANSPARENT);

        // The last drawn is the source. If this mask is painted on bitmap canvas, the bitmap canvas is the dst.
        maskPaint = new Paint();
        maskPaint.setColor(Color.TRANSPARENT);
        //maskPaint.setAlpha(255);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        renderBitmap = Bitmap.createBitmap( bitmapImage.getWidth(), bitmapImage.getHeight(), Bitmap.Config.ARGB_8888);

        // Set up canvas
        bitmapCanvas = new Canvas(renderBitmap);
        bitmapCanvas.drawBitmap(bitmapImage, 0, 0, null);

        Shader shader = new BitmapShader(renderBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        eraserPaint = new Paint();
        eraserPaint.setShader(shader);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //Log.i(TAG, "in onDraw");

        canvas.drawBitmap(renderBitmap, 0, 0, null);
        //canvas.drawColor(Color.GRAY);
        canvas.drawColor(Color.parseColor("#802c3e50")); // 40% transparency/ 60% opacity
        if ( drawCircle ) {
            canvas.drawRect(0, y, bitmapImage.getWidth(), y+bitmapImage.getHeight()/3, eraserPaint);
            //canvas.drawCircle(x, y, 40, eraserPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension( bitmapImage.getWidth(), bitmapImage.getHeight() );
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //Log.i("TextView", "onTouch!x, y: "+ x + " " + y);
        //x = (int) motionEvent.getX();
        //y = (int) motionEvent.getY();
        //drawCircle = true;
        //invalidate();
        return true;
    }

    private void animateMask() {
        //Log.i(TAG, "y: " + y);
        if ( y >= bitmapImage.getHeight() ) {
            y = -bitmapImage.getHeight()/2;
        } else
            y+=2;
        invalidate();
    }

    public void startMaskAnimation() {
        animateHandler.post(animateRunnable);
    }
}
