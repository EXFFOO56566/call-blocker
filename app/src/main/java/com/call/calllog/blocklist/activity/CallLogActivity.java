package com.call.calllog.blocklist.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.call.calllog.blocklist.object.Contact;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.object.RoundedImageView;
import com.call.calllog.blocklist.textdrawable.TextDrawable;
import com.call.calllog.blocklist.textdrawable.util.ColorGenerator;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.call.calllog.blocklist.activity.ContactsActivity.getContactBitmapFromURI;

/**
 * Created by jksol3 on 22/2/17.
 */

public class CallLogActivity extends AppCompatActivity {


    private InterstitialAd interstitialAds;
    public static Comparator<Contact> name = new Comparator<Contact>() {

        public int compare(Contact app1, Contact app2) {

            Contact stringName1 = app1;
            Contact stringName2 = app2;

            return stringName1.getName().compareToIgnoreCase(stringName2.getName());
        }
    };
    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    private Toolbar toolbar;
    private ListView listView;
    private TextView textView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ArrayList<Contact> Selected_contacts = new ArrayList<>();
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private CallLogAdapter sampleAdapter;
    private EditText search_edittext;
    private Gson gson = new Gson();
    private ArrayList<Contact> pre_contacts = new ArrayList<>();
    private ArrayList<Contact> white_contacts = new ArrayList<>();
    private String whitelist = null;
    public static afterloading afterloading;

    public static void setLoading(afterloading afterloading1) {
        afterloading = afterloading1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);

        init();

        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }


        click();

    }

    public void init() {

        whitelist = getIntent().getStringExtra("data");
        toolbar = (Toolbar) findViewById(R.id.contact_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Call Log");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = (ListView) findViewById(R.id.contact_listview);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.done);
        textView = (TextView) findViewById(R.id.selected_text);
        search_edittext = (EditText) findViewById(R.id.search_edittext);

        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

        if (!PrefUtils.getContactBlackList(getApplicationContext()).equals("")) {
            pre_contacts = gson.fromJson(PrefUtils.getContactBlackList(getApplicationContext()), type);
        }

        if (!PrefUtils.getWhiteList(getApplicationContext()).equals("")) {
            white_contacts = gson.fromJson(PrefUtils.getWhiteList(getApplicationContext()), type);
        }

        getAllContact();

        afterloading.afterLoading();

//        Collections.sort(contacts, name);

        sampleAdapter = new CallLogAdapter(contacts);
        listView.setAdapter(sampleAdapter);

    }

    public void click() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search_edittext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = search_edittext.getText().toString().toLowerCase(Locale.getDefault());
                sampleAdapter.filter(text);
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
                if (whitelist == null) {
                    if (!PrefUtils.getContactBlackList(getApplicationContext()).equals("")) {
                        ArrayList<Contact> contacts1 = gson.fromJson(PrefUtils.getContactBlackList(getApplicationContext()), type);

                        for (int i = 0; i < Selected_contacts.size(); i++) {
                            if (!contacts1.contains(Selected_contacts.get(i))) {
                                contacts1.add(Selected_contacts.get(i));
                            }
                        }
                        PrefUtils.setContactBlackList(getApplicationContext(), gson.toJson(contacts1));
                    } else {
                        PrefUtils.setContactBlackList(getApplicationContext(), gson.toJson(Selected_contacts));
                    }
                    Log.d("TAG", "updateCheckedState: " + PrefUtils.getContactBlackList(getApplicationContext()));
                } else {
                    if (!PrefUtils.getWhiteList(getApplicationContext()).equals("")) {
                        ArrayList<Contact> contacts1 = gson.fromJson(PrefUtils.getWhiteList(getApplicationContext()), type);
                        for (int i = 0; i < Selected_contacts.size(); i++) {
                            if (!contacts1.contains(Selected_contacts.get(i))) {
                                contacts1.contains(Selected_contacts.get(i));
                            }
                        }
                        PrefUtils.setWhiteList(getApplicationContext(), gson.toJson(contacts1));
                    } else {
                        PrefUtils.setWhiteList(getApplicationContext(), gson.toJson(Selected_contacts));
                    }
                }
                onBackPressed();

            }
        });

    }

    public void getAllContact() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
        int image = managedCursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI);
        while (managedCursor.moveToNext()) {
            String phNum = managedCursor.getString(number);
            String callTypeCode = managedCursor.getString(type);
            String strcallDate = managedCursor.getString(date);
            Date callDate = new Date(Long.valueOf(strcallDate));
            String callDuration = managedCursor.getString(duration);
            String call_name = managedCursor.getString(name);

            String callType = null;
            int callcode = Integer.parseInt(callTypeCode);
            switch (callcode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    callType = "Outgoing";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    callType = "Incoming";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    callType = "Missed";
                    break;
            }
            Contact contact = new Contact();
            if (call_name != null) {
                contact.setName(call_name);
                contact.setIsName("yes");
            } else {
                contact.setName(phNum);
                contact.setIsName(null);
            }
            contact.setNumber(phNum);
            contact.setContact_image(getContactImage(phNum));
            if (!contacts.contains(contact)) {
                if (!pre_contacts.contains(contact))
                    if (!white_contacts.contains(contact))
                        contacts.add(contact);
            }
        }
        managedCursor.close();
    }

    public String getContactImage(String phNumber) {

        String image_uri = null;
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?", new String[]{phNumber}, null);

        while (cursor.moveToNext()) {
            image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
        }
        cursor.close();
        return image_uri;
    }

    private static class ViewHolder {

        private View view;

        private RoundedImageView round_imageView;
        private ImageView imageView;

        private TextView textView;

        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            round_imageView = (RoundedImageView) view.findViewById(R.id.round_imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
        }
    }

    private class CallLogAdapter extends BaseAdapter {

        private ArrayList<Contact> contactArrayList = new ArrayList<>();
        private ArrayList<Contact> ori = new ArrayList<>();

        public CallLogAdapter(ArrayList<Contact> contactArrayList) {
            this.contactArrayList = contactArrayList;
            ori.addAll(contactArrayList);
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
                convertView = View.inflate(CallLogActivity.this, R.layout.list_item_layout, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Contact item = getItem(position);

            // provide support for selected state
            updateCheckedState(holder, item);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // when the image is clicked, update the selected state
                    Contact data = getItem(position);
                    data.setChecked(!data.isChecked);
                    updateCheckedState(holder, data);
                }
            });
            if (item.getName() == null) {
                holder.textView.setText(item.getNumber());
            } else {
                holder.textView.setText(item.name);
            }

            return convertView;
        }

        private void updateCheckedState(ViewHolder holder, Contact item) {
            if (item.isChecked) {
//                holder.imageView.setImageDrawable(mDrawableBuilder.build(" ", 0xff616161));
                holder.checkIcon.setVisibility(View.VISIBLE);
            } else {
                if (item.getContact_image() != null) {
                    Bitmap mBitmap = getContactBitmapFromURI(getApplicationContext(), Uri.parse(item.getContact_image()));
                    holder.round_imageView.setImageBitmap(mBitmap);
                    holder.round_imageView.setVisibility(View.VISIBLE);
                    holder.imageView.setVisibility(View.GONE);
                } else {
                    TextDrawable drawable;

                    Log.e("TAG", "updateCheckedState: " + item.name + "     " + item.number
                    );
                    if (item.getIsName() == null) {
                        if (item.number.startsWith("+")) {
                            drawable = mDrawableBuilder.build(String.valueOf(item.number.substring(0, 2)), mColorGenerator.getColor(item.number));
                        } else {
                            drawable = mDrawableBuilder.build(String.valueOf(item.number.charAt(0)), mColorGenerator.getColor(item.number));
                        }
                    } else {
                        drawable = mDrawableBuilder.build(String.valueOf(item.name.charAt(0)), mColorGenerator.getColor(item.name));
                    }

                    holder.imageView.setImageDrawable(drawable);
                    holder.round_imageView.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.VISIBLE);
                }
                holder.view.setBackgroundColor(Color.TRANSPARENT);
                holder.checkIcon.setVisibility(View.GONE);
            }
            int count = 0;
            Selected_contacts = new ArrayList<>();
            for (int i = 0; i < contacts.size(); i++) {
                if (contacts.get(i).isChecked) {
                    count = count + 1;
                    Selected_contacts.add(contacts.get(i));
                }
                if (count == 0) {
                    Selected_contacts = new ArrayList<>();
                }
                textView.setText("Selected: " + count);
            }
            Log.d("TAG", "updateCheckedState: ");
        }

        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            contactArrayList.clear();
            if (charText.length() == 0) {
                contactArrayList.addAll(ori);
            } else {
                for (Contact wp : ori) {
                    if (wp.getName().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        contactArrayList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }

    public interface afterloading {
        void afterLoading();
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