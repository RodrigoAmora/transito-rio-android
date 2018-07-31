package br.com.rodrigoamora.transitorio.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.rodrigoamora.transitorio.R;
import br.com.rodrigoamora.transitorio.ui.fragment.OnibusFragment;
import br.com.rodrigoamora.transitorio.util.FragmentUtil;
import br.com.rodrigoamora.transitorio.util.PermissionUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionUtil.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION});
        FragmentUtil.changeFragment(R.id.conatiner, new OnibusFragment(), getSupportFragmentManager(), false, null);
    }

}
