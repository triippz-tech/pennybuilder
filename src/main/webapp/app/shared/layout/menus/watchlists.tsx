import React from "react";
import {NavItem, NavLink} from "reactstrap";
import {NavLink as Link} from "react-router-dom";

export const WatchlistsMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/watchlists" className="d-flex align-items-center">
      <span>Watchlists</span>
    </NavLink>
  </NavItem>
)
