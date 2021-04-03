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
  MIN1= "1m",
  MIN5 = "5m",
  MIN15 = "15m",
  HOUR1 = "1h",
  HOUR4 = "4h",
  DAY1 = "1D",
  WEEK1 = "1W",
  MONTH1 = "1M"
}
export interface ITechnicalAnalysisProps {
  symbol: string;
  height?: string;
  width?: string;
  interval?: Interval;
  isTransparent?: boolean;
  showIntervalTabs?: boolean;
}

const defaultProps: ITechnicalAnalysisProps = {
  symbol: "AAPL",
  height: "425",
  width: "450",
  interval: Interval.HOUR4,
  isTransparent: false,
  showIntervalTabs: true
}

/**
 * TradingView Widget for a Symbol's technical analysis
 * https://www.tradingview.com/widget/technical-analysis/
 * @param props {@link ITechnicalAnalysisProps}
 */
export const TechnicalAnalysis = (props: ITechnicalAnalysisProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-technical-analysis.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "symbol": props.symbol,
      "isTransparent": props.isTransparent,
      "showIntervalTabs": props.showIntervalTabs,
      "width": props.width,
      "height": props.height,
      "locale": "en",
      "colorTheme": "dark"
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

TechnicalAnalysis.defaultProps = defaultProps;
export default TechnicalAnalysis;
