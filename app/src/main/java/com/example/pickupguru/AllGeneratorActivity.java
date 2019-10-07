package com.example.pickupguru;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Random;

public class AllGeneratorActivity extends AppCompatActivity {

    String[] finalList;

    public void onShare(View v)
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String text = ((TextView)findViewById(R.id.textView)).getText().toString();
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    public void generate(View v)
    {
        Random rn = new Random();
        TextView textView = findViewById(R.id.textView);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.generate_anim);

        if(finalList.length > 0)
            textView.setText(finalList[rn.nextInt(finalList.length-1)]);
        else
            textView.setText("Ошибка, ахтунг, арбайтен");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("animation", false))
        {
            textView.startAnimation(anim);
        }



        ScrollView sv = (ScrollView)findViewById(R.id.scrollView);

        sv.scrollTo(0,0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean("night", false))
            setTheme(R.style.FeedActivityThemeDark);
        else
            setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Bundle b = getIntent().getExtras();
        String fileName = "";
        String groupName = "";
        if(b != null) {
            fileName = b.getString("file");
            groupName = b.getString("name");
            setTitle(groupName);
        }

        if(groupName.equalsIgnoreCase("Крышесносы") || groupName.equalsIgnoreCase("Рутины"))
        {
            TextView textView = findViewById(R.id.textView);
            textView.setTextSize(20);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_INHERIT);
        }

        finalList = MyShared.getFileString(this, fileName).split("###");

        generate(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.action_comment:
                MyShared.showRateActive(this);
                return true;
            case R.id.action_exit:
                finishAffinity();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
