import dayjs from 'dayjs';
import { IWatchlist } from 'app/shared/model/watchlist.model';
import { IAsset } from 'app/shared/model/asset.model';

export interface IWatchlistPosition {
  id?: number;
  createdDate?: string | null;
  updatedDate?: string | null;
  watchlist?: IWatchlist | null;
  asset?: IAsset | null;
}

export const defaultValue: Readonly<IWatchlistPosition> = {};
