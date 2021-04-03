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

export interface Symbols {
  name: string;
  displayName?: string;
}

export interface SymbolsGroup {
  name: string;
  symbols: Symbols[];
}

export interface IMarketDataProps {
  symbolsGroups: SymbolsGroup[];
  height?: string;
  width?: string;
  isTransparent?: boolean;
}

const defaultProps: IMarketDataProps = {
  symbolsGroups: [],
  height: "100%",
  width: "100%",
  isTransparent: false,
}

/**
 * TradingView Widget for MarketData
 * https://www.tradingview.com/widget/market-quotes/
 * @param props {@link IMarketDataProps}
 */
export const MarketData = (props: IMarketDataProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-market-quotes.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "width": props.width,
      "height": props.height,
      "showSymbolLogo": true,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "locale": "en",
      "symbolsGroups": props.symbolsGroups
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

MarketData.defaultProps = defaultProps;
export default MarketData;
