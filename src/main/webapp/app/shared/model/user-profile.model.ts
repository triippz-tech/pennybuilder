import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IUserProfile {
  id?: number;
  headline?: string | null;
  bio?: string | null;
  location?: string | null;
  nickname?: string | null;
  profilePicture?: string | null;
  tradingViewUrl?: string | null;
  twitterUrl?: string | null;
  facebookUrl?: string | null;
  bithDate?: string | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserProfile> = {};
