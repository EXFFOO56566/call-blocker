package com.call.calllog.blocklist.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.call.calllog.blocklist.activity.SettingActivity;
import com.call.calllog.blocklist.activity.ToastAdListener;
import com.call.calllog.blocklist.activity.WhiteListActivity;
import com.call.calllog.blocklist.object.Constant;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class BlockingFragment extends Fragment {

    private RelativeLayout blocking,private_number,unknown_number,all_calls,white_list;
    private ImageView switch_on,private_switch,unknown_switch,all_switch;
    private View view;

    private InterstitialAd interstitialAds;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view=inflater.inflate(R.layout.blocking_fragment, container, false);

        init();
        click();


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }

        switch_on.setImageResource(R.drawable.switchon);

        return view;
    }

    private void click() {
        blocking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }

                boolean b= PrefUtils.getBlocking(getActivity(), Constant.BLOCKING);
                PrefUtils.setBlocking(getActivity(), Constant.BLOCKING,!b);
                if(PrefUtils.getBlocking(getActivity(), Constant.BLOCKING))
                {
                    switch_on.setImageResource(R.drawable.switchon);
                }else{
                    switch_on.setImageResource(R.drawable.switchoff);
                }
            }
        });
        private_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Random random = new Random();
                int l = random.nextInt(2);
                if (l == 1) {
                    googleintersial();
                }

                boolean b= PrefUtils.getPrivteNumbers(getActivity(), Constant.PRIVATE_NUMBERS);
                PrefUtils.setPrivteNumbers(getActivity(), Constant.PRIVATE_NUMBERS,!b);
                if (PrefUtils.getPrivteNumbers(getActivity(), Constant.PRIVATE_NUMBERS))
                {
                    private_switch.setImageResource(R.drawable.checked);
                }else{
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

                boolean b= PrefUtils.getUnknownNumbers(getActivity(), Constant.UNKNOWN_NUMBERS);
                PrefUtils.setUnknownNumbers(getActivity(), Constant.UNKNOWN_NUMBERS,!b);
                if (PrefUtils.getUnknownNumbers(getActivity(), Constant.UNKNOWN_NUMBERS))
                {
                    unknown_switch.setImageResource(R.drawable.checked);
                }else{
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

                boolean b= PrefUtils.getAllCalls(getActivity(), Constant.ALL_CALLS);
                PrefUtils.setAllCalls(getActivity(), Constant.ALL_CALLS,!b);
                if ( PrefUtils.getAllCalls(getActivity(), Constant.ALL_CALLS))
                {
                    all_switch.setImageResource(R.drawable.checked);
                }else{
                    all_switch.setImageResource(R.drawable.check);
                }
            }
        });

        white_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WhiteListActivity.class);
                startActivity(intent);
            }
        });

    }

    private void init() {

        blocking= (RelativeLayout) view.findViewById(R.id.blocking);
        private_number= (RelativeLayout) view.findViewById(R.id.private_number);
        unknown_number= (RelativeLayout) view.findViewById(R.id.unknown_number);
        all_calls= (RelativeLayout) view.findViewById(R.id.all_calls);
        white_list= (RelativeLayout) view.findViewById(R.id.white_list);

        switch_on= (ImageView) view.findViewById(R.id.switch_on);
        private_switch= (ImageView) view.findViewById(R.id.private_switch);
        unknown_switch= (ImageView) view.findViewById(R.id.unknown_switch);
        all_switch= (ImageView) view.findViewById(R.id.all_switch);

        if(PrefUtils.getBlocking(getActivity(), Constant.BLOCKING))
        {
            switch_on.setImageResource(R.drawable.switchon);
        }else{
            switch_on.setImageResource(R.drawable.switchoff);
        }


        if (PrefUtils.getPrivteNumbers(getActivity(), Constant.PRIVATE_NUMBERS))
        {
            private_switch.setImageResource(R.drawable.checked);
        }else{
            private_switch.setImageResource(R.drawable.check);
        }

        if (PrefUtils.getUnknownNumbers(getActivity(), Constant.UNKNOWN_NUMBERS))
        {
            unknown_switch.setImageResource(R.drawable.checked);
        }else{
            unknown_switch.setImageResource(R.drawable.check);
        }

        if ( PrefUtils.getAllCalls(getActivity(), Constant.ALL_CALLS))
        {
            all_switch.setImageResource(R.drawable.checked);
        }else{
            all_switch.setImageResource(R.drawable.check);
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings:

                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

                return true;
            case R.id.white_list:
                Intent intent1=new Intent(getActivity(), WhiteListActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void googleintersial() {

        interstitialAds = new InterstitialAd(getActivity().getApplicationContext());
        interstitialAds.setAdUnitId(getResources().getString(
                R.string.full_id));
        interstitialAds.loadAd(new AdRequest.Builder().build());
        interstitialAds.setAdListener(new ToastAdListener(getActivity().getApplicationContext()) {
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
