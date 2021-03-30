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
import { getEntity, updateEntity, createEntity, reset } from './watchlist.reducer';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWatchlistUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchlistUpdate = (props: IWatchlistUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { watchlistEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/watchlist' + props.location.search);
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
        ...watchlistEntity,
        ...values,
        owner: users.find(it => it.id.toString() === values.ownerId.toString()),
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
          <h2 id="pennybuilderApp.watchlist.home.createOrEditLabel" data-cy="WatchlistCreateUpdateHeading">
            Create or edit a Watchlist
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : watchlistEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="watchlist-id">ID</Label>
                  <AvInput id="watchlist-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="watchlistNameLabel" for="watchlist-watchlistName">
                  Watchlist Name
                </Label>
                <AvField
                  id="watchlist-watchlistName"
                  data-cy="watchlistName"
                  type="text"
                  name="watchlistName"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isActiveLabel">
                  <AvInput id="watchlist-isActive" data-cy="isActive" type="checkbox" className="form-check-input" name="isActive" />
                  Is Active
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="watchlist-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="watchlist-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchlistEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="watchlist-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="watchlist-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchlistEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="watchlist-owner">Owner</Label>
                <AvInput id="watchlist-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
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
              <Button tag={Link} id="cancel-save" to="/watchlist" replace color="info">
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
  watchlistEntity: storeState.watchlist.entity,
  loading: storeState.watchlist.loading,
  updating: storeState.watchlist.updating,
  updateSuccess: storeState.watchlist.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(WatchlistUpdate);
