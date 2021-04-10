/*
 *     PennyBuilder
 *     Copyright (C) 2021  Mark Tripoli, RamChandraReddy Manda
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import React, {useEffect, useState} from 'react';
import {connect} from 'react-redux';
import {Link, RouteComponentProps} from 'react-router-dom';
import {Button, Row, Col} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {IRootState} from 'app/shared/reducers';
import {addPosition, getEntity, getPositions} from 'app/entities/portfolio/portfolio.reducer';
import PortfolioTable from "app/shared/components/portfolio-table/portfolio-table";
import {PositionQuote} from "app/shared/model/iex/quote";
import {deleteEntities, partialUpdateCB as partialUpdatePosition} from "app/entities/portfolio-position/portfolio-position.reducer";
import SymbolSearch from "app/shared/components/symbol-search";
import {IPortfolioPosition} from "app/shared/model/portfolio-position.model";
import {thousandsSeparator} from "app/shared/util/number-utils";
import bigDecimal from "js-big-decimal";

export interface IUserPortfolioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {
}

export const UserPortfolioDetail: React.FC<IUserPortfolioDetailProps> = (props: IUserPortfolioDetailProps) => {
  const [selectedRows, setSelectedRows] = useState<Array<PositionQuote>>([]);
  const [isSearching, setIsSearching] = useState(false);

  const getUpdatedPositions = () => {
    props.getPositions(props.match.params.id);
  }

  useEffect(() => {
    props.getEntity(props.match.params.id);
    getUpdatedPositions();
  }, []);

  useEffect(() => {
    getUpdatedPositions();
  }, [props.portfolioEntity]);

  const onSelectedRowsChanged = (state) => {
    setSelectedRows(state.selectedRows);
  }

  const onDeleteSymbols = () => {
    const ids = selectedRows.map((value => value.id));
    props.deleteEntities({ids: [...ids]}, getUpdatedPositions);
  }

  const onAddSymbol = (asset: PositionQuote) => {
    setIsSearching(false);
    props.addPosition(portfolioEntity, asset.symbol);
  }

  const onEditSymbol = (position: PositionQuote, positionQuantity: number) => {
    const updatedPosition = {} as IPortfolioPosition;
    updatedPosition.id = position.id;
    updatedPosition.asset = position.asset;
    updatedPosition.createdDate = position.createdDate;
    updatedPosition.isOpen = position.isOpen;
    updatedPosition.quantity = positionQuantity;
    updatedPosition.updatedDate = position.updatedDate;
    updatedPosition.portfolio = portfolioEntity;

    props.partialUpdatePosition(updatedPosition, getUpdatedPositions);
  }

  const getTotalValue = () => {
    let totalPrice = "0";
    props.positions.forEach(position => {
      totalPrice = bigDecimal.add(totalPrice, position.totalValue);
    })
    return bigDecimal.round(totalPrice, 2);
  }

  const {portfolioEntity} = props;
  return (
    <>
      <Row>
        <Col>
          <h2 data-cy="portfolioDetailsHeading">Portfolio - {portfolioEntity.portfolioName}</h2>

          <div className="d-flex justify-content-start">
            {selectedRows.length === 0 && (
              <Button color="success" onClick={() => setIsSearching(!isSearching)}>Add Symbol</Button>
            )}
            <Button color="info" onClick={() => getUpdatedPositions()}>Refresh</Button>
          </div>
          <div className="d-flex justify-content-end">
            Total Value: ${thousandsSeparator(getTotalValue())}
          </div>

          <div className="d-flex justify-content-end">
            {selectedRows.length > 0 && <Button color="danger" onClick={e=>onDeleteSymbols()}>Delete Symbol(s)</Button>}
          </div>
        </Col>
      </Row>
      {isSearching && (
        <Row>
          <Col>
            <div className="d-flex justify-content-start">
              <SymbolSearch
                onSelect={asset => onAddSymbol(asset)}
              />
            </div>
          </Col>
        </Row>
      )}
      <Row>
        <Col>
          <PortfolioTable
            onUpdateRow={onEditSymbol}
            positions={props.positions}
            onSelectedRowsChanged={onSelectedRowsChanged}
          />
        </Col>
      </Row>
      <Row className="pt-4">
        <Col className="text-center">
          <Button tag={Link} to="/portfolios" replace color="info" data-cy="entityDetailsBackButton">
            <FontAwesomeIcon icon="arrow-left"/> <span className="d-none d-md-inline">Back</span>
          </Button>
        </Col>
      </Row>
    </>
  );
};

const mapStateToProps = ({portfolio, asset}: IRootState) => ({
  portfolioEntity: portfolio.entity,
  positions: portfolio.positions,
});

const mapDispatchToProps = {getEntity, getPositions, deleteEntities, addPosition, partialUpdatePosition};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserPortfolioDetail);
