import React from 'react';
import { Switch, useLocation } from 'react-router-dom';
import Loadable from 'react-loadable';

import Login from 'app/modules/login/login';
import Register from 'app/modules/account/register/register';
import Activate from 'app/modules/account/activate/activate';
import PasswordResetInit from 'app/modules/account/password-reset/init/password-reset-init';
import PasswordResetFinish from 'app/modules/account/password-reset/finish/password-reset-finish';
import Logout from 'app/modules/login/logout';
import Home from 'app/modules/home/home';
import Entities from 'app/entities';
import PrivateRoute from 'app/shared/auth/private-route';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';
import PageNotFound from 'app/shared/error/page-not-found';
import { AUTHORITIES } from 'app/config/constants';
import { sendActivity } from 'app/config/websocket-middleware';
import ToCPage from "app/modules/generic/terms-and-conditions";
import PrivacyPolicy from "app/modules/generic/privacy-policy";

const Account = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/account'),
  loading: () => <div>loading ...</div>,
});

const UserPortfolios = Loadable({
  loader: () => import(/* webpackChunkName: "account" */ 'app/modules/portfolios'),
  loading: () => <div>loading ...</div>,
});

const Admin = Loadable({
  loader: () => import(/* webpackChunkName: "administration" */ 'app/modules/administration'),
  loading: () => <div>loading ...</div>,
});

const Routes = () => {
  const location = useLocation();
  React.useEffect(() => {
    sendActivity(location.pathname);
  }, [location]);
  return (
    <div className="view-routes">
      <Switch>
        <ErrorBoundaryRoute path="/login" component={Login} />
        <ErrorBoundaryRoute path="/terms" component={ToCPage} />
        <ErrorBoundaryRoute path="/privacy-policy" component={PrivacyPolicy} />
        <ErrorBoundaryRoute path="/logout" component={Logout} />
        <ErrorBoundaryRoute path="/account/register" component={Register} />
        <ErrorBoundaryRoute path="/account/activate/:key?" component={Activate} />
        <ErrorBoundaryRoute path="/account/reset/request" component={PasswordResetInit} />
        <ErrorBoundaryRoute path="/account/reset/finish/:key?" component={PasswordResetFinish} />
        <PrivateRoute path="/portfolios" component={UserPortfolios} hasAnyAuthorities={[AUTHORITIES.USER]} />
        <PrivateRoute path="/admin" component={Admin} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
        <PrivateRoute path="/account" component={Account} hasAnyAuthorities={[AUTHORITIES.ADMIN, AUTHORITIES.USER]} />
        <ErrorBoundaryRoute path="/" exact component={Home} />
        {/* <PrivateRoute path="/" component={Entities} hasAnyAuthorities={[AUTHORITIES.USER]} /> */}
        <ErrorBoundaryRoute component={PageNotFound} />
      </Switch>
    </div>
  );
};

export default Routes;
