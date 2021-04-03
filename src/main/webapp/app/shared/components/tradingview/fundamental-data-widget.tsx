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

export enum DisplayMode {
  COMPACT= "compact",
  ADAPTIVE = "adaptive",
  REGULAR = "regular"
}

export interface IFundamentalDataWidgetProps {
  symbol: string;
  width?: string;
  isTransparent?: boolean;
  height?: string;
  autosize?: boolean;
  displayMode?: DisplayMode;
}

const defaultProps: IFundamentalDataWidgetProps = {
  symbol: "TSLA",
  width: "480",
  height: "830",
  autosize: false,
  isTransparent: false,
  displayMode: DisplayMode.REGULAR
}

/**
 * TradingView Widget for Symbol Overviews
 * https://www.tradingview.com/widget/fundamental-data/
 * @param props {@link IMiniChartWidgetProps}
 */
export const FundamentalDataWidget = (props: IFundamentalDataWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-financials.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": "tv-medium-widget",
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "locale": "en",
      "symbol": props.symbol,
      "height": props.height,
      "width": props.width,
      "displayMode": props.displayMode
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

FundamentalDataWidget.defaultProps = defaultProps;

export default FundamentalDataWidget;
