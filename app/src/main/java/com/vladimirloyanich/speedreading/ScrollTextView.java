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
    private int mRndDuration = 60000;

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
        int duration = (new Double(mRndDuration * distance * 1.00000
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

    public int getRndDuration() {
        return mRndDuration;
    }

    public void setRndDuration(int duration) {
        this.mRndDuration=duration;
        /*
        switch (duration){
            case 0:this.mRndDuration=95000;break;
            case 1:this.mRndDuration=95000;break;
            case 2:this.mRndDuration=90000;break;
            case 3:this.mRndDuration=85000;break;
            case 4:this.mRndDuration=80000;break;
            case 5:this.mRndDuration=75000;break;
            case 6:this.mRndDuration=70000;break;
            case 7:this.mRndDuration=65000;break;
            case 8:this.mRndDuration=60000;break;
            case 9:this.mRndDuration=55000;break;
            case 10:this.mRndDuration=50000;break;
            case 11:this.mRndDuration=45000;break;
            case 12:this.mRndDuration=40000;break;
            case 13:this.mRndDuration=35000;break;
            case 14:this.mRndDuration=30000;break;
            case 15:this.mRndDuration=25000;break;
            case 16:this.mRndDuration=20000;break;
            case 17:this.mRndDuration=15000;break;
            case 18:this.mRndDuration=10000;break;
            case 19:this.mRndDuration=5000;break;
            case 20:this.mRndDuration=1000;break;
            case 21:this.mRndDuration=120000;break;
            default:this.mRndDuration=140000;break;
        }
//        this.mRndDuration = duration;*/
    }

    public boolean isPaused() {
        return mPaused;
    }


}
