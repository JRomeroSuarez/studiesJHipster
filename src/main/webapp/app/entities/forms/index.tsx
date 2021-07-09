import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Forms from './forms';
import FormsDetail from './forms-detail';
import FormsUpdate from './forms-update';
import FormsDeleteDialog from './forms-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FormsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FormsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FormsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Forms} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FormsDeleteDialog} />
  </>
);

export default Routes;
