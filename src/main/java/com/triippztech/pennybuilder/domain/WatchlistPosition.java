package com.triippztech.pennybuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WatchlistPosition.
 */
@Entity
@Table(name = "watchlist_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WatchlistPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "owner" }, allowSetters = true)
    private Watchlist watchlist;

    @ManyToOne
    @JsonIgnoreProperties(value = { "portfolioPositions", "watchlistPositions" }, allowSetters = true)
    private Asset asset;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WatchlistPosition id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public WatchlistPosition createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public WatchlistPosition updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Watchlist getWatchlist() {
        return this.watchlist;
    }

    public WatchlistPosition watchlist(Watchlist watchlist) {
        this.setWatchlist(watchlist);
        return this;
    }

    public void setWatchlist(Watchlist watchlist) {
        this.watchlist = watchlist;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public WatchlistPosition asset(Asset asset) {
        this.setAsset(asset);
        return this;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchlistPosition)) {
            return false;
        }
        return id != null && id.equals(((WatchlistPosition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchlistPosition{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
