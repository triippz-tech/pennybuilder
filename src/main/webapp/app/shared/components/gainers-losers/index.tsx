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
import React, {useEffect, useState} from "react";
import axios from "axios";
import TickerTapeWidget from "app/shared/components/tradingview/ticker-tape-widget";


export const GainersLosers = () => {
  const [symbolList, setSymbolList] = useState<string[]>([]);
  const [render, setRender] = useState(false);

  useEffect(() => {
    axios.get("/api/iex/gainers-losers").then(results => {
      if (results.status === 200) {
        setSymbolList(results.data);
        setRender(true);
      }
    })
  }, [])

  if (render)
    return (
      <div className="pagewide fixed-top">
        <TickerTapeWidget symbols={symbolList.map((symbol) => {
          return {
            proName: symbol
          }
        })} />
      </div>
    )
  return null;
}

export default GainersLosers;
