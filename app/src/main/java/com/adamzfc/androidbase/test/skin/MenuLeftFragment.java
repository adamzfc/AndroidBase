package com.adamzfc.androidbase.test.skin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/28/17.
 */

public class MenuLeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test_skin_menu, container, false);
    }
}
