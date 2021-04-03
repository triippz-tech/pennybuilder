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

import React, {PropsWithChildren, useEffect} from "react";

export enum DateRange {
  DAY_1= "1D",
  MONTH_1 = "1M",
  MONTH_3 = "3M",
  YEAR_1 = "1Y",
  YEAR_5 = "5Y",
  ALL = "ALL"
}

interface StockMarketProps extends PropsWithChildren<any>{
  height?: string;
  width?: string;
  dateRange?: DateRange;
  isTransparent?: boolean;
  showChart?: boolean;
  exchange?: string;
  showSymbolLogo?: boolean;
}

const defaultProps: StockMarketProps = {
  height: "100%",
  width: "100%",
  dateRange: DateRange.YEAR_1,
  isTransparent: false,
  showChart: true,
  exchange: "US",
  showSymbolLogo: true,
}

/**
 * TradingView Widget for Stock Market Top 5 Gaining, Losing, and active stocks for the day
 * https://www.tradingview.com/widget/market-overview/
 * @param props {@link StockMarketProps}
 */
export const StockMarket: React.FC<StockMarketProps> = (props) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-hotlists.js'
    script.async = false;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "dateRange": props.dateRange,
      "width": `${props.width ? props.width : '100%'}`,
      "height": `${props.height ? props.height : '100%'}`,
      "showChart": props.showChart,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "exchange": props.exchange,
      "showSymbolLogo": props.showSymbolLogo,
      "locale": "en",
      "plotLineColorGrowing": "rgba(25, 118, 210, 1)",
      "plotLineColorFalling": "rgba(25, 118, 210, 1)",
      "gridLineColor": "rgba(42, 46, 57, 1)",
      "scaleFontColor": "rgba(120, 123, 134, 1)",
      "belowLineFillColorGrowing": "rgba(33, 150, 243, 0.12)",
      "belowLineFillColorFalling": "rgba(33, 150, 243, 0.12)",
      "symbolActiveColor": "rgba(33, 150, 243, 0.12)"
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

StockMarket.defaultProps = defaultProps;
export default StockMarket;
