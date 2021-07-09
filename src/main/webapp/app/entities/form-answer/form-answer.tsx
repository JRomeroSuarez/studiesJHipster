import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './form-answer.reducer';
import { IFormAnswer } from 'app/shared/model/form-answer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const FormAnswer = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const formAnswerList = useAppSelector(state => state.formAnswer.entities);
  const loading = useAppSelector(state => state.formAnswer.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="form-answer-heading" data-cy="FormAnswerHeading">
        <Translate contentKey="studiesJHipsterApp.formAnswer.home.title">Form Answers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="studiesJHipsterApp.formAnswer.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="studiesJHipsterApp.formAnswer.home.createLabel">Create new Form Answer</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {formAnswerList && formAnswerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="studiesJHipsterApp.formAnswer.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.formAnswer.creationForm">Creation Form</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.formAnswer.modifiedForm">Modified Form</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.formAnswer.forms">Forms</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {formAnswerList.map((formAnswer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${formAnswer.id}`} color="link" size="sm">
                      {formAnswer.id}
                    </Button>
                  </td>
                  <td>
                    {formAnswer.creationForm ? <TextFormat type="date" value={formAnswer.creationForm} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {formAnswer.modifiedForm ? <TextFormat type="date" value={formAnswer.modifiedForm} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{formAnswer.forms ? <Link to={`forms/${formAnswer.forms.id}`}>{formAnswer.forms.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${formAnswer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formAnswer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${formAnswer.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="studiesJHipsterApp.formAnswer.home.notFound">No Form Answers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default FormAnswer;
