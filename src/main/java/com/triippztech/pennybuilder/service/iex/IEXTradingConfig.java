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

import com.triippztech.pennybuilder.config.IEXProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.client.IEXCloudClient;
import pl.zankowski.iextrading4j.client.IEXCloudTokenBuilder;
import pl.zankowski.iextrading4j.client.IEXTradingApiVersion;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import tech.jhipster.config.JHipsterConstants;

import java.util.Arrays;
import java.util.Collection;

@Service
public class IEXTradingConfig {
    private final Logger log = LoggerFactory.getLogger(IEXInfoService.class);
    private final IEXProperties iexProperties;
    private final Environment env;

    public IEXTradingConfig(IEXProperties iexProperties, Environment env) {
        this.iexProperties = iexProperties;
        this.env = env;
    }

    public IEXCloudClient getConnection() {
        log.error(iexProperties.getToken().getPub());
        log.error(iexProperties.getToken().getSecret());
        log.debug("Getting new instance of IEXCloudCLient");

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        var isDev = activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT);
        return IEXTradingClient.create(isDev ? IEXTradingApiVersion.IEX_CLOUD_STABLE_SANDBOX : IEXTradingApiVersion.IEX_CLOUD_STABLE,
            new IEXCloudTokenBuilder()
                .withPublishableToken(iexProperties.getToken().getPub())
                .withSecretToken(iexProperties.getToken().getSecret())
                .build());
    }
}
