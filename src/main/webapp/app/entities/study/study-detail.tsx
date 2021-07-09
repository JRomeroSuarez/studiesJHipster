import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './study.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const StudyDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const studyEntity = useAppSelector(state => state.study.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="studyDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.study.detail.title">Study</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{studyEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="studiesJHipsterApp.study.title">Title</Translate>
            </span>
          </dt>
          <dd>{studyEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="studiesJHipsterApp.study.description">Description</Translate>
            </span>
          </dt>
          <dd>{studyEntity.description}</dd>
          <dt>
            <span id="numParticipants">
              <Translate contentKey="studiesJHipsterApp.study.numParticipants">Num Participants</Translate>
            </span>
          </dt>
          <dd>{studyEntity.numParticipants}</dd>
          <dt>
            <span id="numForms">
              <Translate contentKey="studiesJHipsterApp.study.numForms">Num Forms</Translate>
            </span>
          </dt>
          <dd>{studyEntity.numForms}</dd>
        </dl>
        <Button tag={Link} to="/study" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/study/${studyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StudyDetail;
