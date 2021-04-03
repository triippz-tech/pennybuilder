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

export enum DateRange {
  DAY_1= "1D",
  MONTH_1 = "1M",
  MONTH_3 = "3M",
  YEAR_1 = "1Y",
  YEAR_5 = "5Y",
  ALL = "ALL"
}

export interface IMiniChartWidgetProps {
  symbol: string;
  width?: string;
  isTransparent?: boolean;
  showSymbolLogo?: boolean;
  height?: string;
  dateRange?: DateRange;
}

const defaultProps: IMiniChartWidgetProps = {
  symbol: "BTCUSD",
  width: "100%",
  height: "100%",
  isTransparent: false,
  showSymbolLogo: true,
  dateRange: DateRange.DAY_1
}


/**
 * TradingView Widget for Symbol Mini Charts
 * https://www.tradingview.com/widget/mini-chart/
 * @param props {@link IMiniChartWidgetProps}
 */
export const MiniChartWidget = (props: IMiniChartWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-mini-symbol-overview.js'
    script.async = false;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "showSymbolLogo": props.showSymbolLogo,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "trendLineColor": "#37a6ef",
      "underLineColor": "rgba(55, 166, 239, 0.15)",
      "locale": "en",
      "symbol": props.symbol,
      "height": props.height,
      "width": props.width,
      "dateRange": props.dateRange
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

MiniChartWidget.defaultProps = defaultProps;

export default MiniChartWidget;
