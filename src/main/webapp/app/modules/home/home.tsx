import './home.scss';

import React from 'react';

import {connect} from 'react-redux';
import {Col, Row} from 'reactstrap';
import TimelineWidget from "app/shared/components/tradingview/timeline-widget";
import GainersLosers from "app/shared/components/gainers-losers";
import FundamentalDataWidget from "app/shared/components/tradingview/fundamental-data-widget";
import TechnicalAnalysis from "app/shared/components/tradingview/technical-analysis";
import RealTimeChart from "app/shared/components/tradingview/real-time-chart";
import SymbolOverviewWidget from "app/shared/components/tradingview/symbol-overview-widget";
import SymbolInfoWidget from "app/shared/components/tradingview/symbol-info-widget";

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const {account} = props;

  return (
    <>
      <Row className={"d-flex justify-content-center"}>
        <Col md="6">
          <SymbolInfoWidget symbol={"GME"} />
        </Col>
      </Row>
      <br/>
      <Row>
        <Col md="4">
          <FundamentalDataWidget symbol={"GME"}/>
        </Col>
        <Col md="4">
          <TechnicalAnalysis symbol={"GME"}/>
        </Col>
        <Col md="4">
          <TimelineWidget symbol={"GME"}/>
        </Col>
      </Row>
    </>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);
