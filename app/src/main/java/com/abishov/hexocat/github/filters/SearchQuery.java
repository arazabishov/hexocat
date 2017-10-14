package com.abishov.hexocat.github.filters;

import org.threeten.bp.LocalDate;

import static com.abishov.hexocat.commons.utils.Preconditions.isNull;
import static org.threeten.bp.format.DateTimeFormatter.ISO_LOCAL_DATE;

public final class SearchQuery {
    private final LocalDate createdSince;

    private SearchQuery(Builder builder) {
        createdSince = isNull(builder.createdSince, "createdSince == null");
    }

    @Override
    public String toString() {
        return "created:>=" + ISO_LOCAL_DATE.format(createdSince);
    }

    public static final class Builder {
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
