import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import userProfile, {
  UserProfileState
} from 'app/entities/user-profile/user-profile.reducer';
// prettier-ignore
import userSetting, {
  UserSettingState
} from 'app/entities/user-setting/user-setting.reducer';
// prettier-ignore
import asset, {
  AssetState
} from 'app/entities/asset/asset.reducer';
// prettier-ignore
import portfolio, {
  PortfolioState
} from 'app/entities/portfolio/portfolio.reducer';
// prettier-ignore
import portfolioPosition, {
  PortfolioPositionState
} from 'app/entities/portfolio-position/portfolio-position.reducer';
// prettier-ignore
import watchlist, {
  WatchlistState
} from 'app/entities/watchlist/watchlist.reducer';
// prettier-ignore
import watchlistPosition, {
  WatchlistPositionState
} from 'app/entities/watchlist-position/watchlist-position.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly userProfile: UserProfileState;
  readonly userSetting: UserSettingState;
  readonly asset: AssetState;
  readonly portfolio: PortfolioState;
  readonly portfolioPosition: PortfolioPositionState;
  readonly watchlist: WatchlistState;
  readonly watchlistPosition: WatchlistPositionState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  userProfile,
  userSetting,
  asset,
  portfolio,
  portfolioPosition,
  watchlist,
  watchlistPosition,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
