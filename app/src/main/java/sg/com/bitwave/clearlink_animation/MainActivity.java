package sg.com.bitwave.clearlink_animation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;

import sg.com.bitwave.clearlink_animation.views.TestView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivDeviceUpgrade;
    private ImageView ivTest;
    private Animation fadeIn;
    private Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ivDeviceUpgrade = (ImageView) findViewById(R.id.iv_device_upgrade);
        ivDeviceUpgrade.setTag(R.drawable.anim_1);
        ivTest = (ImageView) findViewById(R.id.iv_test);
        ((AnimationDrawable) ivTest.getDrawable()).start();*/
        //((AnimationDrawable) ivDeviceUpgrade.getDrawable()).start();

        //initAnimation();
        //initShimmerEffect();

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setDuration(3000);
        container.setMaskShape(ShimmerFrameLayout.MaskShape.LINEAR);
        container.setAngle(ShimmerFrameLayout.MaskAngle.CW_270);
        container.startShimmerAnimation();

        TestView tvMask = (TestView) findViewById(R.id.mv_image);
        tvMask.startMaskAnimation();
    }

    private void initShimmerEffect() {

        TranslateAnimation trans = new TranslateAnimation(0, 400, 0, 0);
        trans.setDuration(3000);
        trans.setRepeatCount(Animation.INFINITE);
        trans.setRepeatMode(Animation.RESTART);

        AlphaAnimation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3000);

        AnimationSet combine = new AnimationSet(true);
        //combine.addAnimation(trans);
        combine.addAnimation(alpha);
        ivDeviceUpgrade.startAnimation(combine);

       /* fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(2000);*/
        /*fadeIn.setRepeatCount(1);
        fadeIn.setRepeatMode(Animation.REVERSE);*/
    }
    private void initAnimation() {

        // Fade in animation
        fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        fadeIn.setRepeatCount(1);
        fadeIn.setRepeatMode(Animation.REVERSE);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                int oriId = (Integer) ivDeviceUpgrade.getTag(); //When you fetch the drawable id
                if ( oriId == R.drawable.anim_1 ) {
                    //fadeIn.setStartOffset(1000);
                } else {
                    fadeIn.setStartOffset(0);
                    //fadeIn.setDuration(500);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                changeAnimImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        // Fade out animation
        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);
        //fadeOut.setRepeatCount(1);
        //fadeOut.setRepeatMode(Animation.REVERSE);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                int oriId = (Integer) ivDeviceUpgrade.getTag(); //When you fetch the drawable id
                if ( oriId == R.drawable.anim_1 ) {
                    //fadeOut.setRepeatCount(0);
                } else {
                    //fadeOut.setRepeatCount(1);
                    //fadeOut.setRepeatMode(Animation.REVERSE);
                }
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                changeAnimImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivDeviceUpgrade.startAnimation(fadeOut);
        //AnimationSet animation = new AnimationSet(false); //change to false
        //animation.addAnimation(fadeIn);
        //animation.addAnimation(fadeOut);
        //ivTest.startAnimation(animation);

        /*Animation rotation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
        rotation.setRepeatCount(Animation.INFINITE);
        ivDeviceUpgrade.startAnimation(rotation);*/
    }

    private void changeAnimImage() {
        int oriId = (Integer) ivDeviceUpgrade.getTag(); //When you fetch the drawable id
        int changeId = 0;
        switch ( oriId ) {
            case R.drawable.anim_1:
                changeId = R.drawable.try_2;
                ivDeviceUpgrade.setTag(R.drawable.anim_2);
                break;
            case R.drawable.anim_2:
                changeId = R.drawable.try_3;
                ivDeviceUpgrade.setTag(R.drawable.anim_3);
                break;
            case R.drawable.anim_3:
                changeId = R.drawable.try_1;
                ivDeviceUpgrade.setTag(R.drawable.anim_1);
                break;
        }
        ivDeviceUpgrade.setImageResource(changeId);

        if ( oriId == R.drawable.anim_3 ) {
            ivDeviceUpgrade.startAnimation(fadeOut);
            Log.i("MainActivity", "fadeOut!");
        }
        else {
            ivDeviceUpgrade.startAnimation(fadeIn);
            Log.i("MainActivity", "fadeIn!");
        }
    }
}

