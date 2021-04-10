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

package com.triippztech.pennybuilder.web.rest.iex;

import com.triippztech.pennybuilder.config.Constants;
import com.triippztech.pennybuilder.service.iex.IEXInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.zankowski.iextrading4j.api.stocks.Quote;
import tech.jhipster.web.util.PaginationUtil;

import java.util.List;

@RestController
@RequestMapping("/api/iex")
public class IEXInfoController {
    private final Logger log = LoggerFactory.getLogger(IEXInfoController.class);

    private final IEXInfoService iexInfoService;

    public IEXInfoController(IEXInfoService iexInfoService) {
        this.iexInfoService = iexInfoService;
    }

    @GetMapping("/gainers-losers")
    public ResponseEntity<List<String>> getGainerLoserSymbols() {
        log.debug("REST request to get a list of top gainers and losers");
        List<String> symbols = iexInfoService.getGainersAndLosers();
        return ResponseEntity.ok().body(symbols);
    }

    @GetMapping("/info/{asset}")
    public ResponseEntity<Quote> getAssetInfo(@PathVariable String asset) {
        log.debug("REST request to get a Quote for: {}", asset);
        Quote quote = iexInfoService.getQuote(asset);
        return ResponseEntity.ok().body(quote);
    }
}
