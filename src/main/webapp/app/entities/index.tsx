import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Study from './study';
import Participants from './participants';
import Forms from './forms';
import Questions from './questions';
import Answer from './answer';
import FormAnswer from './form-answer';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}study`} component={Study} />
      <ErrorBoundaryRoute path={`${match.url}participants`} component={Participants} />
      <ErrorBoundaryRoute path={`${match.url}forms`} component={Forms} />
      <ErrorBoundaryRoute path={`${match.url}questions`} component={Questions} />
      <ErrorBoundaryRoute path={`${match.url}answer`} component={Answer} />
      <ErrorBoundaryRoute path={`${match.url}form-answer`} component={FormAnswer} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
