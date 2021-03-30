package com.triippztech.pennybuilder.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.triippztech.pennybuilder.domain.PortfolioPosition} entity.
 */
public class PortfolioPositionDTO implements Serializable {

    private Long id;

    @NotNull
    private Double quantity;

    @NotNull
    private Boolean isOpen;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private PortfolioDTO portfolio;

    private AssetDTO asset;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
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

    public PortfolioDTO getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(PortfolioDTO portfolio) {
        this.portfolio = portfolio;
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
        if (!(o instanceof PortfolioPositionDTO)) {
            return false;
        }

        PortfolioPositionDTO portfolioPositionDTO = (PortfolioPositionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portfolioPositionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortfolioPositionDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", isOpen='" + getIsOpen() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", portfolio=" + getPortfolio() +
            ", asset=" + getAsset() +
            "}";
    }
}
