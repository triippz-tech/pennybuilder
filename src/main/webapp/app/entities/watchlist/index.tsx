import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Watchlist from './watchlist';
import WatchlistDetail from './watchlist-detail';
import WatchlistUpdate from './watchlist-update';
import WatchlistDeleteDialog from './watchlist-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WatchlistUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WatchlistUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WatchlistDetail} />
      <ErrorBoundaryRoute path={match.url} component={Watchlist} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WatchlistDeleteDialog} />
  </>
);

export default Routes;
