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

export interface ISymbolInfoWidgetProps {
  symbol: string;
  width?: string;
  isTransparent?: boolean;
  autosize?: boolean;
  chartOnly?: boolean;
}

const defaultProps: ISymbolInfoWidgetProps = {
  symbol: "AAPL",
  width: "1000",
  autosize: false,
  isTransparent: false,
  chartOnly: false
}

/**
 * TradingView Widget for Symbol Info
 * https://www.tradingview.com/widget/symbol-info/
 * @param props {@link ISymbolInfoWidgetProps}
 */
export const SymbolInfoWidget = (props: ISymbolInfoWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-symbol-info.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "locale": "en",
      "symbol": props.symbol,
      "width": props.width,
      "autosize": props.autosize
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

SymbolInfoWidget.defaultProps = defaultProps;

export default SymbolInfoWidget;
