package com.triippztech.pennybuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PortfolioPosition.
 */
@Entity
@Table(name = "portfolio_position")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PortfolioPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @NotNull
    @Column(name = "is_open", nullable = false)
    private Boolean isOpen;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "positions", "owner" }, allowSetters = true)
    private Portfolio portfolio;

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

    public PortfolioPosition id(Long id) {
        this.id = id;
        return this;
    }

    public Double getQuantity() {
        return this.quantity;
    }

    public PortfolioPosition quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Boolean getIsOpen() {
        return this.isOpen;
    }

    public PortfolioPosition isOpen(Boolean isOpen) {
        this.isOpen = isOpen;
        return this;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public PortfolioPosition createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public PortfolioPosition updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Portfolio getPortfolio() {
        return this.portfolio;
    }

    public PortfolioPosition portfolio(Portfolio portfolio) {
        this.setPortfolio(portfolio);
        return this;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Asset getAsset() {
        return this.asset;
    }

    public PortfolioPosition asset(Asset asset) {
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
        if (!(o instanceof PortfolioPosition)) {
            return false;
        }
        return id != null && id.equals(((PortfolioPosition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PortfolioPosition{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", isOpen='" + getIsOpen() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
