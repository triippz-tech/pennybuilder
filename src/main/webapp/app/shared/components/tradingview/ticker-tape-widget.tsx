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

export interface Symbols {
  proName: string;
  title?: string;
}

export interface ITickerTapeWidgetProps {
  symbols: Symbols[];
  displayMode?: DisplayMode;
  isTransparent?: boolean;
  showSymbolLogo?: boolean;
}

const defaultProps: ITickerTapeWidgetProps = {
  symbols: [],
  isTransparent: false,
  showSymbolLogo: true
}


/**
 * TradingView Widget for Ticker Tapes
 * https://www.tradingview.com/widget/ticker/
 * @param props {@link ITickerTapeWidgetProps}
 */
export const TickerTapeWidget = (props: ITickerTapeWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-ticker-tape.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": `tradingview-widget-${Math.random()}`,
      "showSymbolLogo": props.showSymbolLogo,
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "locale": "en",
      "displayMode": props.displayMode,
      "symbols": props.symbols
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

TickerTapeWidget.defaultProps = defaultProps;

export default TickerTapeWidget;
