import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPortfolioPosition, defaultValue } from 'app/shared/model/portfolio-position.model';

export const ACTION_TYPES = {
  FETCH_PORTFOLIOPOSITION_LIST: 'portfolioPosition/FETCH_PORTFOLIOPOSITION_LIST',
  FETCH_PORTFOLIOPOSITION: 'portfolioPosition/FETCH_PORTFOLIOPOSITION',
  CREATE_PORTFOLIOPOSITION: 'portfolioPosition/CREATE_PORTFOLIOPOSITION',
  UPDATE_PORTFOLIOPOSITION: 'portfolioPosition/UPDATE_PORTFOLIOPOSITION',
  PARTIAL_UPDATE_PORTFOLIOPOSITION: 'portfolioPosition/PARTIAL_UPDATE_PORTFOLIOPOSITION',
  DELETE_PORTFOLIOPOSITION: 'portfolioPosition/DELETE_PORTFOLIOPOSITION',
  RESET: 'portfolioPosition/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPortfolioPosition>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type PortfolioPositionState = Readonly<typeof initialState>;

// Reducer

export default (state: PortfolioPositionState = initialState, action): PortfolioPositionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PORTFOLIOPOSITION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PORTFOLIOPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_PORTFOLIOPOSITION):
    case REQUEST(ACTION_TYPES.UPDATE_PORTFOLIOPOSITION):
    case REQUEST(ACTION_TYPES.DELETE_PORTFOLIOPOSITION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIOPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_PORTFOLIOPOSITION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PORTFOLIOPOSITION):
    case FAILURE(ACTION_TYPES.CREATE_PORTFOLIOPOSITION):
    case FAILURE(ACTION_TYPES.UPDATE_PORTFOLIOPOSITION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIOPOSITION):
    case FAILURE(ACTION_TYPES.DELETE_PORTFOLIOPOSITION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_PORTFOLIOPOSITION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_PORTFOLIOPOSITION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_PORTFOLIOPOSITION):
    case SUCCESS(ACTION_TYPES.UPDATE_PORTFOLIOPOSITION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIOPOSITION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_PORTFOLIOPOSITION):
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

const apiUrl = 'api/portfolio-positions';

// Actions

export const getEntities: ICrudGetAllAction<IPortfolioPosition> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PORTFOLIOPOSITION_LIST,
    payload: axios.get<IPortfolioPosition>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IPortfolioPosition> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PORTFOLIOPOSITION,
    payload: axios.get<IPortfolioPosition>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IPortfolioPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PORTFOLIOPOSITION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPortfolioPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PORTFOLIOPOSITION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IPortfolioPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_PORTFOLIOPOSITION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPortfolioPosition> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PORTFOLIOPOSITION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
