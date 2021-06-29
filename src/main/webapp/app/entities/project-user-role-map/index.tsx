import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProjectUserRoleMap from './project-user-role-map';
import ProjectUserRoleMapDetail from './project-user-role-map-detail';
import ProjectUserRoleMapUpdate from './project-user-role-map-update';
import ProjectUserRoleMapDeleteDialog from './project-user-role-map-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectUserRoleMapUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectUserRoleMapUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectUserRoleMapDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProjectUserRoleMap} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectUserRoleMapDeleteDialog} />
  </>
);

export default Routes;
