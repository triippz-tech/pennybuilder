import React from "react";
import {NavItem, NavLink} from "reactstrap";
import {NavLink as Link} from "react-router-dom";

export const PortfoliosMenu = props => (
  <NavItem>
    <NavLink tag={Link} to="/portfolios" className="d-flex align-items-center">
      <span>Portfolios</span>
    </NavLink>
  </NavItem>
)
