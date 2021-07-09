import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './participants.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ParticipantsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const participantsEntity = useAppSelector(state => state.participants.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="participantsDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.participants.detail.title">Participants</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.id}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="studiesJHipsterApp.participants.email">Email</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.email}</dd>
          <dt>
            <span id="associatedForms">
              <Translate contentKey="studiesJHipsterApp.participants.associatedForms">Associated Forms</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.associatedForms}</dd>
          <dt>
            <span id="formsCompleted">
              <Translate contentKey="studiesJHipsterApp.participants.formsCompleted">Forms Completed</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.formsCompleted}</dd>
          <dt>
            <span id="languaje">
              <Translate contentKey="studiesJHipsterApp.participants.languaje">Languaje</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.languaje}</dd>
          <dt>
            <span id="invitationStatus">
              <Translate contentKey="studiesJHipsterApp.participants.invitationStatus">Invitation Status</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.invitationStatus}</dd>
          <dt>
            <span id="actions">
              <Translate contentKey="studiesJHipsterApp.participants.actions">Actions</Translate>
            </span>
          </dt>
          <dd>{participantsEntity.actions}</dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.participants.study">Study</Translate>
          </dt>
          <dd>{participantsEntity.study ? participantsEntity.study.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/participants" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/participants/${participantsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ParticipantsDetail;
