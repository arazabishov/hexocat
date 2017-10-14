package com.abishov.hexocat.commons.picasso;

import com.abishov.hexocat.commons.dagger.PicassoScope;
import com.abishov.hexocat.home.repository.RepositoryItemView;

import dagger.Subcomponent;

@PicassoScope
@Subcomponent(modules = PicassoModule.class)
public interface PicassoComponent {
    void inject(RepositoryItemView repositoryItemView);
}