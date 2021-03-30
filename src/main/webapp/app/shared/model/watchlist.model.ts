import dayjs from 'dayjs';
import { IWatchlistPosition } from 'app/shared/model/watchlist-position.model';
import { IUser } from 'app/shared/model/user.model';

export interface IWatchlist {
  id?: number;
  watchlistName?: string;
  isActive?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  positions?: IWatchlistPosition[] | null;
  owner?: IUser | null;
}

export const defaultValue: Readonly<IWatchlist> = {
  isActive: false,
};
