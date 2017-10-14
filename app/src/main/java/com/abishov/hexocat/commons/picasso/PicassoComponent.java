package com.abishov.hexocat.commons.picasso;

import android.content.Context;

import com.abishov.hexocat.commons.dagger.PicassoScope;
import com.abishov.hexocat.home.repository.RepositoryItemView;

import dagger.Subcomponent;

@PicassoScope
@Subcomponent(modules = PicassoModule.class)
public interface PicassoComponent {
    String SERVICE_PICASSO_COMPONENT = "service:picassoComponent";

    void inject(RepositoryItemView repositoryItemView);

    @SuppressWarnings({"ResourceType", "WrongConstant"})
    static PicassoComponent obtain(Context context) {
        return (PicassoComponent) context.getSystemService(SERVICE_PICASSO_COMPONENT);
    }

    static boolean matchesService(String name) {
        return SERVICE_PICASSO_COMPONENT.equals(name);
    }
}