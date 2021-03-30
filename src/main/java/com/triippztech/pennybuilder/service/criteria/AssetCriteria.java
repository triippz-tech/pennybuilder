package com.triippztech.pennybuilder.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;
import tech.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link com.triippztech.pennybuilder.domain.Asset} entity. This class is used
 * in {@link com.triippztech.pennybuilder.web.rest.AssetResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /assets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssetCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter symbol;

    private ZonedDateTimeFilter createdDate;

    private ZonedDateTimeFilter updatedDate;

    private LongFilter portfolioPositionsId;

    private LongFilter watchlistPositionsId;

    public AssetCriteria() {}

    public AssetCriteria(AssetCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.symbol = other.symbol == null ? null : other.symbol.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedDate = other.updatedDate == null ? null : other.updatedDate.copy();
        this.portfolioPositionsId = other.portfolioPositionsId == null ? null : other.portfolioPositionsId.copy();
        this.watchlistPositionsId = other.watchlistPositionsId == null ? null : other.watchlistPositionsId.copy();
    }

    @Override
    public AssetCriteria copy() {
        return new AssetCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSymbol() {
        return symbol;
    }

    public StringFilter symbol() {
        if (symbol == null) {
            symbol = new StringFilter();
        }
        return symbol;
    }

    public void setSymbol(StringFilter symbol) {
        this.symbol = symbol;
    }

    public ZonedDateTimeFilter getCreatedDate() {
        return createdDate;
    }

    public ZonedDateTimeFilter createdDate() {
        if (createdDate == null) {
            createdDate = new ZonedDateTimeFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTimeFilter createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTimeFilter getUpdatedDate() {
        return updatedDate;
    }

    public ZonedDateTimeFilter updatedDate() {
        if (updatedDate == null) {
            updatedDate = new ZonedDateTimeFilter();
        }
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTimeFilter updatedDate) {
        this.updatedDate = updatedDate;
    }

    public LongFilter getPortfolioPositionsId() {
        return portfolioPositionsId;
    }

    public LongFilter portfolioPositionsId() {
        if (portfolioPositionsId == null) {
            portfolioPositionsId = new LongFilter();
        }
        return portfolioPositionsId;
    }

    public void setPortfolioPositionsId(LongFilter portfolioPositionsId) {
        this.portfolioPositionsId = portfolioPositionsId;
    }

    public LongFilter getWatchlistPositionsId() {
        return watchlistPositionsId;
    }

    public LongFilter watchlistPositionsId() {
        if (watchlistPositionsId == null) {
            watchlistPositionsId = new LongFilter();
        }
        return watchlistPositionsId;
    }

    public void setWatchlistPositionsId(LongFilter watchlistPositionsId) {
        this.watchlistPositionsId = watchlistPositionsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetCriteria that = (AssetCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(symbol, that.symbol) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedDate, that.updatedDate) &&
            Objects.equals(portfolioPositionsId, that.portfolioPositionsId) &&
            Objects.equals(watchlistPositionsId, that.watchlistPositionsId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, symbol, createdDate, updatedDate, portfolioPositionsId, watchlistPositionsId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssetCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (symbol != null ? "symbol=" + symbol + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedDate != null ? "updatedDate=" + updatedDate + ", " : "") +
            (portfolioPositionsId != null ? "portfolioPositionsId=" + portfolioPositionsId + ", " : "") +
            (watchlistPositionsId != null ? "watchlistPositionsId=" + watchlistPositionsId + ", " : "") +
            "}";
    }
}
