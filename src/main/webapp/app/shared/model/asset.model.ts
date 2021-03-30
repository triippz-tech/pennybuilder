import dayjs from 'dayjs';
import { IPortfolioPosition } from 'app/shared/model/portfolio-position.model';
import { IWatchlistPosition } from 'app/shared/model/watchlist-position.model';

export interface IAsset {
  id?: number;
  name?: string;
  symbol?: string;
  createdDate?: string | null;
  updatedDate?: string | null;
  portfolioPositions?: IPortfolioPosition[] | null;
  watchlistPositions?: IWatchlistPosition[] | null;
}

export const defaultValue: Readonly<IAsset> = {};
