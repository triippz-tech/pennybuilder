/*
 *     PennyBuilder
 *     Copyright (C) 2021  Mark Tripoli, RamChandraReddy Manda
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.triippztech.pennybuilder.service.dto;

import com.triippztech.pennybuilder.domain.Asset;
import com.triippztech.pennybuilder.domain.PortfolioPosition;
import pl.zankowski.iextrading4j.api.stocks.Quote;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

public class PortfolioPositionQuoteDTO implements Serializable {
    private Long id;

    @NotNull
    private Double quantity;

    @NotNull
    private Boolean isOpen;

    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    private Asset asset;

    private final String symbol;
    private final String companyName;
    private final BigDecimal volume;
    private final BigDecimal iexRealtimePrice;
    private final BigDecimal change;
    private final BigDecimal changePercent;
    private final BigDecimal marketCap;
    private final BigDecimal week52High;
    private final BigDecimal week52Low;
    private final BigDecimal totalValue;

    public PortfolioPositionQuoteDTO(PortfolioPosition portfolioPosition, Quote quote) {
        this.id = portfolioPosition.getId();
        this.quantity = portfolioPosition.getQuantity();
        this.isOpen = portfolioPosition.getIsOpen();
        this.createdDate = portfolioPosition.getCreatedDate();
        this.updatedDate = portfolioPosition.getUpdatedDate();
        this.asset = portfolioPosition.getAsset();
        this.symbol = quote.getSymbol();
        this.companyName = quote.getCompanyName();;
        this.volume = quote.getVolume();
        this.iexRealtimePrice = quote.getIexRealtimePrice();
        this.change = quote.getChange();
        this.changePercent = quote.getChangePercent();
        this.marketCap = quote.getMarketCap();
        this.week52High = quote.getWeek52High();
        this.week52Low = quote.getWeek52Low();
        this.totalValue = quote.getIexRealtimePrice().multiply(BigDecimal.valueOf(portfolioPosition.getQuantity()));
    }

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


    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public BigDecimal getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    public BigDecimal getChange() {
        return change;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public BigDecimal getWeek52High() {
        return week52High;
    }

    public BigDecimal getWeek52Low() {
        return week52Low;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PortfolioPositionQuoteDTO)) {
            return false;
        }

        PortfolioPositionQuoteDTO portfolioPositionDTO = (PortfolioPositionQuoteDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, portfolioPositionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
