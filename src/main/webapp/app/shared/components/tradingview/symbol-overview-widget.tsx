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

export interface ISymbolOverviewWidgetProps {
  symbols: string[];
  width?: string;
  isTransparent?: boolean;
  showSymbolLogo?: boolean;
  height?: string;
  dateRange?: DateRange;
  autosize?: boolean;
  chartOnly?: boolean;
}

const defaultProps: ISymbolOverviewWidgetProps = {
  symbols: [],
  width: "1000",
  height: "400",
  autosize: false,
  isTransparent: false,
  showSymbolLogo: true,
  dateRange: DateRange.DAY_1,
  chartOnly: false
}

/**
 * TradingView Widget for Symbol Overviews
 * https://www.tradingview.com/widget/symbol-overview/
 * @param props {@link IMiniChartWidgetProps}
 */
export const SymbolOverviewWidget = (props: ISymbolOverviewWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/tv.js'
    script.async = false;
    script.innerHTML = JSON.stringify({
      "container_id": "tv-medium-widget",
      "chartOnly": props.chartOnly,
      "showSymbolLogo": props.showSymbolLogo,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "gridLineColor": "#2a2e39",
      "trendLineColor": "#1976d2",
      "fontColor": "#787b86",
      "underLineColor": "rgba(55, 166, 239, 0.15)",
      "locale": "en",
      "symbols": props.symbols,
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

SymbolOverviewWidget.defaultProps = defaultProps;

export default SymbolOverviewWidget;
