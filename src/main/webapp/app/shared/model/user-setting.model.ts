import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IUserSetting {
  id?: number;
  receiveEmail?: boolean;
  privateProfile?: boolean;
  phoneNumber?: string | null;
  createdDate?: string | null;
  updatedDate?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IUserSetting> = {
  receiveEmail: false,
  privateProfile: false,
};
