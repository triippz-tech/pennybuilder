import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './portfolio.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPortfolioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PortfolioDetail = (props: IPortfolioDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { portfolioEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="portfolioDetailsHeading">Portfolio</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{portfolioEntity.id}</dd>
          <dt>
            <span id="portfolioName">Portfolio Name</span>
          </dt>
          <dd>{portfolioEntity.portfolioName}</dd>
          <dt>
            <span id="baseCurrency">Base Currency</span>
          </dt>
          <dd>{portfolioEntity.baseCurrency}</dd>
          <dt>
            <span id="isActive">Is Active</span>
          </dt>
          <dd>{portfolioEntity.isActive ? 'true' : 'false'}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {portfolioEntity.createdDate ? <TextFormat value={portfolioEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">Updated Date</span>
          </dt>
          <dd>
            {portfolioEntity.updatedDate ? <TextFormat value={portfolioEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>Owner</dt>
          <dd>{portfolioEntity.owner ? portfolioEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/portfolio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/portfolio/${portfolioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ portfolio }: IRootState) => ({
  portfolioEntity: portfolio.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PortfolioDetail);
