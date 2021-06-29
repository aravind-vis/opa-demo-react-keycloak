import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './project-user-role-map.reducer';
import { IProjectUserRoleMap } from 'app/shared/model/project-user-role-map.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProjectUserRoleMap = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const projectUserRoleMapList = useAppSelector(state => state.projectUserRoleMap.entities);
  const loading = useAppSelector(state => state.projectUserRoleMap.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="project-user-role-map-heading" data-cy="ProjectUserRoleMapHeading">
        <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.home.title">Project User Role Maps</Translate>
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.home.createLabel">Create new Project User Role Map</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectUserRoleMapList && projectUserRoleMapList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.user">User</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.projectId">Project Id</Translate>
                </th>
                <th>
                  <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.roleId">Role Id</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectUserRoleMapList.map((projectUserRoleMap, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${projectUserRoleMap.id}`} color="link" size="sm">
                      {projectUserRoleMap.id}
                    </Button>
                  </td>
                  <td>{projectUserRoleMap.user}</td>
                  <td>
                    {projectUserRoleMap.projectId ? (
                      <Link to={`project/${projectUserRoleMap.projectId.id}`}>{projectUserRoleMap.projectId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectUserRoleMap.roleId ? (
                      <Link to={`role/${projectUserRoleMap.roleId.id}`}>{projectUserRoleMap.roleId.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${projectUserRoleMap.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${projectUserRoleMap.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${projectUserRoleMap.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
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
              <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.home.notFound">No Project User Role Maps found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default ProjectUserRoleMap;
