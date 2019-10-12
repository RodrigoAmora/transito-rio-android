package br.com.rodrigoamora.transitorio.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import br.com.rodrigoamora.transitorio.R;
import br.com.rodrigoamora.transitorio.ui.fragment.OnibusFragment;
import br.com.rodrigoamora.transitorio.ui.fragment.SobreFragment;
import br.com.rodrigoamora.transitorio.util.FragmentUtil;
import br.com.rodrigoamora.transitorio.util.PermissionUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        hideNavigationBar();
        navigationBarTransparent();
        changeFragment(new OnibusFragment(), null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            changeFragment(new SobreFragment(), null);
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeFragment(Fragment fragment, Bundle bundle) {
        FragmentUtil.changeFragment(R.id.container, fragment, getSupportFragmentManager(), false, bundle);
    }

    private void checkPermissions() {
        PermissionUtil.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION});
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    private void navigationBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
