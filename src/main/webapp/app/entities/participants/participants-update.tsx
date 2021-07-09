import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IStudy } from 'app/shared/model/study.model';
import { getEntities as getStudies } from 'app/entities/study/study.reducer';
import { getEntity, updateEntity, createEntity, reset } from './participants.reducer';
import { IParticipants } from 'app/shared/model/participants.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ParticipantsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const studies = useAppSelector(state => state.study.entities);
  const participantsEntity = useAppSelector(state => state.participants.entity);
  const loading = useAppSelector(state => state.participants.loading);
  const updating = useAppSelector(state => state.participants.updating);
  const updateSuccess = useAppSelector(state => state.participants.updateSuccess);

  const handleClose = () => {
    props.history.push('/participants');
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
      ...participantsEntity,
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
          ...participantsEntity,
          invitationStatus: 'ACCEPTED',
          studyId: participantsEntity?.study?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="studiesJHipsterApp.participants.home.createOrEditLabel" data-cy="ParticipantsCreateUpdateHeading">
            <Translate contentKey="studiesJHipsterApp.participants.home.createOrEditLabel">Create or edit a Participants</Translate>
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
                  id="participants-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.email')}
                id="participants-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.associatedForms')}
                id="participants-associatedForms"
                name="associatedForms"
                data-cy="associatedForms"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.formsCompleted')}
                id="participants-formsCompleted"
                name="formsCompleted"
                data-cy="formsCompleted"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.languaje')}
                id="participants-languaje"
                name="languaje"
                data-cy="languaje"
                type="text"
              />
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.invitationStatus')}
                id="participants-invitationStatus"
                name="invitationStatus"
                data-cy="invitationStatus"
                type="select"
              >
                <option value="ACCEPTED">{translate('studiesJHipsterApp.Status.ACCEPTED')}</option>
                <option value="PENDING">{translate('studiesJHipsterApp.Status.PENDING')}</option>
                <option value="REJECTED">{translate('studiesJHipsterApp.Status.REJECTED')}</option>
              </ValidatedField>
              <ValidatedField
                label={translate('studiesJHipsterApp.participants.actions')}
                id="participants-actions"
                name="actions"
                data-cy="actions"
                type="text"
              />
              <ValidatedField
                id="participants-study"
                name="studyId"
                data-cy="study"
                label={translate('studiesJHipsterApp.participants.study')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/participants" replace color="info">
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

export default ParticipantsUpdate;
