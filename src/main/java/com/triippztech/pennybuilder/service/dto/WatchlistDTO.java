package com.triippztech.pennybuilder.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.triippztech.pennybuilder.domain.Watchlist} entity.
 */
public class WatchlistDTO implements Serializable {

    private Long id;

    @NotNull
    private String watchlistName;

    @NotNull
    private Boolean isActive;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private UserDTO owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWatchlistName() {
        return watchlistName;
    }

    public void setWatchlistName(String watchlistName) {
        this.watchlistName = watchlistName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WatchlistDTO)) {
            return false;
        }

        WatchlistDTO watchlistDTO = (WatchlistDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, watchlistDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WatchlistDTO{" +
            "id=" + getId() +
            ", watchlistName='" + getWatchlistName() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", owner=" + getOwner() +
            "}";
    }
}
