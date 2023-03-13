package com.call.calllog.blocklist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.call.calllog.blocklist.object.Contact;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.textdrawable.TextDrawable;
import com.call.calllog.blocklist.textdrawable.util.ColorGenerator;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class WhiteListActivity extends AppCompatActivity implements CallLogActivity.afterloading {

    Gson gson = new Gson();
    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Contact> WhiteList = new ArrayList<>();
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private ProgressBar progressBar;

    private InterstitialAd interstitialAds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whitelist_activity);

        init();


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }
        click();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }

    public void setAdapter() {
        WhiteList = new ArrayList<>();

        if (!PrefUtils.getWhiteList(getApplicationContext()).equals("")) {
            WhiteList = gson.fromJson(PrefUtils.getWhiteList(getApplicationContext()), type);
        }
        if (WhiteList.size() != 0) {
            WhiteListAdapter logAdapter = new WhiteListAdapter(WhiteList);
            listView.setAdapter(logAdapter);
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
        }

    }

    public void openDialog() {


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }


        AlertDialog.Builder adb = new AlertDialog.Builder(WhiteListActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle("Add");

        final String[] Colors = new String[]{
                "From calls log", "From contacts", "Input number",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("input number")) {
                    Intent intent = new Intent(WhiteListActivity.this, AddActivity.class);
                    intent.putExtra("value", 0);
                    startActivity(intent);
                } else if (clickedItemValue.equalsIgnoreCase("From contacts")) {
                    progressBar.setVisibility(View.VISIBLE);
                    ContactsActivity.setLoading(WhiteListActivity.this);
                    Intent intent = new Intent(WhiteListActivity.this, ContactsActivity.class);
                    intent.putExtra("data", "whitelist");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else if (clickedItemValue.equalsIgnoreCase("From calls log")) {
                    progressBar.setVisibility(View.VISIBLE);
                    CallLogActivity.setLoading(WhiteListActivity.this);
                    Intent intent = new Intent(WhiteListActivity.this, CallLogActivity.class);
                    intent.putExtra("data", "whitelist");
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        alertDialog.show();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.white_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Whitelist");
        getSupportActionBar().setSubtitle("Never blocked numbers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.white_progressBar);
        progressBar.setVisibility(View.GONE);

        listView = (ListView) findViewById(R.id.white_list);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.whitelist_float);

        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

    }

    public void click() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                openDialog();
            }
        });
    }

    public void deleteDialog(final Contact contact) {
        AlertDialog.Builder adb = new AlertDialog.Builder(WhiteListActivity.this, R.style.MyAlertDialogStyle);
        adb.setTitle(contact.getName());

        final String[] Colors = new String[]{
                "Delete",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("delete")) {
                    if (WhiteList.contains(contact)) {
                        WhiteList.remove(contact);
                        ArrayList<Contact> contacts = new ArrayList<Contact>();
                        contacts.addAll(WhiteList);
                        PrefUtils.setWhiteList(getApplicationContext(), gson.toJson(contacts));
                        setAdapter();
                    }
                }
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        alertDialog.show();
    }

    @Override
    public void afterLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;

        private TextView textView;

        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    private class WhiteListAdapter extends BaseAdapter {

        private ArrayList<Contact> contactArrayList = new ArrayList<>();

        public WhiteListAdapter(ArrayList<Contact> contactArrayList) {
            this.contactArrayList = contactArrayList;
        }

        @Override
        public int getCount() {
            return contactArrayList.size();
        }

        @Override
        public Contact getItem(int position) {
            return contactArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Contact item = getItem(position);

            // provide support for selected state
            updateCheckedState(holder, item);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteDialog(item);

                }
            });
            holder.textView.setText(item.name);

            return convertView;
        }

        private void updateCheckedState(ViewHolder holder, Contact item) {
            TextDrawable drawable;
            drawable = mDrawableBuilder.build(String.valueOf(item.name.charAt(0)), mColorGenerator.getColor(item.name));
            holder.imageView.setImageDrawable(drawable);
            holder.view.setBackgroundColor(Color.TRANSPARENT);
            holder.checkIcon.setVisibility(View.GONE);
            Log.d("TAG", "updateCheckedState: ");
        }
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