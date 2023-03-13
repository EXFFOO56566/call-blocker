package com.call.calllog.blocklist.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.call.calllog.blocklist.object.Constant;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

/**
 * Created by jksol3 on 22/2/17.
 */

public class SettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ScrollView main_layout;
    private LinearLayout blockin_layout, noti_layout;
    private RelativeLayout block_layout, whitelist_layout, notifi_layout, support_layout, rate_layout, share_layout, other_layout;
    private RelativeLayout blocking, private_number, unknown_number, all_calls, status_bar, notifi_rel_layout;
    private ImageView switch_on, private_switch, unknown_switch, all_switch, status_image, noti_image;

    private InterstitialAd interstitialAds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        init();

        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }


        click();

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        main_layout = (ScrollView) findViewById(R.id.main_layout);
        blockin_layout = (LinearLayout) findViewById(R.id.blocking_layout);
        noti_layout = (LinearLayout) findViewById(R.id.notification_layout);

        block_layout = (RelativeLayout) findViewById(R.id.block_layout);
        whitelist_layout = (RelativeLayout) findViewById(R.id.white_layout);
        notifi_layout = (RelativeLayout) findViewById(R.id.noti_layout);
        support_layout = (RelativeLayout) findViewById(R.id.support_layout);
        rate_layout = (RelativeLayout) findViewById(R.id.rate_layout);
        share_layout = (RelativeLayout) findViewById(R.id.share_layout);
        other_layout = (RelativeLayout) findViewById(R.id.other_app_layout);


        blocking = (RelativeLayout) findViewById(R.id.blocking);
        private_number = (RelativeLayout) findViewById(R.id.private_number);
        unknown_number = (RelativeLayout) findViewById(R.id.unknown_number);
        all_calls = (RelativeLayout) findViewById(R.id.all_calls);

        switch_on = (ImageView) findViewById(R.id.switch_on);
        private_switch = (ImageView) findViewById(R.id.private_switch);
        unknown_switch = (ImageView) findViewById(R.id.unknown_switch);
        all_switch = (ImageView) findViewById(R.id.all_switch);

        status_bar = (RelativeLayout) findViewById(R.id.status_bar);
        notifi_rel_layout = (RelativeLayout) findViewById(R.id.notification_rel_layout);

        status_image = (ImageView) findViewById(R.id.status_image);
        noti_image = (ImageView) findViewById(R.id.noti_switch);

        main_layout.setVisibility(View.VISIBLE);
        blockin_layout.setVisibility(View.GONE);
        noti_layout.setVisibility(View.GONE);

        if (PrefUtils.getBlocking(getApplicationContext(), Constant.BLOCKING)) {
            switch_on.setImageResource(R.drawable.switchon);
        } else {
            switch_on.setImageResource(R.drawable.switchoff);
        }


        if (PrefUtils.getPrivteNumbers(getApplicationContext(), Constant.PRIVATE_NUMBERS)) {
            private_switch.setImageResource(R.drawable.checked);
        } else {
            private_switch.setImageResource(R.drawable.check);
        }

        if (PrefUtils.getUnknownNumbers(getApplicationContext(), Constant.UNKNOWN_NUMBERS)) {
            unknown_switch.setImageResource(R.drawable.checked);
        } else {
            unknown_switch.setImageResource(R.drawable.check);
        }

        if (PrefUtils.getAllCalls(getApplicationContext(), Constant.ALL_CALLS)) {
            all_switch.setImageResource(R.drawable.checked);
        } else {
            all_switch.setImageResource(R.drawable.check);
        }

        if (PrefUtils.getStatusIcon(getApplicationContext(), Constant.STATUSBAR)) {
            status_image.setImageResource(R.drawable.switchon);
        } else {
            status_image.setImageResource(R.drawable.switchoff);
        }
        if (PrefUtils.getNotification(getApplicationContext(), Constant.NOTIFICATIONS)) {
            noti_image.setImageResource(R.drawable.switchon);
        } else {
            noti_image.setImageResource(R.drawable.switchoff);
        }


    }

    public void click() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        block_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                Log.e("TAG", "onClick: block");
                main_layout.setVisibility(View.GONE);
                blockin_layout.setVisibility(View.VISIBLE);
                noti_layout.setVisibility(View.GONE);
                toolbar.setTitle("Blocking");
            }
        });
        notifi_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                Log.e("TAG", "onClick: noti");
                main_layout.setVisibility(View.GONE);
                blockin_layout.setVisibility(View.GONE);
                noti_layout.setVisibility(View.VISIBLE);
                toolbar.setTitle("Notifications");
            }
        });
        whitelist_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                Intent intent = new Intent(SettingActivity.this, WhiteListActivity.class);
                startActivity(intent);
            }
        });

        blocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                boolean b = PrefUtils.getBlocking(getApplicationContext(), Constant.BLOCKING);
                PrefUtils.setBlocking(getApplicationContext(), Constant.BLOCKING, !b);
                if (PrefUtils.getBlocking(getApplicationContext(), Constant.BLOCKING)) {
                    switch_on.setImageResource(R.drawable.switchon);
                } else {
                    switch_on.setImageResource(R.drawable.switchoff);
                }
            }
        });
        private_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = PrefUtils.getPrivteNumbers(getApplicationContext(), Constant.PRIVATE_NUMBERS);
                PrefUtils.setPrivteNumbers(getApplicationContext(), Constant.PRIVATE_NUMBERS, !b);
                if (PrefUtils.getPrivteNumbers(getApplicationContext(), Constant.PRIVATE_NUMBERS)) {
                    private_switch.setImageResource(R.drawable.checked);
                } else {
                    private_switch.setImageResource(R.drawable.check);
                }
            }
        });
        unknown_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                boolean b = PrefUtils.getUnknownNumbers(getApplicationContext(), Constant.UNKNOWN_NUMBERS);
                PrefUtils.setUnknownNumbers(getApplicationContext(), Constant.UNKNOWN_NUMBERS, !b);
                if (PrefUtils.getUnknownNumbers(getApplicationContext(), Constant.UNKNOWN_NUMBERS)) {
                    unknown_switch.setImageResource(R.drawable.checked);
                } else {
                    unknown_switch.setImageResource(R.drawable.check);
                }
            }
        });
        all_calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                boolean b = PrefUtils.getAllCalls(getApplicationContext(), Constant.ALL_CALLS);
                PrefUtils.setAllCalls(getApplicationContext(), Constant.ALL_CALLS, !b);
                if (PrefUtils.getAllCalls(getApplicationContext(), Constant.ALL_CALLS)) {
                    all_switch.setImageResource(R.drawable.checked);
                } else {
                    all_switch.setImageResource(R.drawable.check);
                }
            }
        });

        status_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = PrefUtils.getStatusIcon(getApplicationContext(), Constant.STATUSBAR);
                PrefUtils.setStatusIcon(getApplicationContext(), Constant.STATUSBAR, !b);
                if (PrefUtils.getStatusIcon(getApplicationContext(), Constant.STATUSBAR)) {
                    PendingIntent pi = PendingIntent.getActivity(SettingActivity.this, 0, new Intent(SettingActivity.this, MainActivity.class), 0);
                    Resources r = getResources();
                    Notification notification = new NotificationCompat.Builder(SettingActivity.this)
                            .setSmallIcon(R.drawable.noti_icon)
                            .setContentTitle(r.getString(R.string.app_name))
                            .setContentText("Blocking is enabled")
                            .setContentIntent(pi)
                            .setAutoCancel(false)
                            .build();

                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(1111, notification);
                    status_image.setImageResource(R.drawable.switchon);
                } else {
                    NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    nMgr.cancel(1111);
                    status_image.setImageResource(R.drawable.switchoff);
                }

            }
        });

        notifi_rel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean b = PrefUtils.getNotification(getApplicationContext(), Constant.NOTIFICATIONS);
                PrefUtils.setNotification(getApplicationContext(), Constant.NOTIFICATIONS, !b);
                if (PrefUtils.getNotification(getApplicationContext(), Constant.NOTIFICATIONS)) {
                    noti_image.setImageResource(R.drawable.switchon);
                } else {
                    noti_image.setImageResource(R.drawable.switchoff);
                }
            }
        });

        other_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?id="+getResources().getString(R.string.developername))));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id="+getResources().getString(R.string.developername))));
                }
            }
        });

        support_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getResources().getString(R.string.email), null));
                startActivity(sharingIntent);
            }
        });

        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareintent = new Intent(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                shareintent.putExtra(Intent.EXTRA_TEXT, "http://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(shareintent, "Share with"));
            }
        });

        rate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateUsDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (main_layout.getVisibility() == View.VISIBLE)
            super.onBackPressed();
        main_layout.setVisibility(View.VISIBLE);
        blockin_layout.setVisibility(View.GONE);
        noti_layout.setVisibility(View.GONE);
        toolbar.setTitle("Settings");
    }

    public void rateUsDialog() {
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