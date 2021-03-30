import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPortfolio, defaultValue } from 'app/shared/model/portfolio.model';

export const ACTION_TYPES = {
  FETCH_PORTFOLIO_LIST: 'portfolio/FETCH_PORTFOLIO_LIST',
  FETCH_PORTFOLIO: 'portfolio/FETCH_PORTFOLIO',
  CREATE_PORTFOLIO: 'portfolio/CREATE_PORTFOLIO',
  UPDATE_PORTFOLIO: 'portfolio/UPDATE_PORTFOLIO',
  PARTIAL_UPDATE_PORTFOLIO: 'portfolio/PARTIAL_UPDATE_PORTFOLIO',
  DELETE_PORTFOLIO: 'portfolio/DELETE_PORTFOLIO',
  RESET: 'portfolio/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPortfolio>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PortfolioState = Readonly<typeof initialState>;

// Reducer

export default (state: PortfolioState = initialState, action): PortfolioState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PORTFOLIO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PORTFOLIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PORTFOLIO):
    case REQUEST(ACTION_TYPES.UPDATE_PORTFOLIO):
    case REQUEST(ACTION_TYPES.DELETE_PORTFOLIO):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PORTFOLIO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PORTFOLIO):
    case FAILURE(ACTION_TYPES.CREATE_PORTFOLIO):
    case FAILURE(ACTION_TYPES.UPDATE_PORTFOLIO):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIO):
    case FAILURE(ACTION_TYPES.DELETE_PORTFOLIO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PORTFOLIO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PORTFOLIO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PORTFOLIO):
    case SUCCESS(ACTION_TYPES.UPDATE_PORTFOLIO):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PORTFOLIO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/portfolios';

// Actions

export const getEntities: ICrudGetAllAction<IPortfolio> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PORTFOLIO_LIST,
    payload: axios.get<IPortfolio>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPortfolio> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PORTFOLIO,
    payload: axios.get<IPortfolio>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPortfolio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PORTFOLIO,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPortfolio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PORTFOLIO,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPortfolio> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIO,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPortfolio> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PORTFOLIO,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
