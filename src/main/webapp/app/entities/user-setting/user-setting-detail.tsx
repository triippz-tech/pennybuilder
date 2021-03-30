import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-setting.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserSettingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserSettingDetail = (props: IUserSettingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userSettingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userSettingDetailsHeading">UserSetting</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userSettingEntity.id}</dd>
          <dt>
            <span id="receiveEmail">Receive Email</span>
          </dt>
          <dd>{userSettingEntity.receiveEmail ? 'true' : 'false'}</dd>
          <dt>
            <span id="privateProfile">Private Profile</span>
          </dt>
          <dd>{userSettingEntity.privateProfile ? 'true' : 'false'}</dd>
          <dt>
            <span id="phoneNumber">Phone Number</span>
          </dt>
          <dd>{userSettingEntity.phoneNumber}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {userSettingEntity.createdDate ? (
              <TextFormat value={userSettingEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {userSettingEntity.updatedDate ? (
              <TextFormat value={userSettingEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>User</dt>
          <dd>{userSettingEntity.user ? userSettingEntity.user.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-setting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-setting/${userSettingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userSetting }: IRootState) => ({
  userSettingEntity: userSetting.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserSettingDetail);
