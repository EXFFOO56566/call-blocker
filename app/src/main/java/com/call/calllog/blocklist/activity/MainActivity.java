package com.call.calllog.blocklist.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.call.calllog.blocklist.fragment.BlacklistFragment;
import com.call.calllog.blocklist.fragment.BlockingFragment;
import com.call.calllog.blocklist.fragment.LogFragment;
import com.call.calllog.blocklist.fragment.MoreFeatureFragment;
import com.call.calllog.blocklist.object.Constant;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout blocking_layout, blacklist_layout, log_layout, more_layout;
    private TextView txt_blocking, txt_blacklist, txt_log, txt_more;

    private InterstitialAd interstitialAds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        googleintersial();

        init();
        rateus();
    }

    public void init() {

        try {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }catch (Exception e)
        {

        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blacklist_layout = (LinearLayout) findViewById(R.id.blacklist_layout);
        blocking_layout = (LinearLayout) findViewById(R.id.blocking_layout);
        log_layout = (LinearLayout) findViewById(R.id.log_layout);
        more_layout = (LinearLayout) findViewById(R.id.more_layout);

        txt_blacklist = (TextView) findViewById(R.id.txt_blcklist);
        txt_blocking = (TextView) findViewById(R.id.txt_blocking);
        txt_log = (TextView) findViewById(R.id.txt_log);
        txt_more = (TextView) findViewById(R.id.txt_more);

        if (PrefUtils.getStatusIcon(getApplicationContext(), Constant.STATUSBAR)) {
            PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
            Resources r = getResources();
            Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.noti_icon)
                    .setContentTitle(r.getString(R.string.app_name))
                    .setContentText("Blocking is enabled")
                    .setContentIntent(pi)
                    .setAutoCancel(false)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.notify(1111, notification);
        }

        BlacklistFragment newContent = new BlacklistFragment();
        if (newContent != null) {

            setSelected();
            txt_blacklist.setSelected(true);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, newContent).commit();
        }

        click();
    }

    private void click() {
        blocking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlockingFragment newContent = new BlockingFragment();
                if (newContent != null) {

                    setSelected();
                    txt_blocking.setSelected(true);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, newContent).commit();
                }
            }
        });

        blacklist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlacklistFragment newContent = new BlacklistFragment();
                if (newContent != null) {

                    setSelected();
                    txt_blacklist.setSelected(true);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, newContent).commit();
                }
            }
        });

        log_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogFragment newContent = new LogFragment();
                if (newContent != null) {

                    setSelected();
                    txt_log.setSelected(true);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, newContent).commit();
                }
            }
        });

        more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreFeatureFragment newContent = new MoreFeatureFragment();
                if (newContent != null) {

                    setSelected();
                    txt_more.setSelected(true);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame, newContent).commit();
                }
            }
        });
    }

    private void setSelected() {
        txt_blacklist.setSelected(false);
        txt_blocking.setSelected(false);
        txt_log.setSelected(false);
        txt_more.setSelected(false);
    }


    public void rateus(){

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.rate_dialog, null);
            dialogBuilder.setView(dialogView);

            final Button rate_us = (Button) dialogView.findViewById(R.id.btn_rate_us);
            final Button cancle = (Button) dialogView.findViewById(R.id.btn_cancle);
            final TextView upper= (TextView) dialogView.findViewById(R.id.uppertext);

            Typeface face= Typeface.createFromAsset(getAssets(),
                    "fonts/avenirltstd_medium.otf");

            rate_us.setTypeface(face);
            cancle.setTypeface(face);
            upper.setTypeface(face);

            final AlertDialog b = dialogBuilder.create();
            rate_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=" + getPackageName())));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/search?q=" + getPackageName())));
                    }
                    b.cancel();
                }
            });

            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b.cancel();
                }
            });

            b.show();

    }
    public void googleintersial() {

        interstitialAds = new InterstitialAd(this);
        interstitialAds.setAdUnitId(getResources().getString(
                R.string.full_id));
        interstitialAds.loadAd(new AdRequest.Builder().build());
        interstitialAds.setAdListener(new ToastAdListener(this) {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
                if (interstitialAds.isLoaded()) {
                    interstitialAds.show();
                }
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // TODO Auto-generated method stub

                super.onAdFailedToLoad(errorCode);
            }
        });

    }

}
