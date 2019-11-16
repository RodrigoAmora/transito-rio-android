package br.com.rodrigoamora.transitorio.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.rodrigoamora.transitorio.R;
import br.com.rodrigoamora.transitorio.util.PackageInfoUtil;

public class SobreFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sobre, container, false);
        configurarComponentes(rootView);
        return rootView;
    }

    private void configurarComponentes(View rootView) {
        TextView version = rootView.findViewById(R.id.version);
        version.setText(PackageInfoUtil.getVersionName(getActivity()));
    }
}
