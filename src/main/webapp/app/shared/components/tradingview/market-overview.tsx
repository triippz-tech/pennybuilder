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

export interface Symbols {
  s: string;
  d?: string;
}

export interface Tabs {
  title: string;
  originalTitle: string;
  symbols: Symbols[];
}

export interface IMarketOverViewProps {
  tabs: Tabs[];
  height?: string;
  width?: string;
  dateRange?: DateRange;
  isTransparent?: boolean;
  showSymbolLogo?: boolean;
}

/**
 * TradingView Widget for Market Overviews
 * https://www.tradingview.com/widget/market-overview/
 * @param props {@link IMarketOverViewProps}
 */
export const MarketOverview = (props: IMarketOverViewProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-market-overview.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "dateRange": props.dateRange,
      "tabs": props.tabs,
      "greyText": "Quotes by",
      "plotLineColorGrowing": "rgba(25, 118, 210, 1)",
      "plotLineColorFalling": "rgba(25, 118, 210, 1)",
      "gridLineColor": "rgba(42, 46, 57, 1)",
      "scaleFontColor": "rgba(120, 123, 134, 1)",
      "belowLineFillColorGrowing": "rgba(33, 150, 243, 0.12)",
      "belowLineFillColorFalling": "rgba(33, 150, 243, 0.12)",
      "symbolActiveColor": "rgba(33, 150, 243, 0.12)",
      "width": props.width,
      "height": props.height,
      "locale": "en",
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "showSymbolLogo": props.showSymbolLogo,
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}
