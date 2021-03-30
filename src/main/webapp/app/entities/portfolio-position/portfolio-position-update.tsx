import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPortfolio } from 'app/shared/model/portfolio.model';
import { getEntities as getPortfolios } from 'app/entities/portfolio/portfolio.reducer';
import { IAsset } from 'app/shared/model/asset.model';
import { getEntities as getAssets } from 'app/entities/asset/asset.reducer';
import { getEntity, updateEntity, createEntity, reset } from './portfolio-position.reducer';
import { IPortfolioPosition } from 'app/shared/model/portfolio-position.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPortfolioPositionUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PortfolioPositionUpdate = (props: IPortfolioPositionUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { portfolioPositionEntity, portfolios, assets, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/portfolio-position' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPortfolios();
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
        ...portfolioPositionEntity,
        ...values,
        portfolio: portfolios.find(it => it.id.toString() === values.portfolioId.toString()),
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
          <h2 id="pennybuilderApp.portfolioPosition.home.createOrEditLabel" data-cy="PortfolioPositionCreateUpdateHeading">
            Create or edit a PortfolioPosition
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : portfolioPositionEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="portfolio-position-id">ID</Label>
                  <AvInput id="portfolio-position-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="quantityLabel" for="portfolio-position-quantity">
                  Quantity
                </Label>
                <AvField
                  id="portfolio-position-quantity"
                  data-cy="quantity"
                  type="string"
                  className="form-control"
                  name="quantity"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="isOpenLabel">
                  <AvInput id="portfolio-position-isOpen" data-cy="isOpen" type="checkbox" className="form-check-input" name="isOpen" />
                  Is Open
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="portfolio-position-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="portfolio-position-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.portfolioPositionEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="portfolio-position-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="portfolio-position-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.portfolioPositionEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="portfolio-position-portfolio">Portfolio</Label>
                <AvInput id="portfolio-position-portfolio" data-cy="portfolio" type="select" className="form-control" name="portfolioId">
                  <option value="" key="0" />
                  {portfolios
                    ? portfolios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="portfolio-position-asset">Asset</Label>
                <AvInput id="portfolio-position-asset" data-cy="asset" type="select" className="form-control" name="assetId">
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
              <Button tag={Link} id="cancel-save" to="/portfolio-position" replace color="info">
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
  portfolios: storeState.portfolio.entities,
  assets: storeState.asset.entities,
  portfolioPositionEntity: storeState.portfolioPosition.entity,
  loading: storeState.portfolioPosition.loading,
  updating: storeState.portfolioPosition.updating,
  updateSuccess: storeState.portfolioPosition.updateSuccess,
});

const mapDispatchToProps = {
  getPortfolios,
  getAssets,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PortfolioPositionUpdate);
