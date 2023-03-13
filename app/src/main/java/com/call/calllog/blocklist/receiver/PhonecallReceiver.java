package com.call.calllog.blocklist.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.v7.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.call.calllog.blocklist.activity.MainActivity;
import com.call.calllog.blocklist.object.Constant;
import com.call.calllog.blocklist.object.Contact;
import com.call.calllog.blocklist.object.PrefUtils;
import com.call.calllog.blocklist.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhonecallReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static boolean isAnswered;
    private static String savedNumber;
    ArrayList<Contact> arrayList = new ArrayList<>();
    ArrayList<Contact> contactDatas;
    ArrayList<Contact> logList = new ArrayList<>();
    Gson gson = new Gson();
    Type type = new TypeToken<List<Contact>>() {
    }.getType();
    long nationalNumber = 0;
    String countryCode = "";
    int unknown = 0;
    private ITelephony telephonyService;

    private logInterface logInterface;

    public void setinterface(logInterface logInterface1) {
        logInterface = logInterface1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
//
            String state = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            Log.d("TAG", "onReceive: " + number);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                if (!PrefUtils.getContactBlackList(context).equals(""))
                    arrayList = gson.fromJson(PrefUtils.getContactBlackList(context), type);

                int private_number = 1;
                try {
                    private_number = Integer.parseInt(number);
                } catch (Exception e) {

                }
                if (PrefUtils.getAllCalls(context, Constant.ALL_CALLS)) {
                    disconnectPhoneItelephony(context, number);
                    return;
                } else if (PrefUtils.getUnknownNumbers(context, Constant.UNKNOWN_NUMBERS)) {
                    String incommingName = getContactDisplayNameByNumber(number, context);
                    if ((incommingName == null) || (incommingName.length() <= 1)) {
                        disconnectPhoneItelephony(context, number);
                        return;
                    }
                }
                if (private_number <= 0) {
                    if (PrefUtils.getPrivteNumbers(context, Constant.PRIVATE_NUMBERS)) {
                        disconnectPhoneItelephony(context, number);
                        return;
                    }
                }

                if (!PrefUtils.getInputList(context).equals("")) {
                    ArrayList<Contact> add_contact = gson.fromJson(PrefUtils.getInputList(context), type);
                    for (int i = 0; i < add_contact.size(); i++) {
                        if (add_contact.get(i).getType().equalsIgnoreCase("1")) {
                            String[] s = add_contact.get(i).getName().split("\\?");
                            if (s[0].contains(number.substring(1, s[0].length()))) {
                                disconnectPhoneItelephony(context, number);
                                return;
                            }
                        } else {
                            if (number.contains(add_contact.get(i).getName())) {
                                disconnectPhoneItelephony(context, number);
                                return;
                            }
                        }
                    }
                }

                if (arrayList != null && arrayList.size() != 0) {
                    Contact contactData = new Contact();
                    contactData.setNumber(number);

                    if (arrayList.contains(contactData)) {

                        disconnectPhoneItelephony(context, number);
                        return;
                    } else {
                        if (number.startsWith("+")) {
                            try {
                                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                                Phonenumber.PhoneNumber numberProto = phoneUtil.parse(number.trim(), "");

                                countryCode = String.valueOf(numberProto.getCountryCode());
                                nationalNumber = numberProto.getNationalNumber();
                                number = String.valueOf(nationalNumber);

                            } catch (Exception e) {
                            }
                        } else {
                            number = savedNumber.replaceAll("[^0-9]", "");
                        }
                        if (arrayList.contains(contactData)) {

                            disconnectPhoneItelephony(context, number);
                            return;
                        }
                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getContactDisplayNameByNumber(String number, Context c) {

        String name = "?";
        String data = null;
        try {
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
            ContentResolver contentResolver = c.getContentResolver();
            Cursor contactLookup = contentResolver.query(uri, new String[]{BaseColumns._ID,
                    ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);

            try {
                if (contactLookup != null && contactLookup.getCount() > 0) {
                    contactLookup.moveToNext();
                    data = contactLookup.getString(contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                }
            } finally {
                if (contactLookup != null) {
                    contactLookup.close();

                }
            }
        } catch (Exception e) {
            data = "-1";
        }


        return data;
    }

    public boolean contactExists(Context context, String number) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    private void disconnectPhoneItelephony(Context context, String number) {
        if (PrefUtils.getBlocking(context, Constant.BLOCKING)) {
            TelephonyManager telephony = (TelephonyManager)
                    context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class cls = Class.forName(telephony.getClass().getName());
                Method m = cls.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                telephonyService = (ITelephony) m.invoke(telephony);
                //telephonyService.silenceRinger();
                telephonyService.endCall();

                if (!PrefUtils.getLogList(context).equals("")) {
                    logList = gson.fromJson(PrefUtils.getLogList(context), type);
                }

                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());

                SimpleDateFormat df = new SimpleDateFormat("dd MMM,hh:mm");
                String formattedDate = df.format(c.getTime());

                if (number != null) {
                    Contact contact = new Contact();
                    String name = getContactDisplayNameByNumber(number, context);
                    if (name != null) {
                        contact.setName(name);
                    } else {
                        contact.setName(number);
                    }
                    contact.setNumber(number);
                    contact.setTime(formattedDate);
                    logList.add(contact);
                }
                PrefUtils.setLogList(context, gson.toJson(logList));
                if (logInterface != null) {
                    logInterface.refresh();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PrefUtils.getNotification(context, Constant.NOTIFICATIONS)) {
                PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);
                Resources r = context.getResources();
                Notification notification = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.blocking)
                        .setContentTitle(r.getString(R.string.app_name))
                        .setContentText("Click to view log")
                        .setContentIntent(pi)
                        .setAutoCancel(false)
                        .build();

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(2222, notification);
            }

        }
    }

    public interface logInterface {
        void refresh();
    }

}