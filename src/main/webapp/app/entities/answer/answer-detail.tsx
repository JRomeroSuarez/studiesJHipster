import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './answer.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnswerDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const answerEntity = useAppSelector(state => state.answer.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="answerDetailsHeading">
          <Translate contentKey="studiesJHipsterApp.answer.detail.title">Answer</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{answerEntity.id}</dd>
          <dt>
            <span id="answer">
              <Translate contentKey="studiesJHipsterApp.answer.answer">Answer</Translate>
            </span>
          </dt>
          <dd>{answerEntity.answer}</dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.answer.questions">Questions</Translate>
          </dt>
          <dd>{answerEntity.questions ? answerEntity.questions.id : ''}</dd>
          <dt>
            <Translate contentKey="studiesJHipsterApp.answer.formAnswer">Form Answer</Translate>
          </dt>
          <dd>{answerEntity.formAnswer ? answerEntity.formAnswer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/answer" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/answer/${answerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AnswerDetail;
