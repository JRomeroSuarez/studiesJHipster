import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IForms } from 'app/shared/model/forms.model';
import { getEntities as getForms } from 'app/entities/forms/forms.reducer';
import { getEntity, updateEntity, createEntity, reset } from './form-answer.reducer';
import { IFormAnswer } from 'app/shared/model/form-answer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FormAnswerUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const forms = useAppSelector(state => state.forms.entities);
  const formAnswerEntity = useAppSelector(state => state.formAnswer.entity);
  const loading = useAppSelector(state => state.formAnswer.loading);
  const updating = useAppSelector(state => state.formAnswer.updating);
  const updateSuccess = useAppSelector(state => state.formAnswer.updateSuccess);

  const handleClose = () => {
    props.history.push('/form-answer');
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
    values.creationForm = convertDateTimeToServer(values.creationForm);
    values.modifiedForm = convertDateTimeToServer(values.modifiedForm);

    const entity = {
      ...formAnswerEntity,
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
          creationForm: displayDefaultDateTime(),
          modifiedForm: displayDefaultDateTime(),
        }
      : {
          ...formAnswerEntity,
          creationForm: convertDateTimeFromServer(formAnswerEntity.creationForm),
          modifiedForm: convertDateTimeFromServer(formAnswerEntity.modifiedForm),
          formsId: formAnswerEntity?.forms?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="studiesJHipsterApp.formAnswer.home.createOrEditLabel" data-cy="FormAnswerCreateUpdateHeading">
            <Translate contentKey="studiesJHipsterApp.formAnswer.home.createOrEditLabel">Create or edit a FormAnswer</Translate>
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
                  id="form-answer-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('studiesJHipsterApp.formAnswer.creationForm')}
                id="form-answer-creationForm"
                name="creationForm"
                data-cy="creationForm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.formAnswer.modifiedForm')}
                id="form-answer-modifiedForm"
                name="modifiedForm"
                data-cy="modifiedForm"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="form-answer-forms"
                name="formsId"
                data-cy="forms"
                label={translate('studiesJHipsterApp.formAnswer.forms')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/form-answer" replace color="info">
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

export default FormAnswerUpdate;
