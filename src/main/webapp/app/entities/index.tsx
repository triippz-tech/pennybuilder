import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserProfile from './user-profile';
import UserSetting from './user-setting';
import Asset from './asset';
import Portfolio from './portfolio';
import PortfolioPosition from './portfolio-position';
import Watchlist from './watchlist';
import WatchlistPosition from './watchlist-position';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}user-profile`} component={UserProfile} />
      <ErrorBoundaryRoute path={`${match.url}user-setting`} component={UserSetting} />
      <ErrorBoundaryRoute path={`${match.url}asset`} component={Asset} />
      <ErrorBoundaryRoute path={`${match.url}portfolio`} component={Portfolio} />
      <ErrorBoundaryRoute path={`${match.url}portfolio-position`} component={PortfolioPosition} />
      <ErrorBoundaryRoute path={`${match.url}watchlist`} component={Watchlist} />
      <ErrorBoundaryRoute path={`${match.url}watchlist-position`} component={WatchlistPosition} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
