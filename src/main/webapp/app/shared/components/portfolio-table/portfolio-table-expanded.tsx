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

import React from "react";
import SymbolOverviewWidget from "app/shared/components/tradingview/symbol-overview-widget";
import SymbolInfoWidget from "app/shared/components/tradingview/symbol-info-widget";
import MiniChartWidget from "app/shared/components/tradingview/mini-chart-widget";

// eslint-disable-next-line react/prop-types
export default ({ data }) => (
  <pre>
    <MiniChartWidget symbol={data.symbol}/>
  </pre>
);
