import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Role from './role';
import RoleDetail from './role-detail';
import RoleUpdate from './role-update';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/share`} component={RoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RoleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Role} />
    </Switch>
  </>
);

export default Routes;
