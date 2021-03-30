import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './portfolio-position.reducer';
import { IPortfolioPosition } from 'app/shared/model/portfolio-position.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IPortfolioPositionProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const PortfolioPosition = (props: IPortfolioPositionProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { portfolioPositionList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="portfolio-position-heading" data-cy="PortfolioPositionHeading">
        Portfolio Positions
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Portfolio Position
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {portfolioPositionList && portfolioPositionList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('quantity')}>
                  Quantity <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('isOpen')}>
                  Is Open <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  Created Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedDate')}>
                  Updated Date <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Portfolio <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Asset <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {portfolioPositionList.map((portfolioPosition, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${portfolioPosition.id}`} color="link" size="sm">
                      {portfolioPosition.id}
                    </Button>
                  </td>
                  <td>{portfolioPosition.id}</td>
                  <td>{portfolioPosition.quantity}</td>
                  <td>{portfolioPosition.isOpen ? 'true' : 'false'}</td>
                  <td>
                    {portfolioPosition.createdDate ? (
                      <TextFormat type="date" value={portfolioPosition.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {portfolioPosition.updatedDate ? (
                      <TextFormat type="date" value={portfolioPosition.updatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {portfolioPosition.portfolio ? (
                      <Link to={`portfolio/${portfolioPosition.portfolio.id}`}>{portfolioPosition.portfolio.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {portfolioPosition.asset ? <Link to={`asset/${portfolioPosition.asset.id}`}>{portfolioPosition.asset.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${portfolioPosition.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${portfolioPosition.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${portfolioPosition.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Portfolio Positions found</div>
        )}
      </div>
      {props.totalItems ? (
        <div className={portfolioPositionList && portfolioPositionList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ portfolioPosition }: IRootState) => ({
  portfolioPositionList: portfolioPosition.entities,
  loading: portfolioPosition.loading,
  totalItems: portfolioPosition.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PortfolioPosition);
