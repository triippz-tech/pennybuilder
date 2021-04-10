/*
 *     PennyBuilder
 *     Copyright (C) 2021  Mark Tripoli, RamChandraReddy Manda
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserPortfolio from './user-portfolio';
import UserPortfolioDetail from './user-portfolio-detail';
import UserPortfolioUpdate from './user-portfolio-update';
import UserPortfolioDeleteDialog from './user-portfolio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserPortfolioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserPortfolioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserPortfolioDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserPortfolio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserPortfolioDeleteDialog} />
  </>
);

export default Routes;
