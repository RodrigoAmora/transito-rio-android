package br.com.rodrigoamora.transitorio.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
