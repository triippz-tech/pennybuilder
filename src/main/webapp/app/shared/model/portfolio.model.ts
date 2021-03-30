import dayjs from 'dayjs';
import { IPortfolioPosition } from 'app/shared/model/portfolio-position.model';
import { IUser } from 'app/shared/model/user.model';
import { FiatCurrency } from 'app/shared/model/enumerations/fiat-currency.model';

export interface IPortfolio {
  id?: number;
  portfolioName?: string;
  baseCurrency?: FiatCurrency;
  isActive?: boolean;
  createdDate?: string | null;
  updatedDate?: string | null;
  positions?: IPortfolioPosition[] | null;
  owner?: IUser | null;
}

export const defaultValue: Readonly<IPortfolio> = {
  isActive: false,
};
