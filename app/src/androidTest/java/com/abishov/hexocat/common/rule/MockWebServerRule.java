package com.abishov.hexocat.common.rule;

import com.abishov.hexocat.HexocatTestApp;
import io.appflate.restmock.RESTMockServer;
import okhttp3.HttpUrl;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public final class MockWebServerRule implements TestRule {

  @Override
  public Statement apply(Statement base, Description description) {
    RESTMockServer.reset();
    HexocatTestApp.overrideBaseUrl(HttpUrl.parse(RESTMockServer.getUrl()));
    return base;
  }
}
