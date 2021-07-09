import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import FormAnswer from './form-answer';
import FormAnswerDetail from './form-answer-detail';
import FormAnswerUpdate from './form-answer-update';
import FormAnswerDeleteDialog from './form-answer-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={FormAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={FormAnswerUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={FormAnswerDetail} />
      <ErrorBoundaryRoute path={match.url} component={FormAnswer} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={FormAnswerDeleteDialog} />
  </>
);

export default Routes;
