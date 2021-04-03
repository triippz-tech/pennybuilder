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

export interface ICompanyProfileWidgetProps {
  symbol: string;
  width?: string;
  isTransparent?: boolean;
  height?: string;
  autosize?: boolean;
}

const defaultProps: ICompanyProfileWidgetProps = {
  symbol: "TSLA",
  width: "100%",
  height: "100%",
  autosize: false,
  isTransparent: false,
}

/**
 * TradingView Widget for Symbol Overviews
 * https://www.tradingview.com/widget/symbol-profile/
 * @param props {@link ICompanyProfileWidgetProps}
 */
export const CompanyProfileWidget = (props: ICompanyProfileWidgetProps) => {
  const myRef = React.createRef<HTMLDivElement>();

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://s3.tradingview.com/external-embedding/embed-widget-symbol-profile.js'
    script.async = true;
    script.innerHTML = JSON.stringify({
      "container_id": "tv-medium-widget",
      "colorTheme": "dark",
      "isTransparent": props.isTransparent,
      "locale": "en",
      "symbol": props.symbol,
      "height": props.height,
      "width": props.width,
    })
    myRef.current.appendChild(script);
  }, []);

  return (
    <div className="tradingview-widget-container" ref={myRef}>
      <div className="tradingview-widget-container__widget"></div>
    </div>
  );
}

CompanyProfileWidget.defaultProps = defaultProps;

export default CompanyProfileWidget;
