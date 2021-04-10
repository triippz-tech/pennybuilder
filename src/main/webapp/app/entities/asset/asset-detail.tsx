import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './asset.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAssetDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssetDetail = (props: IAssetDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { assetEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="assetDetailsHeading">Asset</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{assetEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{assetEntity.name}</dd>
          <dt>
            <span id="symbol">Symbol</span>
          </dt>
          <dd>{assetEntity.symbol}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>{assetEntity.createdDate ? <TextFormat value={assetEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>{assetEntity.updatedDate ? <TextFormat value={assetEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/admin/entities/asset" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/admin/entities/asset/${assetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ asset }: IRootState) => ({
  assetEntity: asset.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssetDetail);
