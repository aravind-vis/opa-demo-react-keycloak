import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './project-user-role-map.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProjectUserRoleMapDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const projectUserRoleMapEntity = useAppSelector(state => state.projectUserRoleMap.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projectUserRoleMapDetailsHeading">
          <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.detail.title">ProjectUserRoleMap</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projectUserRoleMapEntity.id}</dd>
          <dt>
            <span id="user">
              <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.user">User</Translate>
            </span>
          </dt>
          <dd>{projectUserRoleMapEntity.user}</dd>
          <dt>
            <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.projectId">Project Id</Translate>
          </dt>
          <dd>{projectUserRoleMapEntity.projectId ? projectUserRoleMapEntity.projectId.id : ''}</dd>
          <dt>
            <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.roleId">Role Id</Translate>
          </dt>
          <dd>{projectUserRoleMapEntity.roleId ? projectUserRoleMapEntity.roleId.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/project-user-role-map" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/project-user-role-map/${projectUserRoleMapEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjectUserRoleMapDetail;
