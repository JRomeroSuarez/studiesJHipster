import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IQuestions } from 'app/shared/model/questions.model';
import { getEntities as getQuestions } from 'app/entities/questions/questions.reducer';
import { IFormAnswer } from 'app/shared/model/form-answer.model';
import { getEntities as getFormAnswers } from 'app/entities/form-answer/form-answer.reducer';
import { getEntity, updateEntity, createEntity, reset } from './answer.reducer';
import { IAnswer } from 'app/shared/model/answer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AnswerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const questions = useAppSelector(state => state.questions.entities);
  const formAnswers = useAppSelector(state => state.formAnswer.entities);
  const answerEntity = useAppSelector(state => state.answer.entity);
  const loading = useAppSelector(state => state.answer.loading);
  const updating = useAppSelector(state => state.answer.updating);
  const updateSuccess = useAppSelector(state => state.answer.updateSuccess);

  const handleClose = () => {
    props.history.push('/answer');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getQuestions({}));
    dispatch(getFormAnswers({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...answerEntity,
      ...values,
      questions: questions.find(it => it.id.toString() === values.questionsId.toString()),
      formAnswer: formAnswers.find(it => it.id.toString() === values.formAnswerId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...answerEntity,
          questionsId: answerEntity?.questions?.id,
          formAnswerId: answerEntity?.formAnswer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="studiesJHipsterApp.answer.home.createOrEditLabel" data-cy="AnswerCreateUpdateHeading">
            <Translate contentKey="studiesJHipsterApp.answer.home.createOrEditLabel">Create or edit a Answer</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('studiesJHipsterApp.answer.answer')}
                id="answer-answer"
                name="answer"
                data-cy="answer"
                type="text"
              />
              <ValidatedField
                id="answer-questions"
                name="questionsId"
                data-cy="questions"
                label={translate('studiesJHipsterApp.answer.questions')}
                type="select"
              >
                <option value="" key="0" />
                {questions
                  ? questions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="answer-formAnswer"
                name="formAnswerId"
                data-cy="formAnswer"
                label={translate('studiesJHipsterApp.answer.formAnswer')}
                type="select"
              >
                <option value="" key="0" />
                {formAnswers
                  ? formAnswers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/answer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AnswerUpdate;
