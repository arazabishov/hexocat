package com.abishov.hexocat.github.filters;

import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE;

import androidx.annotation.Nullable;
import java.util.Objects;
import org.threeten.bp.LocalDate;

public final class SearchQuery {

  private final LocalDate createdSince;

  SearchQuery(Builder builder) {
    createdSince = Objects.requireNonNull(builder.createdSince);
  }

  @Override
  public String toString() {
    return "created:>=" + ISO_LOCAL_DATE.format(createdSince);
  }

  public static final class Builder {

    @Nullable
    private LocalDate createdSince;

    public Builder createdSince(LocalDate createdSince) {
      this.createdSince = createdSince;
      return this;
    }

    public SearchQuery build() {
      return new SearchQuery(this);
    }
  }
}
