package com.triippztech.pennybuilder.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.triippztech.pennybuilder.domain.PortfolioPosition;
import com.triippztech.pennybuilder.domain.enumeration.FiatCurrency;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.triippztech.pennybuilder.domain.Portfolio} entity.
 */
public class PortfolioDTO implements Serializable {

    private Long id;

    @NotNull
    private String portfolioName;

    @NotNull
    private FiatCurrency baseCurrency;

    @NotNull
    private Boolean isActive;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private UserDTO owner;

    @JsonIgnoreProperties("portfolio")
    private Set<PortfolioPosition> positions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public FiatCurrency getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(FiatCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
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

    public Set<PortfolioPosition> getPositions() {
        return positions;
    }

    public void setPositions(Set<PortfolioPosition> positions) {
        this.positions = positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioDTO)) {
            return false;
        }

        PortfolioDTO portfolioDTO = (PortfolioDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portfolioDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortfolioDTO{" +
            "id=" + getId() +
            ", portfolioName='" + getPortfolioName() + "'" +
            ", baseCurrency='" + getBaseCurrency() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", owner=" + getOwner() +
            "}";
    }
}
