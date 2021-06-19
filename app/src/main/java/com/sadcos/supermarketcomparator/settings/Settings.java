package com.sadcos.supermarketcomparator.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.sadcos.supermarketcomparator.R;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        ImageView goback = findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    public static class SettingsFragment extends PreferenceFragmentCompat  {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            Intent intent;
            SwitchPreferenceCompat darkmode = findPreference("Dark Mode");
            assert darkmode != null;
            darkmode.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean darkmodeOn = (Boolean) newValue;
                    SharedPreferences cartPreferences= getActivity().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = cartPreferences.edit();
                    if(darkmodeOn) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        editor.putBoolean("nightmode", darkmodeOn);
                        editor.apply();
                    }else{
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        editor.putBoolean("nightmode", darkmodeOn);
                        editor.apply();
                    }
                    return true;
                }
            });
            switch (key) {
                case "Send Feedback":
                    sendFeedback(requireActivity());
                    return true;
                case "Privacy Policy":
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://supermarketcomparator.000webhostapp.com/htmls/privacypolicy.html"));
                    startActivity(intent);
                    return true;
                case "Terms And Conditions":
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://supermarketcomparator.000webhostapp.com/htmls/termsandconditions.html"));
                    startActivity(intent);
                    return true;
                case "FAQ":
                    intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://supermarketcomparator.000webhostapp.com/htmls/faq.html"));
                    startActivity(intent);
                    return true;
                //codes
            }
            return false;
        }
    }
    public static void sendFeedback(Context context) {
        String body = null;
        body = context.getPackageManager().getPackageArchiveInfo(context.getPackageName(), 0).versionName;
        body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"supermarketscrapper@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }}