import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWatchlistPosition, defaultValue } from 'app/shared/model/watchlist-position.model';

export const ACTION_TYPES = {
  FETCH_WATCHLISTPOSITION_LIST: 'watchlistPosition/FETCH_WATCHLISTPOSITION_LIST',
  FETCH_WATCHLISTPOSITION: 'watchlistPosition/FETCH_WATCHLISTPOSITION',
  CREATE_WATCHLISTPOSITION: 'watchlistPosition/CREATE_WATCHLISTPOSITION',
  UPDATE_WATCHLISTPOSITION: 'watchlistPosition/UPDATE_WATCHLISTPOSITION',
  PARTIAL_UPDATE_WATCHLISTPOSITION: 'watchlistPosition/PARTIAL_UPDATE_WATCHLISTPOSITION',
  DELETE_WATCHLISTPOSITION: 'watchlistPosition/DELETE_WATCHLISTPOSITION',
  RESET: 'watchlistPosition/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWatchlistPosition>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WatchlistPositionState = Readonly<typeof initialState>;

// Reducer

export default (state: WatchlistPositionState = initialState, action): WatchlistPositionState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WATCHLISTPOSITION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WATCHLISTPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WATCHLISTPOSITION):
    case REQUEST(ACTION_TYPES.UPDATE_WATCHLISTPOSITION):
    case REQUEST(ACTION_TYPES.DELETE_WATCHLISTPOSITION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_WATCHLISTPOSITION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WATCHLISTPOSITION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WATCHLISTPOSITION):
    case FAILURE(ACTION_TYPES.CREATE_WATCHLISTPOSITION):
    case FAILURE(ACTION_TYPES.UPDATE_WATCHLISTPOSITION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_WATCHLISTPOSITION):
    case FAILURE(ACTION_TYPES.DELETE_WATCHLISTPOSITION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHLISTPOSITION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHLISTPOSITION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WATCHLISTPOSITION):
    case SUCCESS(ACTION_TYPES.UPDATE_WATCHLISTPOSITION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_WATCHLISTPOSITION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WATCHLISTPOSITION):
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

const apiUrl = 'api/watchlist-positions';

// Actions

export const getEntities: ICrudGetAllAction<IWatchlistPosition> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHLISTPOSITION_LIST,
    payload: axios.get<IWatchlistPosition>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWatchlistPosition> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHLISTPOSITION,
    payload: axios.get<IWatchlistPosition>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWatchlistPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WATCHLISTPOSITION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWatchlistPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WATCHLISTPOSITION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IWatchlistPosition> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_WATCHLISTPOSITION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWatchlistPosition> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WATCHLISTPOSITION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
