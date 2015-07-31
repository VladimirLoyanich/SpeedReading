package com.vladimirloyanich.speedreading;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
    Context context;

    private boolean testEndFull=false;
    private Animation animStart, animResume;
    private ImageView imageBook;
    private boolean startTest=false;
    private CountDownTimer timer;
    private long time;
    private long frozenTime=600;
    private double standardSpeedTime;
    private double sumAverageReadSpeed;
    private TextView stringTimer,user,userResult;
    private Button startButton;
    private int counterStartStop=1;
    private RelativeLayout storageView;
    private SeekBar SeekBarView;
    private ScrollTextView ScrollView;
    RelativeLayout.LayoutParams ScrollTextViewParams,SeekBarTextViewParams,TextViewParams,TextViewResultParams;
    private String textScrolling, UserName;
    private StorageClass StringStorage = new StorageClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
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
        textScrolling = StringStorage.getStorageText();
        //create link on RelativeLayout to add widget
        storageView = (RelativeLayout) findViewById(R.id.storage);

        //create widget text view user name
        user = (TextView) LayoutInflater.from(this).inflate(R.layout.textviewuser, null);
        storageView.addView(user);
        user.setText(getResources().getString(R.string.UserTest)+"\n" +UserName);
        TextViewParams = (RelativeLayout.LayoutParams) user.getLayoutParams();
        TextViewParams.leftMargin = (int) getResources().getDimension(R.dimen.TextViewLeftMargin);
        //create widget text view user Result test
        userResult = (TextView) LayoutInflater.from(this).inflate(R.layout.textviewuserresult, null);
        storageView.addView(userResult);
        userResult.setText(getResources().getString(R.string.UserTestResultBefore));
        TextViewResultParams = (RelativeLayout.LayoutParams) userResult.getLayoutParams(); //add
        TextViewResultParams.leftMargin = (int) getResources().getDimension(R.dimen.TextViewLeftMargin);
        TextViewResultParams.topMargin = (int) getResources().getDimension(R.dimen.TextViewTopMargin);
    }

    public void ClickButtonStart(View view){
        startButton.setVisibility(View.GONE);
        counterStartStop++;
        //true if click Start Button
        if((counterStartStop%2)==0){
            testEndFull=false;
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
        testEndFull=true;
        storageView.removeView(ScrollView);
        storageView.removeView(SeekBarView);
        timer.cancel();

        averageReadSpeed();

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
            SeekBarView.setMax(21);

            startButton.setVisibility(View.VISIBLE);
            startButton.setText(R.string.Stop_test);
//            ScrollView.pauseScroll();
            ScrollView.setText(textScrolling);
            standardSpeedTime=ScrollView.setRndDuration(0,StringStorage.getTextSize());
            ScrollView.startScroll();
            timerStart(stringTimer);
        //stop test and return to the Activiti results
        }else {
            startButton.setVisibility(View.VISIBLE);
            startButton.setText(R.string.Start_test);
            storageView.addView(user);
            storageView.addView(userResult);

            if (testEndFull){
                long result=(long)sumAverageReadSpeed/600;
                userResult.setText(getResources().getString(R.string.UserTestResult)+"\n"+" "+result+" "+getResources().getString(R.string.UserTestResultEndText));
                viewDialogResult(getResources().getString(R.string.UserTestResult)+"\n"  + result+ " " +getResources().getString(R.string.UserTestResultEndText));}
            else {
                userResult.setText(getResources().getString(R.string.UserTestResultFalse)+"");
                viewDialogResult(getResources().getString(R.string.UserTestResultFalse) + "");}

            }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public void timerStart(final TextView stringTimer){
        startTest=true;
        timer = new CountDownTimer(5000,100){
            @Override
            public void onFinish() {
                finishTest();
            }
            @Override
            public void onTick(long millisUntilFinished) {
                time=millisUntilFinished/100;
                    stringTimer.setText(getResources().getString(R.string.TimerText) + " " + millisUntilFinished / 1000 + " " + getResources().getString(R.string.TimerTextTime));


            }
        };
        timer.start();
    }

    //calculation of the average read speed
    private void averageReadSpeed(){
        double timeDifference=frozenTime-time;
        frozenTime=time;
        sumAverageReadSpeed=sumAverageReadSpeed+(standardSpeedTime*timeDifference);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(startTest){
            ScrollView.pauseScroll();
            averageReadSpeed();
            standardSpeedTime=ScrollView.setRndDuration(progress,StringStorage.getTextSize());
            ScrollView.resumeScroll();
        }
    }

    public void viewDialogResult(String resultTest){
        // Created a new Dialog
        final Dialog dialog = new Dialog(Speed_reading.this);
        /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // inflate the layout
        dialog.setContentView(R.layout.customizeresultdialog);
        /** Design the dialog in main.xml file */
        dialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        TextView mMessage = (TextView) dialog.findViewById(R.id.dialogMessage);
        mMessage.setText(resultTest);
        Button okButton = (Button) dialog.findViewById(R.id.OkButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                dialog.dismiss();
            }});
        // Display the dialog
        dialog.show();
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
