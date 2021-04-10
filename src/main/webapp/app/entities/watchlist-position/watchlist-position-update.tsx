import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWatchlist } from 'app/shared/model/watchlist.model';
import { getEntities as getWatchlists } from 'app/entities/watchlist/watchlist.reducer';
import { IAsset } from 'app/shared/model/asset.model';
import { getEntities as getAssets } from 'app/entities/asset/asset.reducer';
import { getEntity, updateEntity, createEntity, reset } from './watchlist-position.reducer';
import { IWatchlistPosition } from 'app/shared/model/watchlist-position.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWatchlistPositionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchlistPositionUpdate = (props: IWatchlistPositionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { watchlistPositionEntity, watchlists, assets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/admin/entities/watchlist-position' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getWatchlists();
    props.getAssets();
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
        ...watchlistPositionEntity,
        ...values,
        watchlist: watchlists.find(it => it.id.toString() === values.watchlistId.toString()),
        asset: assets.find(it => it.id.toString() === values.assetId.toString()),
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
          <h2 id="pennybuilderApp.watchlistPosition.home.createOrEditLabel" data-cy="WatchlistPositionCreateUpdateHeading">
            Create or edit a WatchlistPosition
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : watchlistPositionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="watchlist-position-id">ID</Label>
                  <AvInput id="watchlist-position-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="createdDateLabel" for="watchlist-position-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="watchlist-position-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchlistPositionEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="watchlist-position-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="watchlist-position-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.watchlistPositionEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="watchlist-position-watchlist">Watchlist</Label>
                <AvInput id="watchlist-position-watchlist" data-cy="watchlist" type="select" className="form-control" name="watchlistId">
                  <option value="" key="0" />
                  {watchlists
                    ? watchlists.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="watchlist-position-asset">Asset</Label>
                <AvInput id="watchlist-position-asset" data-cy="asset" type="select" className="form-control" name="assetId">
                  <option value="" key="0" />
                  {assets
                    ? assets.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/admin/entities/watchlist-position" replace color="info">
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
  watchlists: storeState.watchlist.entities,
  assets: storeState.asset.entities,
  watchlistPositionEntity: storeState.watchlistPosition.entity,
  loading: storeState.watchlistPosition.loading,
  updating: storeState.watchlistPosition.updating,
  updateSuccess: storeState.watchlistPosition.updateSuccess,
});

const mapDispatchToProps = {
  getWatchlists,
  getAssets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchlistPositionUpdate);
