import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Questions from './questions';
import QuestionsDetail from './questions-detail';
import QuestionsUpdate from './questions-update';
import QuestionsDeleteDialog from './questions-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={QuestionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={QuestionsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={QuestionsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Questions} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={QuestionsDeleteDialog} />
  </>
);

export default Routes;
