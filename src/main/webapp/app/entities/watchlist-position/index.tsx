import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WatchlistPosition from './watchlist-position';
import WatchlistPositionDetail from './watchlist-position-detail';
import WatchlistPositionUpdate from './watchlist-position-update';
import WatchlistPositionDeleteDialog from './watchlist-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WatchlistPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WatchlistPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WatchlistPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={WatchlistPosition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WatchlistPositionDeleteDialog} />
  </>
);

export default Routes;
