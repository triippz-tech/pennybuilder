import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserProfileProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserProfile = (props: IUserProfileProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { userProfileList, match, loading } = props;
  return (
    <div>
      <h2 id="user-profile-heading" data-cy="UserProfileHeading">
        User Profiles
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new User Profile
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {userProfileList && userProfileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Headline</th>
                <th>Bio</th>
                <th>Location</th>
                <th>Nickname</th>
                <th>Profile Picture</th>
                <th>Trading View Url</th>
                <th>Twitter Url</th>
                <th>Facebook Url</th>
                <th>Bith Date</th>
                <th>Created Date</th>
                <th>Updated Date</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userProfileList.map((userProfile, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${userProfile.id}`} color="link" size="sm">
                      {userProfile.id}
                    </Button>
                  </td>
                  <td>{userProfile.id}</td>
                  <td>{userProfile.headline}</td>
                  <td>{userProfile.bio}</td>
                  <td>{userProfile.location}</td>
                  <td>{userProfile.nickname}</td>
                  <td>{userProfile.profilePicture}</td>
                  <td>{userProfile.tradingViewUrl}</td>
                  <td>{userProfile.twitterUrl}</td>
                  <td>{userProfile.facebookUrl}</td>
                  <td>
                    {userProfile.bithDate ? <TextFormat type="date" value={userProfile.bithDate} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {userProfile.createdDate ? <TextFormat type="date" value={userProfile.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {userProfile.updatedDate ? <TextFormat type="date" value={userProfile.updatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{userProfile.user ? userProfile.user.id : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userProfile.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userProfile.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userProfile.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No User Profiles found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userProfile }: IRootState) => ({
  userProfileList: userProfile.entities,
  loading: userProfile.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserProfile);
