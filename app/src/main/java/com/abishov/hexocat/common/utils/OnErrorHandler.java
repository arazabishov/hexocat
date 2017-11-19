package com.abishov.hexocat.common.utils;

import io.reactivex.exceptions.OnErrorNotImplementedException;
import io.reactivex.functions.Consumer;

public final class OnErrorHandler implements Consumer<Throwable> {

  private OnErrorHandler() {
    // use factory method
  }

  public static Consumer<Throwable> create() {
    return new OnErrorHandler();
  }

  @Override
  public void accept(Throwable throwable) {
    throw new OnErrorNotImplementedException(throwable);
  }
}