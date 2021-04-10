import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/admin/entities/user-profile">
      User Profile
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/user-setting">
      User Setting
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/asset">
      Asset
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/portfolio">
      Portfolio
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/portfolio-position">
      Portfolio Position
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/watchlist">
      Watchlist
    </MenuItem>
    <MenuItem icon="asterisk" to="/admin/entities/watchlist-position">
      Watchlist Position
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
