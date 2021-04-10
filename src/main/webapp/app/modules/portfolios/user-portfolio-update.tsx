import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Col, Label, Row} from 'reactstrap';
import {AvField, AvForm, AvGroup, AvInput} from 'availity-reactstrap-validation';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';

import {createEntity, getEntity, reset, updateEntity} from 'app/entities/portfolio/portfolio.reducer';
import {convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime} from 'app/shared/util/date-utils';
import {FiatCurrency, fiatCurrencyAsOptions2} from "app/shared/model/enumerations/fiat-currency.model";
import Select from "react-select";
import {IUser} from "app/shared/model/user.model";

export interface IUserPortfolioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const UserPortfolioUpdate = (props: IUserPortfolioUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);
  const [baseCurrency, setBaseCurrency] = useState<FiatCurrency>(FiatCurrency.USD)

  const {portfolioEntity, users, loading, updating} = props;

  const handleClose = () => {
    props.history.push('/portfolios' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (!isNew && portfolioEntity.baseCurrency)
      setBaseCurrency(portfolioEntity.baseCurrency)
  }, [portfolioEntity.baseCurrency])

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);
    values.isActive = true;
    values.baseCurrency = baseCurrency;

    if (errors.length === 0) {
      const entity = {
        ...portfolioEntity,
        ...values,
        owner: props.account,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  const onBaseCurrencyChange = value => setBaseCurrency(FiatCurrency[value.value]);

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="pennybuilderApp.portfolio.home.createOrEditLabel" data-cy="PortfolioCreateUpdateHeading">
            {isNew ? "Create a new Portfolio" : "Edit Portfolio"}
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : portfolioEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup hidden>
                  <Label for="portfolio-id">ID</Label>
                  <AvInput id="portfolio-id" type="text" className="form-control" name="id" required readOnly/>
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="portfolioNameLabel" for="portfolio-portfolioName">
                  Portfolio Name
                </Label>
                <AvField
                  id="portfolio-portfolioName"
                  data-cy="portfolioName"
                  type="text"
                  name="portfolioName"
                  validate={{
                    required: {value: true, errorMessage: 'This field is required.'},
                  }}
                />
              </AvGroup>

              <AvGroup>
                <Label id="baseCurrencyLabel" for="portfolio-baseCurrency">
                  Base Currency
                </Label>
                <Select
                  id="portfolio-baseCurrency"
                  className="text-dark"
                  name="baseCurrency"
                  options={fiatCurrencyAsOptions2()}
                  defaultValue={[{value: baseCurrency, label: baseCurrency}]}
                  onChange={onBaseCurrencyChange}
                />
              </AvGroup>
              <AvGroup hidden>
                <Label id="createdDateLabel" for="portfolio-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="portfolio-createdDate"
                  data-cy="createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.portfolioEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup hidden>
                <Label id="updatedDateLabel" for="portfolio-updatedDate">
                  Updated Date
                </Label>
                <AvInput
                  id="portfolio-updatedDate"
                  data-cy="updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.portfolioEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup hidden>
                <Label for="portfolio-owner">Owner</Label>
                <AvInput id="portfolio-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
                  <option value={props.account.id} key={props.account.id}>{props.account.login}</option>
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/portfolios" replace color="info">
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
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  account: storeState.authentication.account,
  users: storeState.userManagement.users,
  portfolioEntity: storeState.portfolio.entity,
  loading: storeState.portfolio.loading,
  updating: storeState.portfolio.updating,
  updateSuccess: storeState.portfolio.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserPortfolioUpdate);
