package br.com.rodrigoamora.transitorio.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentUtil {

    public static void changeFragment(int id, Fragment fragment, FragmentManager fragmentManager, boolean backstack, Bundle bundle) {
        FragmentTransaction transacao = fragmentManager.beginTransaction();
        transacao.replace(id, fragment);

        if (backstack) {
            transacao.addToBackStack(null);
        }

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        transacao.commit();
    }

}
