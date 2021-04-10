import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './watchlist.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWatchlistDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WatchlistDetail = (props: IWatchlistDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { watchlistEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="watchlistDetailsHeading">Watchlist</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{watchlistEntity.id}</dd>
          <dt>
            <span id="watchlistName">Watchlist Name</span>
          </dt>
          <dd>{watchlistEntity.watchlistName}</dd>
          <dt>
            <span id="isActive">Is Active</span>
          </dt>
          <dd>{watchlistEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {watchlistEntity.createdDate ? <TextFormat value={watchlistEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {watchlistEntity.updatedDate ? <TextFormat value={watchlistEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Owner</dt>
          <dd>{watchlistEntity.owner ? watchlistEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/admin/entities/watchlist" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/entities/watchlist/${watchlistEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ watchlist }: IRootState) => ({
  watchlistEntity: watchlist.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WatchlistDetail);
