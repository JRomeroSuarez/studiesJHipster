import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Participants from './participants';
import ParticipantsDetail from './participants-detail';
import ParticipantsUpdate from './participants-update';
import ParticipantsDeleteDialog from './participants-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ParticipantsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ParticipantsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ParticipantsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Participants} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ParticipantsDeleteDialog} />
  </>
);

export default Routes;
