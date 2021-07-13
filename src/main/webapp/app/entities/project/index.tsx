import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Project from './project';
import ProjectDetail from './project-detail';
import ProjectUpdate from './project-update';
import ProjectDeleteDialog from './project-delete-dialog';
import ProjectUserRoleMap from '../project-user-role-map/project-user-role-map';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProjectUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProjectDetail} />
      <ErrorBoundaryRoute exact path={'/project-user-role-map/new'} component={ProjectUserRoleMap} />
      <ErrorBoundaryRoute path={match.url} component={Project} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProjectDeleteDialog} />
  </>
);

export default Routes;
