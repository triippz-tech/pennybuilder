import React, {useState, useEffect} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col, Label, Card, CardImg, CardBody, CardTitle} from 'reactstrap';
import {AvFeedback, AvForm, AvGroup, AvInput, AvField} from 'availity-reactstrap-validation';
import {setFileData, byteSize, translate} from 'react-jhipster';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';

import {IUser} from 'app/shared/model/user.model';
import {getUsers} from 'app/modules/administration/user-management/user-management.reducer';
import {getEntity, updateEntity, createEntity, setBlob, reset} from 'app/entities/user-profile/user-profile.reducer';
import {IUserProfile} from 'app/shared/model/user-profile.model';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {mapIdList} from 'app/shared/util/entity-utils';
import UserCard from "app/shared/components/user-card";

export interface IProfileUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const Profile = (props: IProfileUpdateProps) => {

  const {userProfileEntity, loading, updating} = props;

  const {bio} = userProfileEntity;

  useEffect(() => {
    props.getEntity(props.account.userProfile.id);
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    if (errors.length === 0) {
      const entity = {
        ...userProfileEntity,
        ...values,
        user: props.account,
      };

      props.updateEntity(entity);
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pennybuilderApp.userProfile.home.createOrEditLabel" data-cy="UserProfileCreateUpdateHeading">
            Edit Profile
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        {loading ? (
          <p>Loading...</p>
        ) : (
          <><Col md="8">
            <AvForm model={userProfileEntity} onSubmit={saveEntity}>
              <AvGroup hidden>
                <Label for="user-profile-id">ID</Label>
                <AvInput id="user-profile-id" type="text" className="form-control" name="id" required readOnly/>
              </AvGroup>
              <AvGroup>
                <Label id="headlineLabel" for="user-profile-headline">
                  Headline
                </Label>
                <AvField id="user-profile-headline" data-cy="headline" type="text" name="headline"/>
              </AvGroup>
              <AvGroup>
                <Label id="bioLabel" for="user-profile-bio">
                  Bio
                </Label>
                <AvInput id="user-profile-bio" data-cy="bio" type="textarea" name="bio"/>
              </AvGroup>
              <AvGroup>
                <Label id="locationLabel" for="user-profile-location">
                  Location
                </Label>
                <AvField id="user-profile-location" data-cy="location" type="text" name="location"/>
              </AvGroup>
              <AvGroup>
                <Label id="nicknameLabel" for="user-profile-nickname">
                  Nickname
                </Label>
                <AvField id="user-profile-nickname" data-cy="nickname" type="text" name="nickname"/>
              </AvGroup>
              <AvGroup>
                <Label id="profilePictureLabel" for="user-profile-profilePicture">
                  Profile Picture
                </Label>
                <AvField id="user-profile-profilePicture" data-cy="profilePicture" type="text" name="profilePicture"/>
              </AvGroup>
              <AvGroup>
                <Label id="tradingViewUrlLabel" for="user-profile-tradingViewUrl">
                  Trading View Url
                </Label>
                <AvField id="user-profile-tradingViewUrl" data-cy="tradingViewUrl" type="text" name="tradingViewUrl"/>
              </AvGroup>
              <AvGroup>
                <Label id="twitterUrlLabel" for="user-profile-twitterUrl">
                  Twitter Url
                </Label>
                <AvField id="user-profile-twitterUrl" data-cy="twitterUrl" type="text" name="twitterUrl"/>
              </AvGroup>
              <AvGroup>
                <Label id="facebookUrlLabel" for="user-profile-facebookUrl">
                  Facebook Url
                </Label>
                <AvField id="user-profile-facebookUrl" data-cy="facebookUrl" type="text" name="facebookUrl"/>
              </AvGroup>
              <AvGroup>
                <Label id="bithDateLabel" for="user-profile-bithDate">
                  Bith Date
                </Label>
                <AvField id="user-profile-bithDate" data-cy="bithDate" type="date" className="form-control"
                         name="bithDate"/>
              </AvGroup>
              <AvGroup hidden>
                <Label id="createdDateLabel" for="user-profile-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="user-profile-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  readOnly
                  value={convertDateTimeFromServer(props.userProfileEntity.createdDate)}/>
              </AvGroup>
              <AvGroup hidden>
                <Label id="updatedDateLabel" for="user-profile-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="user-profile-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={convertDateTimeFromServer(props.userProfileEntity.updatedDate)}
                  readOnly/>
              </AvGroup>
              <AvGroup hidden>
                <Label for="user-profile-user">User</Label>
                <AvInput id="user-profile-user" data-cy="user" type="select" className="form-control" name="userId"
                         readOnly>
                  <option value={props.account.id} key={props.account.id}>
                    {props.account.login}
                  </option>

                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-profile" replace color="info">
                <FontAwesomeIcon icon="arrow-left"/>
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit"
                      disabled={updating}>
                <FontAwesomeIcon icon="save"/>
                &nbsp; Save
              </Button>
            </AvForm>
          </Col>
            <Col md="4">
              <UserCard
                username={props.account.login}
                profilePicture={props.userProfileEntity.profilePicture}
                headline={props.userProfileEntity.headline}
                twitter={props.userProfileEntity.twitterUrl}
                facebook={props.userProfileEntity.facebookUrl}
                tradingView={props.userProfileEntity.tradingViewUrl}
              />
            </Col>
          </>
        )}
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  account: storeState.authentication.account,
  userProfileEntity: storeState.userProfile.entity,
  loading: storeState.userProfile.loading,
  updating: storeState.userProfile.updating,
  updateSuccess: storeState.userProfile.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Profile);
