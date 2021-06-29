import React, { useEffect } from 'react';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RoleDeleteDialog = (props: RouteComponentProps<{ id: string }>) => {
  const roleEntity = useAppSelector(state => state.role.entity);
  const updateSuccess = useAppSelector(state => state.role.updateSuccess);

  const handleClose = () => {
    props.history.push('/role');
  };

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="roleDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="opademoreactkeycloakApp.role.delete.question">
        <Translate contentKey="opademoreactkeycloakApp.role.delete.question" interpolate={{ id: roleEntity.id }}>
          Are you sure you want to delete this Role?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default RoleDeleteDialog;
