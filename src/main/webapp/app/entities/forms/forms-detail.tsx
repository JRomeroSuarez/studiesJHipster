import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './forms.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FormsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const formsEntity = useAppSelector(state => state.forms.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formsDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.forms.detail.title">Forms</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formsEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="studiesJHipsterApp.forms.title">Title</Translate>
            </span>
          </dt>
          <dd>{formsEntity.title}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="studiesJHipsterApp.forms.description">Description</Translate>
            </span>
          </dt>
          <dd>{formsEntity.description}</dd>
          <dt>
            <span id="numResponses">
              <Translate contentKey="studiesJHipsterApp.forms.numResponses">Num Responses</Translate>
            </span>
          </dt>
          <dd>{formsEntity.numResponses}</dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.forms.study">Study</Translate>
          </dt>
          <dd>{formsEntity.study ? formsEntity.study.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/forms" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/forms/${formsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormsDetail;
