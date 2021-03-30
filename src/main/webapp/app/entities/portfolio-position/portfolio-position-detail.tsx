import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './portfolio-position.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPortfolioPositionDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PortfolioPositionDetail = (props: IPortfolioPositionDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { portfolioPositionEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="portfolioPositionDetailsHeading">PortfolioPosition</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{portfolioPositionEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{portfolioPositionEntity.quantity}</dd>
          <dt>
            <span id="isOpen">Is Open</span>
          </dt>
          <dd>{portfolioPositionEntity.isOpen ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {portfolioPositionEntity.createdDate ? (
              <TextFormat value={portfolioPositionEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {portfolioPositionEntity.updatedDate ? (
              <TextFormat value={portfolioPositionEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>Portfolio</dt>
          <dd>{portfolioPositionEntity.portfolio ? portfolioPositionEntity.portfolio.id : ''}</dd>
          <dt>Asset</dt>
          <dd>{portfolioPositionEntity.asset ? portfolioPositionEntity.asset.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/portfolio-position" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/portfolio-position/${portfolioPositionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ portfolioPosition }: IRootState) => ({
  portfolioPositionEntity: portfolioPosition.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PortfolioPositionDetail);
