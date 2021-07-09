import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './form-answer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FormAnswerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const formAnswerEntity = useAppSelector(state => state.formAnswer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="formAnswerDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.formAnswer.detail.title">FormAnswer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{formAnswerEntity.id}</dd>
          <dt>
            <span id="creationForm">
              <Translate contentKey="studiesJHipsterApp.formAnswer.creationForm">Creation Form</Translate>
            </span>
          </dt>
          <dd>
            {formAnswerEntity.creationForm ? (
              <TextFormat value={formAnswerEntity.creationForm} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="modifiedForm">
              <Translate contentKey="studiesJHipsterApp.formAnswer.modifiedForm">Modified Form</Translate>
            </span>
          </dt>
          <dd>
            {formAnswerEntity.modifiedForm ? (
              <TextFormat value={formAnswerEntity.modifiedForm} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.formAnswer.forms">Forms</Translate>
          </dt>
          <dd>{formAnswerEntity.forms ? formAnswerEntity.forms.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/form-answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/form-answer/${formAnswerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FormAnswerDetail;
