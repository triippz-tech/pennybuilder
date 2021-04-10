package com.triippztech.pennybuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Asset.
 */
@Entity
@Table(name = "asset")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "symbol", nullable = false)
    private String symbol;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "portfolio", "asset" }, allowSetters = true)
    private Set<PortfolioPosition> portfolioPositions = new HashSet<>();

    @OneToMany(mappedBy = "asset")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "watchlist", "asset" }, allowSetters = true)
    private Set<WatchlistPosition> watchlistPositions = new HashSet<>();

    public Asset() {
    }

    public Asset(@NotNull String name, @NotNull String symbol, ZonedDateTime createdDate) {
        this.name = name;
        this.symbol = symbol;
        this.createdDate = createdDate;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Asset name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public Asset symbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Asset createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public Asset updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<PortfolioPosition> getPortfolioPositions() {
        return this.portfolioPositions;
    }

    public Asset portfolioPositions(Set<PortfolioPosition> portfolioPositions) {
        this.setPortfolioPositions(portfolioPositions);
        return this;
    }

    public Asset addPortfolioPositions(PortfolioPosition portfolioPosition) {
        this.portfolioPositions.add(portfolioPosition);
        portfolioPosition.setAsset(this);
        return this;
    }

    public Asset removePortfolioPositions(PortfolioPosition portfolioPosition) {
        this.portfolioPositions.remove(portfolioPosition);
        portfolioPosition.setAsset(null);
        return this;
    }

    public void setPortfolioPositions(Set<PortfolioPosition> portfolioPositions) {
        if (this.portfolioPositions != null) {
            this.portfolioPositions.forEach(i -> i.setAsset(null));
        }
        if (portfolioPositions != null) {
            portfolioPositions.forEach(i -> i.setAsset(this));
        }
        this.portfolioPositions = portfolioPositions;
    }

    public Set<WatchlistPosition> getWatchlistPositions() {
        return this.watchlistPositions;
    }

    public Asset watchlistPositions(Set<WatchlistPosition> watchlistPositions) {
        this.setWatchlistPositions(watchlistPositions);
        return this;
    }

    public Asset addWatchlistPositions(WatchlistPosition watchlistPosition) {
        this.watchlistPositions.add(watchlistPosition);
        watchlistPosition.setAsset(this);
        return this;
    }

    public Asset removeWatchlistPositions(WatchlistPosition watchlistPosition) {
        this.watchlistPositions.remove(watchlistPosition);
        watchlistPosition.setAsset(null);
        return this;
    }

    public void setWatchlistPositions(Set<WatchlistPosition> watchlistPositions) {
        if (this.watchlistPositions != null) {
            this.watchlistPositions.forEach(i -> i.setAsset(null));
        }
        if (watchlistPositions != null) {
            watchlistPositions.forEach(i -> i.setAsset(this));
        }
        this.watchlistPositions = watchlistPositions;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Asset)) {
            return false;
        }
        return id != null && id.equals(((Asset) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Asset{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", symbol='" + getSymbol() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
