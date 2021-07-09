import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './forms.reducer';
import { IForms } from 'app/shared/model/forms.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FormsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const studies = useAppSelector(state => state.study.entities);
  const formsEntity = useAppSelector(state => state.forms.entity);
  const loading = useAppSelector(state => state.forms.loading);
  const updating = useAppSelector(state => state.forms.updating);
  const updateSuccess = useAppSelector(state => state.forms.updateSuccess);

  const handleClose = () => {
    props.history.push('/forms');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getStudies({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...formsEntity,
      ...values,
      study: studies.find(it => it.id.toString() === values.studyId.toString()),
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
          ...formsEntity,
          studyId: formsEntity?.study?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="studiesJHipsterApp.forms.home.createOrEditLabel" data-cy="FormsCreateUpdateHeading">
            <Translate contentKey="studiesJHipsterApp.forms.home.createOrEditLabel">Create or edit a Forms</Translate>
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
                  id="forms-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('studiesJHipsterApp.forms.title')}
                id="forms-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.forms.description')}
                id="forms-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.forms.numResponses')}
                id="forms-numResponses"
                name="numResponses"
                data-cy="numResponses"
                type="text"
              />
              <ValidatedField
                id="forms-study"
                name="studyId"
                data-cy="study"
                label={translate('studiesJHipsterApp.forms.study')}
                type="select"
              >
                <option value="" key="0" />
                {studies
                  ? studies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/forms" replace color="info">
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

export default FormsUpdate;
