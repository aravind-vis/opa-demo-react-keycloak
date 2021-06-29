import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Post from './post';
import PostDetail from './post-detail';
import PostUpdate from './post-update';
import PostDeleteDialog from './post-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PostUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PostDetail} />
      <ErrorBoundaryRoute path={match.url} component={Post} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PostDeleteDialog} />
  </>
);

export default Routes;
