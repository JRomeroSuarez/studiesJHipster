import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IForms } from 'app/shared/model/forms.model';
import { getEntities as getForms } from 'app/entities/forms/forms.reducer';
import { getEntity, updateEntity, createEntity, reset } from './questions.reducer';
import { IQuestions } from 'app/shared/model/questions.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const QuestionsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const forms = useAppSelector(state => state.forms.entities);
  const questionsEntity = useAppSelector(state => state.questions.entity);
  const loading = useAppSelector(state => state.questions.loading);
  const updating = useAppSelector(state => state.questions.updating);
  const updateSuccess = useAppSelector(state => state.questions.updateSuccess);

  const handleClose = () => {
    props.history.push('/questions');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getForms({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.creationDate = convertDateTimeToServer(values.creationDate);
    values.editDate = convertDateTimeToServer(values.editDate);

    const entity = {
      ...questionsEntity,
      ...values,
      forms: forms.find(it => it.id.toString() === values.formsId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          creationDate: displayDefaultDateTime(),
          editDate: displayDefaultDateTime(),
        }
      : {
          ...questionsEntity,
          fieldType: 'INFO',
          creationDate: convertDateTimeFromServer(questionsEntity.creationDate),
          editDate: convertDateTimeFromServer(questionsEntity.editDate),
          formsId: questionsEntity?.forms?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="studiesJHipsterApp.questions.home.createOrEditLabel" data-cy="QuestionsCreateUpdateHeading">
            <Translate contentKey="studiesJHipsterApp.questions.home.createOrEditLabel">Create or edit a Questions</Translate>
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
                  id="questions-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.question')}
                id="questions-question"
                name="question"
                data-cy="question"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.subtitle')}
                id="questions-subtitle"
                name="subtitle"
                data-cy="subtitle"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.info')}
                id="questions-info"
                name="info"
                data-cy="info"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.fieldType')}
                id="questions-fieldType"
                name="fieldType"
                data-cy="fieldType"
                type="select"
              >
                <option value="INFO">{translate('studiesJHipsterApp.FieldType.INFO')}</option>
                <option value="FREE_TEXT">{translate('studiesJHipsterApp.FieldType.FREE_TEXT')}</option>
                <option value="RANGE">{translate('studiesJHipsterApp.FieldType.RANGE')}</option>
                <option value="DATA">{translate('studiesJHipsterApp.FieldType.DATA')}</option>
                <option value="SINGLE_SELECT">{translate('studiesJHipsterApp.FieldType.SINGLE_SELECT')}</option>
                <option value="NUMERIC">{translate('studiesJHipsterApp.FieldType.NUMERIC')}</option>
                <option value="MULTIPLE_SELECT">{translate('studiesJHipsterApp.FieldType.MULTIPLE_SELECT')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.mandatory')}
                id="questions-mandatory"
                name="mandatory"
                data-cy="mandatory"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.variableName')}
                id="questions-variableName"
                name="variableName"
                data-cy="variableName"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.units')}
                id="questions-units"
                name="units"
                data-cy="units"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.conditional')}
                id="questions-conditional"
                name="conditional"
                data-cy="conditional"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.creationDate')}
                id="questions-creationDate"
                name="creationDate"
                data-cy="creationDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.editDate')}
                id="questions-editDate"
                name="editDate"
                data-cy="editDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.questions.actions')}
                id="questions-actions"
                name="actions"
                data-cy="actions"
                type="text"
              />
              <ValidatedField
                id="questions-forms"
                name="formsId"
                data-cy="forms"
                label={translate('studiesJHipsterApp.questions.forms')}
                type="select"
              >
                <option value="" key="0" />
                {forms
                  ? forms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/questions" replace color="info">
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

export default QuestionsUpdate;
