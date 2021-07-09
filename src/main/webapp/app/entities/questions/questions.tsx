import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './questions.reducer';
import { IQuestions } from 'app/shared/model/questions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const Questions = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const questionsList = useAppSelector(state => state.questions.entities);
  const loading = useAppSelector(state => state.questions.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="questions-heading" data-cy="QuestionsHeading">
        <Translate contentKey="studiesJHipsterApp.questions.home.title">Questions</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="studiesJHipsterApp.questions.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="studiesJHipsterApp.questions.home.createLabel">Create new Questions</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {questionsList && questionsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.subtitle">Subtitle</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.info">Info</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.fieldType">Field Type</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.mandatory">Mandatory</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.variableName">Variable Name</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.units">Units</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.conditional">Conditional</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.creationDate">Creation Date</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.editDate">Edit Date</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.actions">Actions</Translate>
                </th>
                <th>
                  <Translate contentKey="studiesJHipsterApp.questions.forms">Forms</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {questionsList.map((questions, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${questions.id}`} color="link" size="sm">
                      {questions.id}
                    </Button>
                  </td>
                  <td>{questions.subtitle}</td>
                  <td>{questions.info}</td>
                  <td>
                    <Translate contentKey={`studiesJHipsterApp.FieldType.${questions.fieldType}`} />
                  </td>
                  <td>{questions.mandatory ? 'true' : 'false'}</td>
                  <td>{questions.variableName}</td>
                  <td>{questions.units}</td>
                  <td>{questions.conditional ? 'true' : 'false'}</td>
                  <td>
                    {questions.creationDate ? <TextFormat type="date" value={questions.creationDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{questions.editDate ? <TextFormat type="date" value={questions.editDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{questions.actions}</td>
                  <td>{questions.forms ? <Link to={`forms/${questions.forms.id}`}>{questions.forms.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${questions.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questions.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${questions.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="studiesJHipsterApp.questions.home.notFound">No Questions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Questions;
