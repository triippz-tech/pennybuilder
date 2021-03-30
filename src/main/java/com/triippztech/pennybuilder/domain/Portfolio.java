package com.triippztech.pennybuilder.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.triippztech.pennybuilder.domain.enumeration.FiatCurrency;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Portfolio.
 */
@Entity
@Table(name = "portfolio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Portfolio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "portfolio_name", nullable = false)
    private String portfolioName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "base_currency", nullable = false)
    private FiatCurrency baseCurrency;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToMany(mappedBy = "portfolio")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "portfolio", "asset" }, allowSetters = true)
    private Set<PortfolioPosition> positions = new HashSet<>();

    @ManyToOne
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio id(Long id) {
        this.id = id;
        return this;
    }

    public String getPortfolioName() {
        return this.portfolioName;
    }

    public Portfolio portfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
        return this;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public FiatCurrency getBaseCurrency() {
        return this.baseCurrency;
    }

    public Portfolio baseCurrency(FiatCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
        return this;
    }

    public void setBaseCurrency(FiatCurrency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Portfolio isActive(Boolean isActive) {
        this.isActive = isActive;
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Portfolio createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public Portfolio updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<PortfolioPosition> getPositions() {
        return this.positions;
    }

    public Portfolio positions(Set<PortfolioPosition> portfolioPositions) {
        this.setPositions(portfolioPositions);
        return this;
    }

    public Portfolio addPositions(PortfolioPosition portfolioPosition) {
        this.positions.add(portfolioPosition);
        portfolioPosition.setPortfolio(this);
        return this;
    }

    public Portfolio removePositions(PortfolioPosition portfolioPosition) {
        this.positions.remove(portfolioPosition);
        portfolioPosition.setPortfolio(null);
        return this;
    }

    public void setPositions(Set<PortfolioPosition> portfolioPositions) {
        if (this.positions != null) {
            this.positions.forEach(i -> i.setPortfolio(null));
        }
        if (portfolioPositions != null) {
            portfolioPositions.forEach(i -> i.setPortfolio(this));
        }
        this.positions = portfolioPositions;
    }

    public User getOwner() {
        return this.owner;
    }

    public Portfolio owner(User user) {
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
        if (!(o instanceof Portfolio)) {
            return false;
        }
        return id != null && id.equals(((Portfolio) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Portfolio{" +
            "id=" + getId() +
            ", portfolioName='" + getPortfolioName() + "'" +
            ", baseCurrency='" + getBaseCurrency() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
