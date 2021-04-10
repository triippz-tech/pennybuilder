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

package com.triippztech.pennybuilder.service.iex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.refdata.v1.SymbolDescription;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import pl.zankowski.iextrading4j.api.stocks.v1.BatchStocks;
import pl.zankowski.iextrading4j.client.rest.request.refdata.v1.SearchSymbolRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ListRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.ListType;
import pl.zankowski.iextrading4j.client.rest.request.stocks.QuoteRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.v1.BatchMarketStocksRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.v1.BatchStocksType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IEXInfoService {
    private final Logger log = LoggerFactory.getLogger(IEXInfoService.class);

    private final IEXTradingConfig iexTradingConfig;

    public IEXInfoService(IEXTradingConfig iexTradingConfig) {
        this.iexTradingConfig = iexTradingConfig;
    }

    public List<String> getGainersAndLosers() {
        final List<Quote> quotes = this.iexTradingConfig.getConnection().executeRequest(new ListRequestBuilder()
            .withListType(ListType.GAINERS)
            .withListType(ListType.LOSERS)
            .withListType(ListType.IEXPERCENT)
            .build());
        return quotes.stream().map(Quote::getSymbol).collect(Collectors.toList());
    }

    public Quote getQuote(String asset) {
        return this.iexTradingConfig.getConnection().executeRequest(new QuoteRequestBuilder()
            .withSymbol(asset.toLowerCase())
            .build());
    }

    public Map<String, BatchStocks> getQuotes(List<String> assets) {
        var batchStocksRequestBuilder = new BatchMarketStocksRequestBuilder();
        assets.forEach(batchStocksRequestBuilder::withSymbol);
        return this.iexTradingConfig.getConnection().executeRequest(
            batchStocksRequestBuilder.addType(BatchStocksType.QUOTE).build());
    }

    public List<SymbolDescription> searchAssets(String query) {
        return iexTradingConfig.getConnection().executeRequest(
                new SearchSymbolRequestBuilder().withFragment(query).build());
    }
}
