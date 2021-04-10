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

import {IAsset} from "app/shared/model/asset.model";
import bigDecimal from "js-big-decimal";

export interface PositionQuote {
  id?: number;
  quantity?: number;
  isOpen?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  asset?: IAsset | null;
  symbol?: string;
  companyName?: string;
  volume?: bigDecimal;
  iexRealtimePrice?: bigDecimal;
  change?: bigDecimal;
  changePercent?: bigDecimal;
  marketCap?: bigDecimal;
  week52High?: bigDecimal;
  week52Low?: bigDecimal;
  ytdChange?: bigDecimal;
  totalValue?: bigDecimal;
}
