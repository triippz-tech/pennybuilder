import React, {useEffect, useState} from 'react';
import {Button, Col, Nav, NavItem, NavLink, Row} from 'reactstrap';
import { connect } from 'react-redux';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset } from './settings.reducer';
import { getEntity as getSettings, updateEntity as updateSettings } from 'app/entities/user-setting/user-setting.reducer';

import SettingsUpdate from "app/modules/account/settings/components/settings-update";
import UserUpdate from "app/modules/account/settings/components/user-update";
import {convertDateTimeToServer} from "app/shared/util/date-utils";

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export const SettingsPage = (props: IUserSettingsProps) => {

  const [selectedTab, setSelectedTab] = useState<number>(1);

  useEffect(() => {
    props.getSession();
    return () => {
      props.reset();
    };
  }, []);

  const handleValidUserSubmit = (event, values) => {
    const account = {
      ...props.account,
      ...values,
    };

    props.saveAccountSettings(account);
    event.persist();
  };

  const handleValidSettingsSubmit = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    if (errors.length === 0) {
      const settings = {
        ...props.userSettingEntity,
        ...values,
      };

      props.updateSettings(settings);
      event.persist();
    }
  };

  const onTabClick = (tab) => {
    if (tab === 1) {
      setSelectedTab(tab);
    } else if (tab ===2) {
      props.getSettings(props.account.userSetting.id);
      setSelectedTab(2);
    }
  }

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="2">
          <Nav vertical>
            <NavItem>
              <NavLink className="text-white" href="#" onClick={() => onTabClick(1)}>Personal Info</NavLink>
            </NavItem>
            <NavItem>
              <NavLink className="text-white" href="#" onClick={() => onTabClick(2)}>Settings</NavLink>
            </NavItem>
          </Nav>
        </Col>
        <Col md="8">
          {selectedTab === 1 && (
            <UserUpdate
              account={props.account}
              handleValidSubmit={handleValidUserSubmit}
            />
          )}
          {selectedTab === 2 && (
            <SettingsUpdate
              handleValidSubmit={handleValidSettingsSubmit}
              userSettings={props.userSettingEntity}
              updating={props.settingsUpdating}
              account={props.account}
            />
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = ({ authentication, userSetting }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
  userSettingEntity: userSetting.entity,
  settingsUpdating: userSetting.updating,
});

const mapDispatchToProps = { getSession, saveAccountSettings, reset, getSettings, updateSettings };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SettingsPage);
