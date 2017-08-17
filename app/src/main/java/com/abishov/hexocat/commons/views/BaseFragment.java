package com.abishov.hexocat.commons.views;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.abishov.hexocat.Hexocat;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    @Nullable
    private Unbinder unbinder;

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // check if instance of fragment is leaked or not
        ((Hexocat) getActivity().getApplicationContext())
                .refWatcher().watch(this);

        // unbind butterknife
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void bind(@NonNull Object target, @NonNull android.view.View view) {
        unbinder = ButterKnife.bind(target, view);
    }
}
