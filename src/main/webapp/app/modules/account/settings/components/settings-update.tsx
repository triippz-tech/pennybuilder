import React from "react";
import {Button, Col, Input, Label} from "reactstrap";
import {AvForm, AvField, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {IUser} from "app/shared/model/user.model";
import {convertDateTimeFromServer, displayDefaultDateTime} from "app/shared/util/date-utils";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {IUserSetting} from "app/shared/model/user-setting.model";

interface ISettingsUpdateProps {
  userSettings: IUserSetting;
  handleValidSubmit: (event, errors, values) => void;
  updating: boolean;
  account: IUser;
}

export const SettingsUpdate: React.FC<ISettingsUpdateProps> = (props) => {
  return (
    <>
      <h2 id="settings-title">Settings</h2>
      <AvForm model={props.userSettings} onSubmit={props.handleValidSubmit}>
        <AvGroup hidden>
          <Label for="user-setting-id">ID</Label>
          <AvInput id="user-setting-id" type="text" className="form-control" name="id" required readOnly />
        </AvGroup>
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
          <AvField
            id="user-setting-phoneNumber"
            data-cy="phoneNumber"
            type="tel"
            name="phoneNumber"
            mask="(999) 999-9999"
            maskChar="-"
            helpMessage="Valid NANP phone number cannot start with a 1"
            validate={{
              tel: true,
            }}
          />
        </AvGroup>
        <AvGroup hidden>
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
            value={convertDateTimeFromServer(props.userSettings.createdDate)}
            readOnly
          />
        </AvGroup>
        <AvGroup hidden>
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
            value={convertDateTimeFromServer(props.userSettings.updatedDate)}
            readOnly
          />
        </AvGroup>
        <AvGroup hidden>
          <Label for="user-setting-user">User</Label>
          <AvInput id="user-setting-user" data-cy="user" type="select" className="form-control" name="userId" readOnly>
            <option value={props.account.id} key={props.account.id}>
              {props.account.login}
            </option>
          </AvInput>
        </AvGroup>
        <Button tag={Link} id="cancel-save" to="/user-setting" replace color="info">
          <FontAwesomeIcon icon="arrow-left"/>
          &nbsp;
          <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={props.updating}>
          <FontAwesomeIcon icon="save"/>
          &nbsp; Save
        </Button>
      </AvForm>
    </>
  );
}

export default SettingsUpdate;
