import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-setting.reducer';
import { IUserSetting } from 'app/shared/model/user-setting.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserSettingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserSettingUpdate = (props: IUserSettingUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { userSettingEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin/entities/user-setting');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    if (errors.length === 0) {
      const entity = {
        ...userSettingEntity,
        ...values,
        user: users.find(it => it.id.toString() === values.userId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pennybuilderApp.userSetting.home.createOrEditLabel" data-cy="UserSettingCreateUpdateHeading">
            Create or edit a UserSetting
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userSettingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-setting-id">ID</Label>
                  <AvInput id="user-setting-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup check>
                <Label id="receiveEmailLabel">
                  <AvInput
                    id="user-setting-receiveEmail"
                    data-cy="receiveEmail"
                    type="checkbox"
                    className="form-check-input"
                    name="receiveEmail"
                  />
                  Receive Email
                </Label>
              </AvGroup>
              <AvGroup check>
                <Label id="privateProfileLabel">
                  <AvInput
                    id="user-setting-privateProfile"
                    data-cy="privateProfile"
                    type="checkbox"
                    className="form-check-input"
                    name="privateProfile"
                  />
                  Private Profile
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="phoneNumberLabel" for="user-setting-phoneNumber">
                  Phone Number
                </Label>
                <AvField id="user-setting-phoneNumber" data-cy="phoneNumber" type="text" name="phoneNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="user-setting-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="user-setting-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userSettingEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="user-setting-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="user-setting-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.userSettingEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="user-setting-user">User</Label>
                <AvInput id="user-setting-user" data-cy="user" type="select" className="form-control" name="userId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/admin/entities/user-setting" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  userSettingEntity: storeState.userSetting.entity,
  loading: storeState.userSetting.loading,
  updating: storeState.userSetting.updating,
  updateSuccess: storeState.userSetting.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserSettingUpdate);
