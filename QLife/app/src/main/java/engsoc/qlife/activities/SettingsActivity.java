package engsoc.qlife.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import engsoc.qlife.PrefManager;
import engsoc.qlife.Util;
import engsoc.qlife.database.local.DatabaseAccessor;
import engsoc.qlife.database.local.users.User;
import engsoc.qlife.database.local.SqlStringStatements;

import engsoc.qlife.R;
import engsoc.qlife.database.local.users.UserManager;
import engsoc.qlife.interfaces.IQLOptionsMenuActivity;

import java.util.ArrayList;

/**
 * Activity for the settings. Can see NetID, time since calendar was last synced and can logout here
 */
public class SettingsActivity extends AppCompatActivity implements IQLOptionsMenuActivity {

    private void clearData(View v) {
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
        WebView web = new WebView(getApplicationContext());
        web.clearFormData();
        web.clearHistory();
        web.clearCache(true);

        v.getContext().deleteDatabase(SqlStringStatements.PHONE_DATABASE_NAME);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clearData(v);
                PrefManager mPrefManager = new PrefManager(getApplicationContext());
                mPrefManager.setFirstTimeLaunch(true);
                Toast.makeText(SettingsActivity.this, getString(R.string.logged_out), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingsActivity.this, StartupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setBackButton();

        setTextViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DatabaseAccessor.getDatabase().close(); //ensure only one database connection is ever open
    }

    /**
     * Method that sets the current user and last login time in the TextViews.
     */
    private void setTextViews() {
        UserManager mUserManager = new UserManager(this.getApplicationContext());
        ArrayList<User> users = mUserManager.getTable();
        User user = users.get(0); //only ever one user in database
        TextView netID = (TextView) findViewById(R.id.netID);
        TextView date = (TextView) findViewById(R.id.login_date);
        date.setText(user.getDateInit());
        netID.setText(user.getNetid());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleOptionsClick(item.getItemId());
        return true;
    }

    @Override
    public void setBackButton() {
        Util.setBackButton(getSupportActionBar());
    }

    @Override
    public void handleOptionsClick(int itemId) {
        switch (itemId) {
            case R.id.about:
                startActivity(new Intent(SettingsActivity.this, AboutActivity.class));
                break;
            case R.id.review:
                startActivity(new Intent(SettingsActivity.this, ReviewActivity.class));
                break;
            case android.R.id.home:
                finish();
        }
    }

    @Override
    public void inflateOptionsMenu(Menu menu) {
        Util.inflateOptionsMenu(R.menu.settings_menu, menu, getMenuInflater());
    }
}