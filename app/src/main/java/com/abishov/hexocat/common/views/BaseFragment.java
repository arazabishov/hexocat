package com.abishov.hexocat.common.views;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.abishov.hexocat.Hexocat;

public abstract class BaseFragment extends Fragment {

  @Nullable
  private Unbinder unbinder;

  @Override
  public void onDestroyView() {
    super.onDestroyView();

    ((Hexocat) getActivity().getApplicationContext())
        .refWatcher().watch(this);

    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  protected void bind(Object target, android.view.View view) {
    unbinder = ButterKnife.bind(target, view);
  }
}
