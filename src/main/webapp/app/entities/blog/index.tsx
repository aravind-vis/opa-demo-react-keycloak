import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Blog from './blog';
import BlogDetail from './blog-detail';
import BlogUpdate from './blog-update';
import BlogDeleteDialog from './blog-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BlogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BlogUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BlogDetail} />
      <ErrorBoundaryRoute path={match.url} component={Blog} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={BlogDeleteDialog} />
  </>
);

export default Routes;
