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
import { getEntity, updateEntity, createEntity, reset } from './portfolio.reducer';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPortfolioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PortfolioUpdate = (props: IPortfolioUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { portfolioEntity, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('admin/entities/portfolio' + props.location.search);
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
        ...portfolioEntity,
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
          <h2 id="pennybuilderApp.portfolio.home.createOrEditLabel" data-cy="PortfolioCreateUpdateHeading">
            Create or edit a Portfolio
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
                <AvGroup>
                  <Label for="portfolio-id">ID</Label>
                  <AvInput id="portfolio-id" type="text" className="form-control" name="id" required readOnly />
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
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="baseCurrencyLabel" for="portfolio-baseCurrency">
                  Base Currency
                </Label>
                <AvInput
                  id="portfolio-baseCurrency"
                  data-cy="baseCurrency"
                  type="select"
                  className="form-control"
                  name="baseCurrency"
                  value={(!isNew && portfolioEntity.baseCurrency) || 'AED'}
                >
                  <option value="AED">AED</option>
                  <option value="AFN">AFN</option>
                  <option value="ALL">ALL</option>
                  <option value="AMD">AMD</option>
                  <option value="ANG">ANG</option>
                  <option value="AOA">AOA</option>
                  <option value="ARS">ARS</option>
                  <option value="AUD">AUD</option>
                  <option value="AWG">AWG</option>
                  <option value="AZN">AZN</option>
                  <option value="BAM">BAM</option>
                  <option value="BBD">BBD</option>
                  <option value="BDT">BDT</option>
                  <option value="BGN">BGN</option>
                  <option value="BHD">BHD</option>
                  <option value="BIF">BIF</option>
                  <option value="BMD">BMD</option>
                  <option value="BND">BND</option>
                  <option value="BOB">BOB</option>
                  <option value="BRL">BRL</option>
                  <option value="BSD">BSD</option>
                  <option value="BTC">BTC</option>
                  <option value="BTN">BTN</option>
                  <option value="BWP">BWP</option>
                  <option value="BYN">BYN</option>
                  <option value="BZD">BZD</option>
                  <option value="CAD">CAD</option>
                  <option value="CDF">CDF</option>
                  <option value="CHF">CHF</option>
                  <option value="CLF">CLF</option>
                  <option value="CLP">CLP</option>
                  <option value="CNH">CNH</option>
                  <option value="CNY">CNY</option>
                  <option value="COP">COP</option>
                  <option value="CRC">CRC</option>
                  <option value="CUC">CUC</option>
                  <option value="CUP">CUP</option>
                  <option value="CVE">CVE</option>
                  <option value="CZK">CZK</option>
                  <option value="DJF">DJF</option>
                  <option value="DKK">DKK</option>
                  <option value="DOP">DOP</option>
                  <option value="DZD">DZD</option>
                  <option value="EGP">EGP</option>
                  <option value="ERN">ERN</option>
                  <option value="ETB">ETB</option>
                  <option value="EUR">EUR</option>
                  <option value="FJD">FJD</option>
                  <option value="FKP">FKP</option>
                  <option value="GBP">GBP</option>
                  <option value="GEL">GEL</option>
                  <option value="GGP">GGP</option>
                  <option value="GHS">GHS</option>
                  <option value="GIP">GIP</option>
                  <option value="GMD">GMD</option>
                  <option value="GNF">GNF</option>
                  <option value="GTQ">GTQ</option>
                  <option value="GYD">GYD</option>
                  <option value="HKD">HKD</option>
                  <option value="HNL">HNL</option>
                  <option value="HRK">HRK</option>
                  <option value="HTG">HTG</option>
                  <option value="HUF">HUF</option>
                  <option value="IDR">IDR</option>
                  <option value="ILS">ILS</option>
                  <option value="IMP">IMP</option>
                  <option value="INR">INR</option>
                  <option value="IQD">IQD</option>
                  <option value="IRR">IRR</option>
                  <option value="ISK">ISK</option>
                  <option value="JEP">JEP</option>
                  <option value="JMD">JMD</option>
                  <option value="JOD">JOD</option>
                  <option value="JPY">JPY</option>
                  <option value="KES">KES</option>
                  <option value="KGS">KGS</option>
                  <option value="KHR">KHR</option>
                  <option value="KMF">KMF</option>
                  <option value="KPW">KPW</option>
                  <option value="KRW">KRW</option>
                  <option value="KWD">KWD</option>
                  <option value="KYD">KYD</option>
                  <option value="KZT">KZT</option>
                  <option value="LAK">LAK</option>
                  <option value="LBP">LBP</option>
                  <option value="LKR">LKR</option>
                  <option value="LRD">LRD</option>
                  <option value="LSL">LSL</option>
                  <option value="LYD">LYD</option>
                  <option value="MAD">MAD</option>
                  <option value="MDL">MDL</option>
                  <option value="MGA">MGA</option>
                  <option value="MKD">MKD</option>
                  <option value="MMK">MMK</option>
                  <option value="MNT">MNT</option>
                  <option value="MOP">MOP</option>
                  <option value="MRO">MRO</option>
                  <option value="MRU">MRU</option>
                  <option value="MUR">MUR</option>
                  <option value="MVR">MVR</option>
                  <option value="MWK">MWK</option>
                  <option value="MXN">MXN</option>
                  <option value="MYR">MYR</option>
                  <option value="MZN">MZN</option>
                  <option value="NAD">NAD</option>
                  <option value="NGN">NGN</option>
                  <option value="NIO">NIO</option>
                  <option value="NOK">NOK</option>
                  <option value="NPR">NPR</option>
                  <option value="NZD">NZD</option>
                  <option value="OMR">OMR</option>
                  <option value="PAB">PAB</option>
                  <option value="PEN">PEN</option>
                  <option value="PGK">PGK</option>
                  <option value="PHP">PHP</option>
                  <option value="PKR">PKR</option>
                  <option value="PLN">PLN</option>
                  <option value="PYG">PYG</option>
                  <option value="QAR">QAR</option>
                  <option value="RON">RON</option>
                  <option value="RSD">RSD</option>
                  <option value="RUB">RUB</option>
                  <option value="RWF">RWF</option>
                  <option value="SAR">SAR</option>
                  <option value="SBD">SBD</option>
                  <option value="SCR">SCR</option>
                  <option value="SDG">SDG</option>
                  <option value="SEK">SEK</option>
                  <option value="SGD">SGD</option>
                  <option value="SHP">SHP</option>
                  <option value="SLL">SLL</option>
                  <option value="SOS">SOS</option>
                  <option value="SRD">SRD</option>
                  <option value="SSP">SSP</option>
                  <option value="STD">STD</option>
                  <option value="STN">STN</option>
                  <option value="SVC">SVC</option>
                  <option value="SYP">SYP</option>
                  <option value="SZL">SZL</option>
                  <option value="THB">THB</option>
                  <option value="TJS">TJS</option>
                  <option value="TMT">TMT</option>
                  <option value="TND">TND</option>
                  <option value="TOP">TOP</option>
                  <option value="TRY">TRY</option>
                  <option value="TTD">TTD</option>
                  <option value="TWD">TWD</option>
                  <option value="TZS">TZS</option>
                  <option value="UAH">UAH</option>
                  <option value="UGX">UGX</option>
                  <option value="USD">USD</option>
                  <option value="UYU">UYU</option>
                  <option value="UZS">UZS</option>
                  <option value="VEF">VEF</option>
                  <option value="VES">VES</option>
                  <option value="VND">VND</option>
                  <option value="VUV">VUV</option>
                  <option value="WST">WST</option>
                  <option value="XAF">XAF</option>
                  <option value="XAG">XAG</option>
                  <option value="XAU">XAU</option>
                  <option value="XCD">XCD</option>
                  <option value="XDR">XDR</option>
                  <option value="XOF">XOF</option>
                  <option value="XPD">XPD</option>
                  <option value="XPF">XPF</option>
                  <option value="XPT">XPT</option>
                  <option value="YER">YER</option>
                  <option value="ZAR">ZAR</option>
                  <option value="ZMW">ZMW</option>
                  <option value="ZWL">ZWL</option>
                </AvInput>
              </AvGroup>
              <AvGroup check>
                <Label id="isActiveLabel">
                  <AvInput id="portfolio-isActive" data-cy="isActive" type="checkbox" className="form-check-input" name="isActive" />
                  Is Active
                </Label>
              </AvGroup>
              <AvGroup>
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
              <AvGroup>
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
              <AvGroup>
                <Label for="portfolio-owner">Owner</Label>
                <AvInput id="portfolio-owner" data-cy="owner" type="select" className="form-control" name="ownerId">
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
              <Button tag={Link} id="cancel-save" to="/portfolio" replace color="info">
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
  portfolioEntity: storeState.portfolio.entity,
  loading: storeState.portfolio.loading,
  updating: storeState.portfolio.updating,
  updateSuccess: storeState.portfolio.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(PortfolioUpdate);
