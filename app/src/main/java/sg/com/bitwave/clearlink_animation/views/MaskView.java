package sg.com.bitwave.clearlink_animation.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import sg.com.bitwave.clearlink_animation.R;

public class MaskView extends View {

    private static final String TAG = "MaskView";

    private Canvas bitmapCanvas;
    private Bitmap bitmapImage;
    private Bitmap maskBitmap;

    private final Paint maskPaint;
    private final Paint imagePaint;
    private final Paint shaderPaint;

    private Drawable srcImage;
    private Drawable maskDrawable;

    private LinearGradient shader;

    private int mPosX = 0;
    private int mPosY = 0;
    private int h;
    private int w;
    private int colors[] = {0x00000000, 0xff000000, 0x00000000};
    //private int colors[] = new int[3];
    private float positions[] = {0, 0.5f, 1f};

    public MaskView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // real work here
        Log.i(TAG, "in constructor: " + R.styleable.MaskView_srcImage);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaskView, 0, 0);
        srcImage = a.getDrawable(R.styleable.MaskView_srcImage);
        maskDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.circle, null);
        bitmapImage = ((BitmapDrawable)srcImage).getBitmap();
        //bitmapImage = BitmapFactory.decodeResource(context.getResources(), srcImage);

        // Get background color
        /*int color = Color.TRANSPARENT;
        Drawable background = this.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();*/

        /*colors[0] = 0x55 << 12 | color;
        colors[1] = Color.TRANSPARENT;
        colors[2] = Color.TRANSPARENT;*/

        // Set up shader (LinearGradient)
        h = bitmapImage.getHeight();
        w = bitmapImage.getWidth();
        //shader = new LinearGradient(0,  h - 200, 0, h, 0xFFFFFFFF, 0x00FFFFFF, Shader.TileMode.CLAMP);
        shader = new LinearGradient(0, 0, 0, h, colors, null, Shader.TileMode.CLAMP);

        shaderPaint = new Paint();
        shaderPaint.setShader(shader);
        shaderPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        // The last drawn is the source. If this mask is painted on bitmap canvas, the bitmap canvas is the dst.
        maskPaint = new Paint();
        maskPaint.setColor(Color.TRANSPARENT);
        //maskPaint.setAlpha(255);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //maskBitmap = drawableToBitmap(maskDrawable);//BitmapFactory.decodeResource(context.getResources(), R.drawable.circle); //

        //maskBitmap = ((BitmapDrawable)maskImage).getBitmap();
        maskBitmap = Bitmap.createBitmap( bitmapImage.getWidth(), bitmapImage.getHeight()/2, Bitmap.Config.ARGB_8888);
        //maskBitmap.eraseColor(Color.parseColor("#85FFFFFF"));
        //maskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle);

        // The last drawn is the source. If this bitmap is painted on empty canvas, the empty canvas is the dst.
        imagePaint = new Paint();
        imagePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        imagePaint.setAlpha(255);
        //imagePaint.setColor(Color.TRANSPARENT);

        // Set up canvas
        bitmapCanvas = new Canvas(maskBitmap);
        bitmapCanvas.drawColor(Color.WHITE);

    }

    private Bitmap drawableToBitmap (Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(bitmapImage.getWidth(), bitmapImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i(TAG, "in onDraw");

        bitmapCanvas.drawBitmap(maskBitmap, 0, 0, maskPaint);
        //super.dispatchDraw(bitmapCanvas);
        bitmapCanvas.drawBitmap(bitmapImage, 0, 0, imagePaint);
        bitmapCanvas.drawColor(0, PorterDuff.Mode.SRC_OVER);
        canvas.drawBitmap(maskBitmap, 0, 0, null);
        //canvas.save();

        //bitmapCanvas.drawCircle(bitmapImage.getWidth()/2, bitmapImage.getHeight()/2, 40, imagePaint);
        //bitmapCanvas.drawBitmap(bitmapImage, 0 , 0, imagePaint);
        /*canvas.drawBitmap(bitmapImage, 0, 0, null);
        canvas.drawCircle(bitmapImage.getWidth()/2, bitmapImage.getHeight()/2, 40, maskPaint);*/

        //canvas.drawBitmap(maskBitmap, 0, 0, null);

        //canvas.drawRect(0, 0, w, h, shaderPaint);
        /*if ( maskBitmap != null )
            canvas.drawBitmap(maskBitmap, 0, 0, maskPaint);*/

        //Canvas tempCanvas = new Canvas(maskBitmap);
        //Shader shader = new LinearGradient(0, 0, 0, 40, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP);

        // For drawable
        /*maskDrawable.setBounds( 0, 0, bitmapImage.getWidth(), bitmapImage.getHeight() );
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPaint(maskPaint);
        maskDrawable.draw(canvas);*/

       /* Bitmap result = Bitmap.createBitmap(bitmapImage.getWidth(), bitmapImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        tempCanvas.drawBitmap(bitmapImage, 0, 0, null);
        tempCanvas.drawBitmap(maskBitmap, 0, 0, maskPaint);
        paint.setXfermode(null);*/

        //Draw result after performing masking
        //canvas.drawBitmap(result, 0, 0, new Paint());


        //canvas.drawText("Hello World!", 10, 10, imagePaint);
        //canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        setMeasuredDimension( bitmapImage.getWidth(), bitmapImage.getHeight() );
    }
}
