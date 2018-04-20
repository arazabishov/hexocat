package com.abishov.hexocat

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import com.abishov.hexocat.common.utils.CrashReportingTree
import com.jakewharton.threetenabp.AndroidThreeTen
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import hu.supercluster.paperwork.Paperwork
import okhttp3.HttpUrl
import org.threeten.bp.Clock
import timber.log.Timber
import javax.inject.Inject

open class Hexocat : Application(), HasActivityInjector {

  protected lateinit var appComponent: AppComponent
  protected lateinit var refWatcher: RefWatcher

  @Inject
  internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

  @Inject
  internal lateinit var paperwork: Paperwork

  override fun onCreate() {
    super.onCreate()

    if (LeakCanary.isInAnalyzerProcess(this)) {
      // this process is going to be used to
      // analyze heap dumps by LeakCanary
      return
    }

    AndroidThreeTen.init(this)

    setupAppComponent()
    setUpLeakCanary()
    setUpTimber()

    // Do not allow to do any work on the
    // main thread. Detect activity leaks.
    setupStrictMode()
  }

  override fun activityInjector() = dispatchingAndroidInjector

  private fun setupStrictMode() {
    if (BuildConfig.DEBUG) {
      StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .build()
      )
      StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
          .detectAll()
          .penaltyLog()
          .build()
      )
    }
  }

  protected fun setupAppComponent() {
    appComponent = prepareAppComponent()
    appComponent.inject(this)
  }

  private fun setUpLeakCanary() {
    refWatcher = if (BuildConfig.DEBUG) {
      LeakCanary.install(this)
    } else {
      RefWatcher.DISABLED
    }
  }

  private fun setUpTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    } else {
      Timber.plant(CrashReportingTree(paperwork))
    }
  }

  protected open fun prepareAppComponent(): AppComponent {
    return DaggerAppComponent.builder()
      .baseUrl(HttpUrl.parse("http://api.github.com")!!)
      .clock(Clock.systemDefaultZone())
      .application(this)
      .build()
  }

  fun appComponent(): AppComponent {
    return appComponent
  }

  fun refWatcher(): RefWatcher {
    return refWatcher
  }
}
