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
 * A Watchlist.
 */
@Entity
@Table(name = "watchlist")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Watchlist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "watchlist_name", nullable = false)
    private String watchlistName;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "watchlist")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "watchlist", "asset" }, allowSetters = true)
    private Set<WatchlistPosition> positions = new HashSet<>();

    @ManyToOne
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Watchlist id(Long id) {
        this.id = id;
        return this;
    }

    public String getWatchlistName() {
        return this.watchlistName;
    }

    public Watchlist watchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
        return this;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Watchlist isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Watchlist createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public Watchlist updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<WatchlistPosition> getPositions() {
        return this.positions;
    }

    public Watchlist positions(Set<WatchlistPosition> watchlistPositions) {
        this.setPositions(watchlistPositions);
        return this;
    }

    public Watchlist addPositions(WatchlistPosition watchlistPosition) {
        this.positions.add(watchlistPosition);
        watchlistPosition.setWatchlist(this);
        return this;
    }

    public Watchlist removePositions(WatchlistPosition watchlistPosition) {
        this.positions.remove(watchlistPosition);
        watchlistPosition.setWatchlist(null);
        return this;
    }

    public void setPositions(Set<WatchlistPosition> watchlistPositions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setWatchlist(null));
        }
        if (watchlistPositions != null) {
            watchlistPositions.forEach(i -> i.setWatchlist(this));
        }
        this.positions = watchlistPositions;
    }

    public User getOwner() {
        return this.owner;
    }

    public Watchlist owner(User user) {
        this.setOwner(user);
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Watchlist)) {
            return false;
        }
        return id != null && id.equals(((Watchlist) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Watchlist{" +
            "id=" + getId() +
            ", watchlistName='" + getWatchlistName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
