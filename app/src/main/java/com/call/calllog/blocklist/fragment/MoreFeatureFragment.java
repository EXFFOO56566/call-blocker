package com.call.calllog.blocklist.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.call.calllog.blocklist.activity.SettingActivity;
import com.call.calllog.blocklist.activity.WhiteListActivity;
import com.call.calllog.blocklist.object.AppData;
import com.call.calllog.blocklist.R;

import java.util.ArrayList;

public class MoreFeatureFragment extends Fragment {

    private ListView listView;
    private View view;
    private ArrayList<AppData> appDatas = new ArrayList<>();
    private int[] app_icon=new int[]{R.drawable.applock,R.drawable.caller_id};
    private String[] app_name=new String[]{"AppLock","Number Tracker"};
    private String[] app_desc=new String[]{"Lock apps, private photos, secret videos, messages","Quick Caller Location Tracker on Map"};
    private String[] app_package_name=new String[]{"com.amazing.secreateapplock","com.phone.locationtracker.calleridnumberlocator"};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.more_fearure_fragment, container, false);

        listView = (ListView) view.findViewById(R.id.more_feature_listview);

        if(appDatas.size()==0) {
            for (int i = 0; i < app_icon.length; i++) {
                AppData appData = new AppData();
                appData.setApp_icon(app_icon[i]);
                appData.setApp_name(app_name[i]);
                appData.setApp_desc(app_desc[i]);
                appData.setPackage_name(app_package_name[i]);
                appDatas.add(appData);
            }
        }
        MoreFeatureAdapter moreFeatureAdapter = new MoreFeatureAdapter(appDatas);
        listView.setAdapter(moreFeatureAdapter);

        return view;
    }

    private static class ViewHolder {

        private View view;

        private ImageView imageView;

        private TextView txt_name, txt_desc;

        private Button btn_download;

        private ViewHolder(View view) {
            this.view = view;
            imageView = (ImageView) view.findViewById(R.id.app_icon);
            txt_name = (TextView) view.findViewById(R.id.txt_name);
            txt_desc = (TextView) view.findViewById(R.id.app_desc);
            btn_download = (Button) view.findViewById(R.id.btn_download);
        }
    }

    private class MoreFeatureAdapter extends BaseAdapter {

        private ArrayList<AppData> contactArrayList = new ArrayList<>();

        public MoreFeatureAdapter(ArrayList<AppData> contactArrayList) {
            this.contactArrayList = contactArrayList;
        }

        @Override
        public int getCount() {
            return contactArrayList.size();
        }

        @Override
        public AppData getItem(int position) {
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
                convertView = View.inflate(getActivity(), R.layout.row_more_feature, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            final AppData item = getItem(position);

            holder.imageView.setImageResource(item.getApp_icon());
            holder.txt_name.setText("" + item.getApp_name());
            holder.txt_desc.setText("" + item.getApp_desc());

            holder.btn_download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String appPackageName = item.getPackage_name();  // getPackageName() from Context or Activity object
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (android.content.ActivityNotFoundException anfe) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                }
            });

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

}
