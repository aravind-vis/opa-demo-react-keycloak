import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './role.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const roleEntity = useAppSelector(state => state.role.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="roleDetailsHeading">
          <Translate contentKey="opademoreactkeycloakApp.role.detail.title">Role</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="opademoreactkeycloakApp.role.id">Id</Translate>
            </span>
          </dt>
          <dd>{roleEntity.id}</dd>
          <dt>
            <span id="roleName">
              <Translate contentKey="opademoreactkeycloakApp.role.roleName">Role Name</Translate>
            </span>
          </dt>
          <dd>{roleEntity.roleName}</dd>
          <dt>
            <span id="roleDescription">
              <Translate contentKey="opademoreactkeycloakApp.role.roleDescription">Role Description</Translate>
            </span>
          </dt>
          <dd>{roleEntity.roleDescription}</dd>
        </dl>
        <Button tag={Link} to="/role" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/role/${roleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RoleDetail;
