package com.abishov.hexocat.home

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.abishov.hexocat.R
import com.abishov.hexocat.home.trending.TrendingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasAndroidInjector {

  @Inject
  internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_home)
    findViewById<BottomNavigationView>(R.id.bottom_navigation_home)
      .setOnNavigationItemSelectedListener {
        swapFragment(it.itemId)
        true
      }

    if (savedInstanceState == null) {
      swapFragment(R.id.item_trending)
    }
  }

  override fun androidInjector() = dispatchingAndroidInjector

  private fun swapFragment(@IdRes viewId: Int) {
    val fragment = when (viewId) {
      R.id.item_trending -> TrendingFragment.create(14)
      else -> Fragment()
    }

    supportFragmentManager.beginTransaction()
      .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
      .replace(R.id.framelayout_content, fragment)
      .commitNow()
  }
}
