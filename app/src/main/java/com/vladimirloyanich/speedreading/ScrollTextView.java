package com.vladimirloyanich.speedreading;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;

import java.security.PublicKey;

/**
 * Created by Владимир on 14.06.2015.
 */
public class ScrollTextView extends TextView {

    // scrolling feature
    private Scroller mSlr;

    // milliseconds for a round of scrolling
    private double mRndDuration;

    // the X offset when paused
    private int mXPaused = 0;

    // whether it's being paused
    private boolean mPaused = true;

    /*
    * constructor
    */


    public ScrollTextView(Context context) {
        this(context, null);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /*
    * constructor
    */
    public ScrollTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /*
    * constructor
    */
    public ScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // customize the TextView
        setSingleLine();
        setEllipsize(null);
        setVisibility(INVISIBLE);
    }

    /**
     * begin to scroll the text from the original position
     */
    public void startScroll() {
        // begin from the very right side
        mXPaused = -1 * getWidth();
        // assume it's paused
        mPaused = true;
        resumeScroll();
    }

    /**
     * resume the scroll from the pausing point
     */
    public void resumeScroll() {

        if (!mPaused)
            return;

        // Do not know why it would not scroll sometimes
        // if setHorizontallyScrolling is called in constructor.
        setHorizontallyScrolling(true);

        // use LinearInterpolator for steady scrolling
        mSlr = new Scroller(this.getContext(), new LinearInterpolator());
        setScroller(mSlr);

        int scrollingLen = calculateScrollingLen();
        int distance = scrollingLen - (getWidth() + mXPaused);
        int duration = (Double.valueOf(mRndDuration * distance * 1.00000
                / scrollingLen)).intValue();

        setVisibility(VISIBLE);

        mSlr.startScroll(mXPaused, 0, distance, 0, duration);
        invalidate();
        mPaused = false;
    }
public int getPozition (){
    return getWidth();
}

    /**
     * calculate the scrolling length of the text in pixel
     *
     * @return the scrolling length in pixels
     */
    private int calculateScrollingLen() {
        TextPaint tp = getPaint();
        Rect rect = new Rect();
        String strTxt = getText().toString();
        tp.getTextBounds(strTxt, 0, strTxt.length(), rect);
        int scrollingLen = rect.width() + getWidth();
        rect = null;
        return scrollingLen;
    }

    /**
     * pause scrolling the text
     */
    public void pauseScroll() {
        if (null == mSlr)
            return;

        if (mPaused)
            return;

        mPaused = true;

        // abortAnimation sets the current X to be the final X,
        // and sets isFinished to be true
        // so current position shall be saved
        mXPaused = mSlr.getCurrX();

        mSlr.abortAnimation();
    }

    @Override
     /*
     * override the computeScroll to restart scrolling when finished so as that
     * the text is scrolled forever
     */
    public void computeScroll() {
        super.computeScroll();

        if (null == mSlr) return;

        if (mSlr.isFinished() && (!mPaused)) {
            this.startScroll();
        }
    }

    public double getRndDuration() {
        return mRndDuration;
    }

    public void setRndDuration(int progress, double StringLength) {
        double time=60000;
        switch (progress){
            case 0:this.mRndDuration=time*(StringLength/900);break;
            case 1:this.mRndDuration=time*(StringLength/1000);break;
            case 2:this.mRndDuration=time*(StringLength/1100);break;
            case 3:this.mRndDuration=time*(StringLength/1200);break;
            case 4:this.mRndDuration=time*(StringLength/1300);break;
            case 5:this.mRndDuration=time*(StringLength/1400);break;
            case 6:this.mRndDuration=time*(StringLength/1500);break;
            case 7:this.mRndDuration=time*(StringLength/1600);break;
            case 8:this.mRndDuration=time*(StringLength/1700);break;
            case 9:this.mRndDuration=time*(StringLength/1800);break;
            case 10:this.mRndDuration=time*(StringLength/1900);break;
            case 11:this.mRndDuration=time*(StringLength/2000);break;
            case 12:this.mRndDuration=time*(StringLength/2100);break;
            case 13:this.mRndDuration=time*(StringLength/2200);break;
            case 14:this.mRndDuration=time*(StringLength/2300);break;
            case 15:this.mRndDuration=time*(StringLength/2400);break;
            case 16:this.mRndDuration=time*(StringLength/2500);break;
            case 17:this.mRndDuration=time*(StringLength/2600);break;
            case 18:this.mRndDuration=time*(StringLength/2700);break;
            case 19:this.mRndDuration=time*(StringLength/3000);break;
            case 20:this.mRndDuration=time*(StringLength/5000);break;
            case 21:this.mRndDuration=time*(StringLength/10000);break;
//            default:this.mRndDuration=time*(StringLength/900);break;
        }
//        this.mRndDuration = duration;
    }

    public boolean isPaused() {
        return mPaused;
    }


}
