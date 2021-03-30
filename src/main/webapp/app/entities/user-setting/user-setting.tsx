import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-setting.reducer';
import { IUserSetting } from 'app/shared/model/user-setting.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserSettingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserSetting = (props: IUserSettingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { userSettingList, match, loading } = props;
  return (
    <div>
      <h2 id="user-setting-heading" data-cy="UserSettingHeading">
        User Settings
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new User Setting
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userSettingList && userSettingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Receive Email</th>
                <th>Private Profile</th>
                <th>Phone Number</th>
                <th>Created Date</th>
                <th>Updated Date</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userSettingList.map((userSetting, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${userSetting.id}`} color="link" size="sm">
                      {userSetting.id}
                    </Button>
                  </td>
                  <td>{userSetting.id}</td>
                  <td>{userSetting.receiveEmail ? 'true' : 'false'}</td>
                  <td>{userSetting.privateProfile ? 'true' : 'false'}</td>
                  <td>{userSetting.phoneNumber}</td>
                  <td>
                    {userSetting.createdDate ? <TextFormat type="date" value={userSetting.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {userSetting.updatedDate ? <TextFormat type="date" value={userSetting.updatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userSetting.user ? userSetting.user.id : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userSetting.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userSetting.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userSetting.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Settings found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userSetting }: IRootState) => ({
  userSettingList: userSetting.entities,
  loading: userSetting.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserSetting);
