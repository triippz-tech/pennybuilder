import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PortfolioPosition from './portfolio-position';
import PortfolioPositionDetail from './portfolio-position-detail';
import PortfolioPositionUpdate from './portfolio-position-update';
import PortfolioPositionDeleteDialog from './portfolio-position-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PortfolioPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PortfolioPositionUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PortfolioPositionDetail} />
      <ErrorBoundaryRoute path={match.url} component={PortfolioPosition} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PortfolioPositionDeleteDialog} />
  </>
);

export default Routes;
