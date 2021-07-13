import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './questions.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const QuestionsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const questionsEntity = useAppSelector(state => state.questions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.questions.detail.title">Questions</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.id}</dd>
          <dt>
            <span id="question">
              <Translate contentKey="studiesJHipsterApp.questions.question">Question</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.question}</dd>
          <dt>
            <span id="subtitle">
              <Translate contentKey="studiesJHipsterApp.questions.subtitle">Subtitle</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.subtitle}</dd>
          <dt>
            <span id="info">
              <Translate contentKey="studiesJHipsterApp.questions.info">Info</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.info}</dd>
          <dt>
            <span id="fieldType">
              <Translate contentKey="studiesJHipsterApp.questions.fieldType">Field Type</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.fieldType}</dd>
          <dt>
            <span id="mandatory">
              <Translate contentKey="studiesJHipsterApp.questions.mandatory">Mandatory</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.mandatory ? 'true' : 'false'}</dd>
          <dt>
            <span id="variableName">
              <Translate contentKey="studiesJHipsterApp.questions.variableName">Variable Name</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.variableName}</dd>
          <dt>
            <span id="units">
              <Translate contentKey="studiesJHipsterApp.questions.units">Units</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.units}</dd>
          <dt>
            <span id="conditional">
              <Translate contentKey="studiesJHipsterApp.questions.conditional">Conditional</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.conditional ? 'true' : 'false'}</dd>
          <dt>
            <span id="creationDate">
              <Translate contentKey="studiesJHipsterApp.questions.creationDate">Creation Date</Translate>
            </span>
          </dt>
          <dd>
            {questionsEntity.creationDate ? <TextFormat value={questionsEntity.creationDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="editDate">
              <Translate contentKey="studiesJHipsterApp.questions.editDate">Edit Date</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.editDate ? <TextFormat value={questionsEntity.editDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="actions">
              <Translate contentKey="studiesJHipsterApp.questions.actions">Actions</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.actions}</dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.questions.forms">Forms</Translate>
          </dt>
          <dd>{questionsEntity.forms ? questionsEntity.forms.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/questions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/questions/${questionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsDetail;
