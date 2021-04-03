/*
  Copyright 2021 Mark Tripoli (triippztech.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import React, {useEffect} from "react";

export enum Interval {
  MIN1= "1",
  MIN3 = "3",
  MIN5 = "5",
  MIN15 = "15",
  HOUR1 = "60",
  HOUR2 = "120",
  HOUR3 = "180",
  HOUR4 = "240",
  DAY1 = "D",
  WEEK1 = "W",
}

export enum ChartStyle {
  BARS = 0,
  CANDLES = 1,
  HALLOW_CANDLES = 9,
  HEIKIN_ASHI= 8,
  LINE = 2,
  AREA =3,
  RENKO = 4,
  LINE_BREAK = 7,
  KAGI =5,
  POINT_AND_FIG= 6
}

export interface IRealTimeChartProps {
  symbol: string;
  width?: string;
  height?: string;
  interval?: Interval;
  timezone?: string;
  allowSymbolChange?: boolean;
  enablePublishing?: boolean;
  chartStyle?: ChartStyle;
  showDetails?: boolean;
  showToolBar?: boolean;
  watchlist?: string[];
}

const defaultProps: IRealTimeChartProps = {
  symbol: "GME",
  timezone: "Etc/UTC",
  width: "980",
  height: "610",
  interval: Interval.HOUR1,
  allowSymbolChange: true,
  enablePublishing: false,
  chartStyle: ChartStyle.CANDLES,
  showDetails: false,
  showToolBar: false,
  watchlist: []
}


/**
 * TradingView Widget for Symbol Mini Charts
 * https://www.tradingview.com/widget/mini-chart/
 * @param props {@link IMiniChartWidgetProps}
 */
export const RealTimeChart = (props: IRealTimeChartProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/tv.js';
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "theme": "dark",
      "details": props.showDetails,
      "toolbar_bg": "#f1f3f6",
      "timezone": props.timezone,
      "style": props.chartStyle,
      "hide_side_toolbar": props.showToolBar,
      "enable_publishing": props.enablePublishing,
      "allow_symbol_change": props.allowSymbolChange,
      "locale": "en",
      "symbol": props.symbol,
      "height": props.height,
      "width": props.width,
      "interval": props.interval,
      "watchlist": props.watchlist
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

RealTimeChart.defaultProps = defaultProps;

export default RealTimeChart;

