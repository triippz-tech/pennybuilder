import dayjs from 'dayjs';
import { IPortfolio } from 'app/shared/model/portfolio.model';
import { IAsset } from 'app/shared/model/asset.model';

export interface IPortfolioPosition {
  id?: number;
  quantity?: number;
  isOpen?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  portfolio?: IPortfolio | null;
  asset?: IAsset | null;
}

export const defaultValue: Readonly<IPortfolioPosition> = {
  isOpen: false,
};
