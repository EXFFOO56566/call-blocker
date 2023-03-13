package com.call.calllog.blocklist.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.call.calllog.blocklist.activity.SettingActivity;
import com.call.calllog.blocklist.activity.ToastAdListener;
import com.call.calllog.blocklist.activity.WhiteListActivity;
import com.call.calllog.blocklist.object.Contact;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.object.RoundedImageView;
import com.call.calllog.blocklist.receiver.PhonecallReceiver;
import com.call.calllog.blocklist.textdrawable.TextDrawable;
import com.call.calllog.blocklist.textdrawable.util.ColorGenerator;
import com.call.calllog.blocklist.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.call.calllog.blocklist.activity.ContactsActivity.getContactBitmapFromURI;

/**
 * Created by jksol3 on 22/2/17.
 */

public class LogFragment extends Fragment implements PhonecallReceiver.logInterface {

    public static contactRefresh refresh;
    Gson gson = new Gson();
    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    private TextView textView;
    private View view;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private ArrayList<Contact> logList = new ArrayList<>();
    private ArrayList<Contact> contactList = new ArrayList<>();

    private InterstitialAd interstitialAds;

    public static void setInterface(contactRefresh anInterface) {
        refresh = anInterface;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.blacklist_fragment, container, false);

        textView = (TextView) view.findViewById(R.id.black_textview);
        textView.setText(getString(R.string.log_fragment));
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating);
        listView = (ListView) view.findViewById(R.id.black_list);
        floatingActionButton.setImageResource(R.drawable.delete);
        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();


        click();

        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }
        PhonecallReceiver phonecallReceiver = new PhonecallReceiver();
        phonecallReceiver.setinterface(this);

        Log.d("TAG", "onCreateView: "+ PrefUtils.getLogList(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setAdapter();
    }

    private void click() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }

        if (!PrefUtils.getLogList(getActivity()).equals("")) {
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
            adb.setTitle("Delete all");
            adb.setMessage("Are you sure you want to clear the log?");

            adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    logList = new ArrayList<Contact>();
                    PrefUtils.setLogList(getActivity(), gson.toJson(logList));
                    dialog.dismiss();
                    logList = new ArrayList<>();

                    if (!PrefUtils.getLogList(getActivity()).equals("")) {
                        logList = gson.fromJson(PrefUtils.getLogList(getActivity()), type);
                    }
                    if (logList.size() != 0) {
                        LogAdapter logAdapter = new LogAdapter(logList);
                        listView.setAdapter(logAdapter);
                        listView.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);
                    } else {
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                    }
                }
            });
            adb.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = adb.create();
            alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
            alertDialog.show();
        }
    }

    @Override
    public void refresh() {
        Log.d("TAG", "refresh: ");
        setAdapter();
    }

    public void setAdapter() {
        logList = new ArrayList<>();

        if (!PrefUtils.getLogList(getActivity()).equals("")) {
            logList = gson.fromJson(PrefUtils.getLogList(getActivity()), type);
        }
        if (!PrefUtils.getContactBlackList(getActivity()).equals("")) {
            contactList = gson.fromJson(PrefUtils.getContactBlackList(getActivity()), type);
            for (int i = 0; i < contactList.size(); i++) {
                contactList.get(i).setContact_image(getContactImage(contactList.get(i).getNumber()));
            }
        }

        if (logList.size() == 0) {
            floatingActionButton.setBackgroundTintList((getActivity().getResources().getColorStateList(R.color.colorPrimary)));
        }else{
            floatingActionButton.setBackgroundTintList((getActivity().getResources().getColorStateList(R.color.colorAccent)));
        }


        if (logList.size() != 0) {
            LogAdapter logAdapter = new LogAdapter(logList);
            listView.setAdapter(logAdapter);
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }
    public String getContactImage(String phNumber) {

        String image_uri = null;
        Cursor cursor = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.NUMBER  + " = ?" , new String[] {phNumber}, null);

        while (cursor.moveToNext()) {
            image_uri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
        }
        cursor.close();
        return image_uri;
    }
    public void firstDialog(final Contact contact) {
        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        adb.setTitle(contact.getName());

        final String[] Colors = new String[]{
                "Delete", "Call",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("delete")) {
                    logList.remove(contact);
                    ArrayList<Contact> contacts = new ArrayList<Contact>();
                    contacts.addAll(logList);
                    PrefUtils.setLogList(getActivity(), gson.toJson(contacts));
                    setAdapter();
                } else if (clickedItemValue.equalsIgnoreCase("call")) {
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + contact.getNumber()));
                    startActivity(intent);
                }
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        alertDialog.show();
    }

    public void secondDialog(final Contact contact) {


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        adb.setTitle(contact.getName());

        final String[] Colors = new String[]{
                "Add to blacklist", "Delete", "Call", "Add to whitelist",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("delete")) {
                    logList.remove(contact);
                    ArrayList<Contact> contacts = new ArrayList<Contact>();
                    contacts.addAll(logList);
                    PrefUtils.setLogList(getActivity(), gson.toJson(contacts));
                    setAdapter();
                } else if (clickedItemValue.equalsIgnoreCase("call")) {
                    Intent intent = new Intent(Intent.ACTION_CALL);

                    intent.setData(Uri.parse("tel:" + contact.getNumber()));
                    startActivity(intent);
                } else if (clickedItemValue.equalsIgnoreCase("add to blacklist")) {
                    contactList.add(contact);
                    ArrayList<Contact> contactDatas = new ArrayList<Contact>();
                    if (!PrefUtils.getContactBlackList(getActivity()).equals("")) {
                        contactDatas = gson.fromJson(PrefUtils.getContactBlackList(getActivity()), type);
                        contactList.addAll(contactDatas);
                    }
                    PrefUtils.setContactBlackList(getActivity(), gson.toJson(contactList));
                    refresh.contactRefresh();
                } else if (clickedItemValue.equalsIgnoreCase("add to whitelist")) {
                    Intent intent = new Intent(getActivity(), WhiteListActivity.class);
                    startActivity(intent);
                }
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        alertDialog.show();
    }

    public interface contactRefresh {
        void contactRefresh();
    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;
        private RoundedImageView round_log_imageView;

        private TextView log_name, log_number;

        private TextView time;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.log_imageView);
            log_name = (TextView) view.findViewById(R.id.log_name);
            log_number = (TextView) view.findViewById(R.id.log_number);
            time = (TextView) view.findViewById(R.id.log_time);
            round_log_imageView= (RoundedImageView) view.findViewById(R.id.round_log_imageView);
        }
    }

    private class LogAdapter extends BaseAdapter {

        private ArrayList<Contact> contactArrayList = new ArrayList<>();

        public LogAdapter(ArrayList<Contact> contactArrayList) {
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
                convertView = View.inflate(getActivity(), R.layout.raw_log_call, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final Contact item = getItem(position);
            if(item.getContact_image()!=null)
            {
                Bitmap mBitmap = getContactBitmapFromURI(getActivity(), Uri.parse(item.getContact_image()));
                holder.round_log_imageView.setImageBitmap(mBitmap);
                holder.round_log_imageView.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
            }else {
                try {
                    TextDrawable drawable;

                    if (!Character.isLetter(item.name.charAt(0))) {
                        drawable = mDrawableBuilder.build(String.valueOf(item.name.substring(0, 2)), mColorGenerator.getColor(item.name));
                    } else {
                        drawable = mDrawableBuilder.build(String.valueOf(item.name.charAt(0)), mColorGenerator.getColor(item.name));
                    }
                    holder.imageView.setImageDrawable(drawable);
                    holder.round_log_imageView.setVisibility(View.GONE);
                    holder.imageView.setVisibility(View.VISIBLE);
                } catch (Exception e) {

                }
            }
            // provide support for selected state
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contactList = new ArrayList<Contact>();
                    if (!PrefUtils.getContactBlackList(getActivity()).equalsIgnoreCase("")) {
                        contactList = gson.fromJson(PrefUtils.getContactBlackList(getActivity()), type);
                        if (contactList.size() != 0 && contactList != null) {
                            if (contactList.contains(item)) {
                                firstDialog(item);
                            }
                        }
                    } else {
                        secondDialog(item);
                    }
                    // when the image is clicked, update the selected state
                }
            });
            holder.log_name.setText(item.name);
            holder.log_number.setText(item.number);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM,hh:mm");
            Calendar c = Calendar.getInstance();
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = sdf.parse(item.time);
                date2 = sdf.parse(sdf.format(c.getTime()));


            if (date1.compareTo(date2) < 0) {
                String[] s = item.getTime().split(",");
                holder.time.setText(s[1]);
            } else {
                holder.time.setText(item.time);
            }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
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
