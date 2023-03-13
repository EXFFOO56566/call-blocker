package com.call.calllog.blocklist.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.call.calllog.blocklist.object.Contact;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by jksol3 on 22/2/17.
 */

public class AddActivity extends AppCompatActivity {

    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    private Toolbar toolbar;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    private ImageView imageView;
    private int isBeginwith;
    private Gson gson = new Gson();

    private InterstitialAd interstitialAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        init();

        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }

        click();

    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);

        isBeginwith = getIntent().getIntExtra("value", 0);

        getSupportActionBar().setTitle("Add");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.add_floating);
        editText = (EditText) findViewById(R.id.add_number);
        imageView = (ImageView) findViewById(R.id.image_begin);

        if (isBeginwith == 0) {
            imageView.setImageResource(R.drawable.check);
        } else {
            imageView.setImageResource(R.drawable.checked);
        }
        floatingActionButton.setVisibility(View.GONE);
    }

    public void click() {

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                if (text.length() == 0) {
                    floatingActionButton.clearAnimation();
                    Animation animation = AnimationUtils.loadAnimation(AddActivity.this, R.anim.pop_down);
                    floatingActionButton.startAnimation(animation);
                    floatingActionButton.setVisibility(View.GONE);
                } else {

                    floatingActionButton.clearAnimation();
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }


                ArrayList<Contact> contacts1 = new ArrayList<Contact>();
                ArrayList<Contact> add_contact = new ArrayList<Contact>();
                if (!PrefUtils.getContactBlackList(getApplicationContext()).equals("")) {
                    contacts1 = gson.fromJson(PrefUtils.getContactBlackList(getApplicationContext()), type);
                    String name = editText.getText().toString();
                    Contact contact = new Contact();
                    if (isBeginwith == 1) {
                        name = name + "?";
                    }
                    contact.setName(name);
                    contact.setNumber(name);
                    contact.setType(String.valueOf(isBeginwith));
                    if (!contacts1.contains(contact)) {
                        contacts1.add(contact);
                    }
                    PrefUtils.setContactBlackList(getApplicationContext(), gson.toJson(contacts1));
                } else {
                    String name = editText.getText().toString();
                    Contact contact = new Contact();
                    if (isBeginwith == 1) {
                        name = name + "?";
                    }
                    contact.setName(name);
                    contact.setNumber(name);
                    contact.setType(String.valueOf(isBeginwith));
                    contacts1.add(contact);

                    PrefUtils.setContactBlackList(getApplicationContext(), gson.toJson(contacts1));
                }

                Log.e("TAG", "onClick: " + String.valueOf(isBeginwith));

                if (!PrefUtils.getInputList(getApplicationContext()).equals("")) {
                    add_contact = gson.fromJson(PrefUtils.getInputList(getApplicationContext()), type);
                    String name = editText.getText().toString();
                    Contact contact = new Contact();
                    if (isBeginwith == 1) {
                        name = name + "?";
                    }
                    contact.setName(name);
                    contact.setNumber(name);
                    contact.setType(String.valueOf(isBeginwith));
                    if (!add_contact.contains(contact)) {
                        add_contact.add(contact);
                    }
                    PrefUtils.setInputList(getApplicationContext(), gson.toJson(add_contact));
                } else {
                    String name = editText.getText().toString();
                    Contact contact = new Contact();
                    if (isBeginwith == 1) {
                        name = name + "?";
                    }
                    contact.setName(name);
                    contact.setNumber(name);
                    contact.setType(String.valueOf(isBeginwith));
                    add_contact.add(contact);

                    PrefUtils.setInputList(getApplicationContext(), gson.toJson(add_contact));
                }

                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: 12    "+isBeginwith );
                if(isBeginwith==0)
                    isBeginwith = 1;
                else
                    isBeginwith = 0;

                if (isBeginwith == 0) {
                    imageView.setImageResource(R.drawable.check);
                } else {
                    imageView.setImageResource(R.drawable.checked);
                }
                Log.e("TAG", "onClick: 12333    "+isBeginwith );
            }
        });

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