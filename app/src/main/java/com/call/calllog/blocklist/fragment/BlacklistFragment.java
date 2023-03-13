package com.call.calllog.blocklist.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.call.calllog.blocklist.activity.AddActivity;
import com.call.calllog.blocklist.activity.CallLogActivity;
import com.call.calllog.blocklist.activity.ContactsActivity;
import com.call.calllog.blocklist.activity.SettingActivity;
import com.call.calllog.blocklist.activity.ToastAdListener;
import com.call.calllog.blocklist.activity.WhiteListActivity;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static com.call.calllog.blocklist.activity.ContactsActivity.getContactBitmapFromURI;

/**
 * Created by jksol3 on 22/2/17.
 */

public class BlacklistFragment extends Fragment implements LogFragment.contactRefresh,CallLogActivity.afterloading {

    public static Comparator<Contact> name = new Comparator<Contact>() {

        public int compare(Contact app1, Contact app2) {

            Contact stringName1 = app1;
            Contact stringName2 = app2;

            return stringName1.getName().compareToIgnoreCase(stringName2.getName());
        }
    };
    Gson gson = new Gson();
    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    private TextView textView;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private View view;
    private ArrayList<Contact> contactDatas = new ArrayList<>();
    private TextDrawable.IBuilder mDrawableBuilder;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private BlackListAdapter blackListAdapter;
    private ProgressBar progressBar;

    private InterstitialAd interstitialAds;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.blacklist_fragment, container, false);

        progressBar= (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        textView = (TextView) view.findViewById(R.id.black_textview);
        textView.setText(getString(R.string.blacklict_fragment));

        listView = (ListView) view.findViewById(R.id.black_list);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floating);

        floatingActionButton.setImageResource(R.drawable.plus);

        mDrawableBuilder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .round();

        LogFragment.setInterface(this);
        click();

        return view;
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

        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        adb.setTitle("Add");

        final String[] Colors = new String[]{
                "From calls log", "From contacts", "Input number", "Begins with",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("input number")) {
                    Intent intent = new Intent(getActivity(), AddActivity.class);
                    intent.putExtra("value", 0);
                    startActivity(intent);
                } else if (clickedItemValue.equalsIgnoreCase("begins with")) {
                    Intent intent = new Intent(getActivity(), AddActivity.class);
                    intent.putExtra("value", 1);
                    startActivity(intent);
                } else if (clickedItemValue.equalsIgnoreCase("From contacts")) {
                    progressBar.setVisibility(View.VISIBLE);
                    ContactsActivity.setLoading(BlacklistFragment.this);
                    Intent intent = new Intent(getActivity(), ContactsActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else if (clickedItemValue.equalsIgnoreCase("From calls log")) {
                    progressBar.setVisibility(View.VISIBLE);
                    CallLogActivity.setLoading(BlacklistFragment.this);
                    Intent intent = new Intent(getActivity(), CallLogActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                }
            }
        });

        AlertDialog alertDialog = adb.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    public void setAdapter() {
        contactDatas = new ArrayList<>();
        if (!PrefUtils.getContactBlackList(getActivity()).equals(""))
            contactDatas = gson.fromJson(PrefUtils.getContactBlackList(getActivity()), type);

        Collections.sort(contactDatas, name);

        for (int i = 0; i < contactDatas.size(); i++) {
            contactDatas.get(i).setChecked(false);
            Log.d("TAG", "onResume: " + contactDatas.get(i).getNumber());
        }
        if (contactDatas.size() != 0) {
            blackListAdapter = new BlackListAdapter(contactDatas);
            listView.setAdapter(blackListAdapter);
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void contactRefresh() {
        setAdapter();
    }

    public void deleteDialog(final Contact contact) {


        Random random = new Random();
        int l = random.nextInt(2);
        if (l == 1) {
            googleintersial();
        }


        AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
        adb.setTitle(contact.getName());

        final String[] Colors = new String[]{
                "Delete",
        };
        adb.setItems(Colors, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String clickedItemValue = Arrays.asList(Colors).get(which);
                if (clickedItemValue.equalsIgnoreCase("delete")) {
                    if (contactDatas.contains(contact)) {
                        contactDatas.remove(contact);
                        ArrayList<Contact> contacts = new ArrayList<Contact>();
                        PrefUtils.setContactBlackList(getActivity(), gson.toJson(contacts));
                        PrefUtils.setContactBlackList(getActivity(), gson.toJson(contactDatas));
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.blacklist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings:

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

                return true;
            case R.id.white_list:
                Intent intent1 = new Intent(getActivity(), WhiteListActivity.class);
                startActivity(intent1);
                return true;
            case R.id.delete_all:
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity(), R.style.MyAlertDialogStyle);
                adb.setTitle("Delete all");
                adb.setMessage("Are you sure you want to clear the blacklist?");

                adb.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Contact> blacklist = new ArrayList<Contact>();
                        PrefUtils.setContactBlackList(getActivity(), gson.toJson(blacklist));
                        dialog.dismiss();
                        listView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void afterLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;
        private RoundedImageView roundedImageView;

        private TextView textView;

        private ImageView checkIcon;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.imageView);
            textView = (TextView) view.findViewById(R.id.textView);
            checkIcon = (ImageView) view.findViewById(R.id.check_icon);
            roundedImageView= (RoundedImageView) view.findViewById(R.id.round_imageView);
        }
    }

    private class BlackListAdapter extends BaseAdapter {

        private ArrayList<Contact> contactArrayList = new ArrayList<>();

        public BlackListAdapter(ArrayList<Contact> contactArrayList) {
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
                convertView = View.inflate(getActivity(), R.layout.list_item_layout, null);
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
            if (item.getContact_image() != null) {
                Bitmap mBitmap = getContactBitmapFromURI(getActivity(), Uri.parse(item.getContact_image()));
                holder.roundedImageView.setImageBitmap(mBitmap);
                holder.roundedImageView.setVisibility(View.VISIBLE);
                holder.imageView.setVisibility(View.GONE);
            } else {
                TextDrawable drawable;
                drawable = mDrawableBuilder.build(String.valueOf(item.name.charAt(0)), mColorGenerator.getColor(item.name));
                holder.imageView.setImageDrawable(drawable);
                holder.imageView.setVisibility(View.VISIBLE);
                holder.roundedImageView.setVisibility(View.GONE);
            }
            holder.view.setBackgroundColor(Color.TRANSPARENT);
            holder.checkIcon.setVisibility(View.GONE);
            Log.d("TAG", "updateCheckedState: ");
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
