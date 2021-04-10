import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-profile.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserProfileDetail = (props: IUserProfileDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userProfileEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userProfileDetailsHeading">UserProfile</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userProfileEntity.id}</dd>
          <dt>
            <span id="headline">Headline</span>
          </dt>
          <dd>{userProfileEntity.headline}</dd>
          <dt>
            <span id="bio">Bio</span>
          </dt>
          <dd>{userProfileEntity.bio}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{userProfileEntity.location}</dd>
          <dt>
            <span id="nickname">Nickname</span>
          </dt>
          <dd>{userProfileEntity.nickname}</dd>
          <dt>
            <span id="profilePicture">Profile Picture</span>
          </dt>
          <dd>{userProfileEntity.profilePicture}</dd>
          <dt>
            <span id="tradingViewUrl">Trading View Url</span>
          </dt>
          <dd>{userProfileEntity.tradingViewUrl}</dd>
          <dt>
            <span id="twitterUrl">Twitter Url</span>
          </dt>
          <dd>{userProfileEntity.twitterUrl}</dd>
          <dt>
            <span id="facebookUrl">Facebook Url</span>
          </dt>
          <dd>{userProfileEntity.facebookUrl}</dd>
          <dt>
            <span id="bithDate">Bith Date</span>
          </dt>
          <dd>
            {userProfileEntity.bithDate ? (
              <TextFormat value={userProfileEntity.bithDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {userProfileEntity.createdDate ? (
              <TextFormat value={userProfileEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {userProfileEntity.updatedDate ? (
              <TextFormat value={userProfileEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>User</dt>
          <dd>{userProfileEntity.user ? userProfileEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admin/entities/user-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/entities/user-profile/${userProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userProfile }: IRootState) => ({
  userProfileEntity: userProfile.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserProfileDetail);
