package bree.com.bewwweibo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bree on 2017/5/10.
 */

public class MyPreference {
    private static MyPreference instance;
    private static SharedPreferences settings;

    private MyPreference() {
    }


    public static MyPreference getInstance(Context context) {
        if (instance == null) {
            synchronized (MyPreference.class) {
                instance = new MyPreference();
                settings = context.getSharedPreferences("bree", PreferenceActivity.MODE_PRIVATE);
            }
        }
        return instance;
    }

    public void write(String tag, String str) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putString(tag, str);
        edit.commit();
    }

    public void write(String tag, boolean value) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putBoolean(tag, value);
        edit.commit();
    }

    public void write(String tag, int value) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt(tag, value);
        edit.commit();
    }

    public void writeLong(String tag, long value) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putLong(tag, value);
        edit.commit();
    }

    public void writeFloat(String tag, float value) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putFloat(tag, value);
        edit.commit();
    }

    public void writeArray(String tag, List<String> items) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt(tag + "_num:", items.size());
        int i = 0;
        for (String s : items) {
            edit.putString(tag + "_" + i, s);
            i++;
        }
        edit.commit();
    }

    public void writeMap(String tag, List<Map<String, String>> items) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt(tag + "_num:", items.size());
        int i = 0;
        for (Map<String, String> map : items) {
            for (String s : map.keySet()) {
                edit.putString(tag + "_" + i + "_" + s, map.get(s));
            }
            i++;
        }
        edit.commit();
    }

    public String read(String tag) {
        return settings.getString(tag, "");
    }

    public String read(String tag, String defaultv) {
        return settings.getString(tag, defaultv);
    }

    public boolean readBoolean(String tag) {
        return settings.getBoolean(tag, false);
    }

    public boolean readBoolean(String tag, boolean defaultv) {
        return settings.getBoolean(tag, defaultv);
    }

    public int readInt(String tag) {
        return settings.getInt(tag, 0);
    }

    public int readInt(String tag, int defaultv) {
        return settings.getInt(tag, defaultv);
    }

    public long readLong(String tag, long defaultv) {
        return settings.getLong(tag, defaultv);
    }

    public float readFloat(String tag, float defaultv) {
        return settings.getFloat(tag, defaultv);
    }

    public List<String> readArray(String tag) {
        String v = null;
        List<String> items = new ArrayList<String>();
        int num = settings.getInt(tag + "_num:", 0);
        int i = 0;
        while (i < num) {
            v = settings.getString(tag + "_" + i, null);
            if (v != null)
                items.add(v);
            i++;
        }

        return items;
    }

    public List<Map<String, String>> readMapArray(String tag) {
        String v = null;
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        int num = settings.getInt(tag + "_num:", 0);
        int i = 0;
        while (i < num) {
            Map<String, String> map = new HashMap<String, String>();

            int k = 0;
            do {
                v = settings.getString(tag + "_" + i + "_" + k, null);
                if (v != null) {
                    map.put("" + k, v);
                }
                k++;
            } while (v != null);

            items.add(map);
            i++;
        }

        return items;
    }

    public void delect(String tag) {
        SharedPreferences.Editor edit = settings.edit();
        edit.remove(tag);
        edit.commit();
    }
}
