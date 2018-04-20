package com.abishov.hexocat.home

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.abishov.hexocat.R
import com.abishov.hexocat.common.picasso.PicassoServiceLocator
import com.abishov.hexocat.home.trending.create
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

  @BindView(R.id.bottom_navigation_home)
  internal lateinit var bottomNavigationView: BottomNavigationView

  @Inject
  internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  @Inject
  internal lateinit var transformation: Transformation

  @Inject
  internal lateinit var picasso: Picasso

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_home)
    ButterKnife.bind(this)

    bottomNavigationView.setOnNavigationItemSelectedListener {
      swapFragment(it.itemId)
      true
    }

    if (savedInstanceState == null) {
      swapFragment(R.id.item_trending)
    }
  }

  override fun supportFragmentInjector() = dispatchingAndroidInjector

  override fun getSystemService(name: String): Any? {
    if (PicassoServiceLocator.matchesService(name)) {
      return picasso
    }

    if (PicassoServiceLocator.matchesTransformationService(name)) {
      return transformation
    }

    return super.getSystemService(name)
  }

  private fun swapFragment(@IdRes viewId: Int) {
    val fragment = when (viewId) {
      R.id.item_trending -> create()
      else -> Fragment()
    }

    supportFragmentManager.beginTransaction()
      .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
      .replace(R.id.framelayout_content, fragment)
      .commitNow()
  }
}
