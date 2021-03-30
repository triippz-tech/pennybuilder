import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWatchlist, defaultValue } from 'app/shared/model/watchlist.model';

export const ACTION_TYPES = {
  FETCH_WATCHLIST_LIST: 'watchlist/FETCH_WATCHLIST_LIST',
  FETCH_WATCHLIST: 'watchlist/FETCH_WATCHLIST',
  CREATE_WATCHLIST: 'watchlist/CREATE_WATCHLIST',
  UPDATE_WATCHLIST: 'watchlist/UPDATE_WATCHLIST',
  PARTIAL_UPDATE_WATCHLIST: 'watchlist/PARTIAL_UPDATE_WATCHLIST',
  DELETE_WATCHLIST: 'watchlist/DELETE_WATCHLIST',
  RESET: 'watchlist/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWatchlist>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type WatchlistState = Readonly<typeof initialState>;

// Reducer

export default (state: WatchlistState = initialState, action): WatchlistState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WATCHLIST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WATCHLIST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_WATCHLIST):
    case REQUEST(ACTION_TYPES.UPDATE_WATCHLIST):
    case REQUEST(ACTION_TYPES.DELETE_WATCHLIST):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_WATCHLIST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_WATCHLIST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WATCHLIST):
    case FAILURE(ACTION_TYPES.CREATE_WATCHLIST):
    case FAILURE(ACTION_TYPES.UPDATE_WATCHLIST):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_WATCHLIST):
    case FAILURE(ACTION_TYPES.DELETE_WATCHLIST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHLIST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_WATCHLIST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_WATCHLIST):
    case SUCCESS(ACTION_TYPES.UPDATE_WATCHLIST):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_WATCHLIST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_WATCHLIST):
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

const apiUrl = 'api/watchlists';

// Actions

export const getEntities: ICrudGetAllAction<IWatchlist> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHLIST_LIST,
    payload: axios.get<IWatchlist>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IWatchlist> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WATCHLIST,
    payload: axios.get<IWatchlist>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IWatchlist> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WATCHLIST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWatchlist> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WATCHLIST,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IWatchlist> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_WATCHLIST,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWatchlist> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WATCHLIST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
