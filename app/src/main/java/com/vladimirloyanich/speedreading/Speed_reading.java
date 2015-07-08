package com.vladimirloyanich.speedreading;

import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


/**
 * Created by Владимир on 18.06.2015.
 */
public class Speed_reading extends ActionBarActivity implements SeekBar.OnSeekBarChangeListener, Animation.AnimationListener {
    private static final String User_Name="com.vladimirloyanich.speedreading.username";
    private Animation animStart, animResume;
    private ImageView imageBook;
    private boolean startTest=false;
    private CountDownTimer timer;
    private TextView stringTimer,user,userResult;
    private Button startButton;
    private int counterStartStop=1;
    private RelativeLayout storageView;
    private SeekBar SeekBarView;
    private ScrollTextView ScrollView;
    RelativeLayout.LayoutParams ScrollTextViewParams,SeekBarTextViewParams,TextViewParams,TextViewResultParams;
    private String textScrolling, UserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speed_reading);
        //get data from intent
        UserName = getIntent().getStringExtra(User_Name);
        //link initialization
        imageBook = (ImageView) findViewById(R.id.Book);
        animStart = AnimationUtils.loadAnimation(this, R.anim.bookalpha);
        animStart.setAnimationListener(this);
        animStart.setFillAfter(true);
        animResume = AnimationUtils.loadAnimation(this, R.anim.bookalpharesume);
        animResume.setFillAfter(true);
        animResume.setAnimationListener(this);
        stringTimer = (TextView) findViewById(R.id.stringTimer);
        startButton = (Button) findViewById(R.id.button_Start);
        // load text for test from Storage class
        textScrolling = new StorageClass().getStorageText();
        //create link on RelativeLayout to add widget
        storageView = (RelativeLayout) findViewById(R.id.storage);

        //create widget text view user name
        user = (TextView) LayoutInflater.from(this).inflate(R.layout.textviewuser, null);
        storageView.addView(user);
        user.setText(getResources().getString(R.string.UserTest) + " " +UserName);
        TextViewParams = (RelativeLayout.LayoutParams) user.getLayoutParams();
        TextViewParams.leftMargin = (int) getResources().getDimension(R.dimen.TextViewLeftMargin);
        //create widget text view user Result test
        userResult = (TextView) LayoutInflater.from(this).inflate(R.layout.textviewuserresult, null);
        storageView.addView(userResult);
        userResult.setText(getResources().getString(R.string.UserTestResult) + "");
        TextViewResultParams = (RelativeLayout.LayoutParams) userResult.getLayoutParams(); //add
        TextViewResultParams.leftMargin = (int) getResources().getDimension(R.dimen.TextViewLeftMargin);
        TextViewResultParams.topMargin = (int) getResources().getDimension(R.dimen.TextViewTopMargin);
    }

    public void ClickButtonStart(View view){
        startButton.setVisibility(View.GONE);
        counterStartStop++;
        //true if click Start Button
        if((counterStartStop%2)==0){
            imageBook.startAnimation(animStart);
            storageView.removeView(user);
            storageView.removeView(userResult);
        //false if click Stop Button
        }else {
            storageView.removeView(ScrollView);
            storageView.removeView(SeekBarView);
            timer.cancel();
            stringTimer.setText("");
            startTest=false;
            imageBook.startAnimation(animResume);

        }
    }
    private void finishTest(){
        counterStartStop++;
        storageView.removeView(ScrollView);
        storageView.removeView(SeekBarView);
        timer.cancel();
        stringTimer.setText("");
        startButton.setVisibility(View.GONE);
        startTest=false;
        imageBook.startAnimation(animResume);
    }

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        //run the test after the animation
        if((counterStartStop%2)==0){
            //create widget scroll text view
            ScrollView = (ScrollTextView)LayoutInflater.from(this).inflate(R.layout.scrolltextview, null);
            SeekBarView=(SeekBar)LayoutInflater.from(this).inflate(R.layout.seekbar,null);
            SeekBarView.setOnSeekBarChangeListener(this);
            storageView.addView(ScrollView);
            ScrollTextViewParams = (RelativeLayout.LayoutParams) ScrollView.getLayoutParams();
            ScrollTextViewParams.topMargin = (int)getResources().getDimension(R.dimen.ScrollTextViewTopMargin);

            storageView.addView(SeekBarView);
            SeekBarTextViewParams = (RelativeLayout.LayoutParams) SeekBarView.getLayoutParams();
            SeekBarTextViewParams.topMargin= (int)getResources().getDimension(R.dimen.SeekBarTextViewTopMargin);
            SeekBarTextViewParams.width=(int)getResources().getDimension(R.dimen.SeekBarView_width);
            SeekBarTextViewParams.height=(int)getResources().getDimension(R.dimen.SeekBarView_height);
            SeekBarTextViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

            startButton.setVisibility(View.VISIBLE);
            startButton.setText(R.string.Stop_test);
            ScrollView.pauseScroll();
            ScrollView.setText(textScrolling);
            ScrollView.setRndDuration(59000);
            ScrollView.startScroll();
            timerStart(stringTimer);
        //stop test and return to the Activiti results
        }else {
            startButton.setVisibility(View.VISIBLE);
            startButton.setText(R.string.Start_test);
            storageView.addView(user);
            storageView.addView(userResult);
            userResult.setText(getResources().getString(R.string.UserTestResult)+ "");}
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void timerStart(final TextView stringTimer){
        startTest=true;
        timer = new CountDownTimer(60000,1000){
            @Override
            public void onFinish() {
                finishTest();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                stringTimer.setText(getResources().getString(R.string.TimerText)+" "+millisUntilFinished/1000+" "+getResources().getString(R.string.TimerTextTime));
            }
        };
        timer.start();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(startTest){
            ScrollView.pauseScroll();
            ScrollView.setRndDuration(progress);
            ScrollView.resumeScroll();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        finish();
        return super.onOptionsItemSelected(item);}

    public int SetSpeed(String StringLength){
        return 12;
    }


}
