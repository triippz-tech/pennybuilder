package com.triippztech.pennybuilder.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.triippztech.pennybuilder.domain.WatchlistPosition} entity.
 */
public class WatchlistPositionDTO implements Serializable {

    private Long id;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private WatchlistDTO watchlist;

    private AssetDTO asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public WatchlistDTO getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(WatchlistDTO watchlist) {
        this.watchlist = watchlist;
    }

    public AssetDTO getAsset() {
        return asset;
    }

    public void setAsset(AssetDTO asset) {
        this.asset = asset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchlistPositionDTO)) {
            return false;
        }

        WatchlistPositionDTO watchlistPositionDTO = (WatchlistPositionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchlistPositionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchlistPositionDTO{" +
            "id=" + getId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", watchlist=" + getWatchlist() +
            ", asset=" + getAsset() +
            "}";
    }
}
