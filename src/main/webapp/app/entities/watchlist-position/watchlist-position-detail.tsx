import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './watchlist-position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWatchlistPositionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchlistPositionDetail = (props: IWatchlistPositionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { watchlistPositionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="watchlistPositionDetailsHeading">WatchlistPosition</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{watchlistPositionEntity.id}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {watchlistPositionEntity.createdDate ? (
              <TextFormat value={watchlistPositionEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {watchlistPositionEntity.updatedDate ? (
              <TextFormat value={watchlistPositionEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Watchlist</dt>
          <dd>{watchlistPositionEntity.watchlist ? watchlistPositionEntity.watchlist.id : ''}</dd>
          <dt>Asset</dt>
          <dd>{watchlistPositionEntity.asset ? watchlistPositionEntity.asset.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/watchlist-position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/watchlist-position/${watchlistPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ watchlistPosition }: IRootState) => ({
  watchlistPositionEntity: watchlistPosition.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchlistPositionDetail);
