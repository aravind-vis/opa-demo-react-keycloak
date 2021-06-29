import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IRole } from 'app/shared/model/role.model';
import { getEntities as getRoles } from 'app/entities/role/role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './project-user-role-map.reducer';
import { IProjectUserRoleMap } from 'app/shared/model/project-user-role-map.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import RoleDetail from '../role/role-detail';

export const ProjectUserRoleMapUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const projects = useAppSelector(state => state.project.entities);
  const roles = useAppSelector(state => state.role.entities);
  const projectUserRoleMapEntity = useAppSelector(state => state.projectUserRoleMap.entity);
  const loading = useAppSelector(state => state.projectUserRoleMap.loading);
  const updating = useAppSelector(state => state.projectUserRoleMap.updating);
  const updateSuccess = useAppSelector(state => state.projectUserRoleMap.updateSuccess);

  const handleClose = () => {
    props.history.push('/project-user-role-map');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProjects({}));
    dispatch(getRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...projectUserRoleMapEntity,
      ...values,
      projectId: projects.find(it => it.id.toString() === values.projectIdId.toString()),
      roleId: roles.find(it => it.id.toString() === values.roleIdId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  var defaultProjectName;
  var defaultRoleName = '';

  const url = window.location.href;
  if (url.indexOf('?') > -1 && RoleDetail.length > 0) {
    const index = parseInt(url.substring(url.indexOf('=') + 1));
    defaultProjectName = projects.length > index ? projects[index].name : '';
    defaultRoleName = roles.length > index ? roles[index].roleName : '';
  }

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...projectUserRoleMapEntity,
          projectIdId: projectUserRoleMapEntity?.projectId?.id,
          roleIdId: projectUserRoleMapEntity?.roleId?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="opademoreactkeycloakApp.projectUserRoleMap.home.createOrEditLabel" data-cy="ProjectUserRoleMapCreateUpdateHeading">
            <Translate contentKey="opademoreactkeycloakApp.projectUserRoleMap.home.createOrEditLabel">
              Create or edit a ProjectUserRoleMap
            </Translate>
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
                  id="project-user-role-map-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('opademoreactkeycloakApp.projectUserRoleMap.user')}
                id="project-user-role-map-user"
                name="user"
                data-cy="user"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="project-user-role-map-projectId"
                name="projectIdId"
                data-cy="projectId"
                label={translate('opademoreactkeycloakApp.projectUserRoleMap.projectId')}
                type="select"
              >
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id} placeholder={defaultProjectName}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="project-user-role-map-roleId"
                name="roleIdId"
                data-cy="roleId"
                label={translate('opademoreactkeycloakApp.projectUserRoleMap.roleId')}
                type="select"
              >
                <option value="" key="0" />
                {roles
                  ? roles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id} defaultValue={defaultRoleName}>
                        {otherEntity.roleName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/project-user-role-map" replace color="info">
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

export default ProjectUserRoleMapUpdate;
