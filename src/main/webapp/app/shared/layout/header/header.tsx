import './header.scss';

import React, {useState} from 'react';

import {Navbar, Nav, NavbarToggler, Collapse} from 'reactstrap';

import LoadingBar from 'react-redux-loading-bar';

import {Home, Brand, News} from './header-components';
import {AdminMenu, EntitiesMenu, AccountMenu} from '../menus';
import {IUser} from "app/shared/model/user.model";
import {PortfoliosMenu} from "app/shared/layout/menus/portfolios";
import {WatchlistsMenu} from "app/shared/layout/menus/watchlists";

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  account: IUser;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">Development</a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar"/>
      <Navbar data-cy="navbar" dark expand="sm" fixed="top" className="bg-primary">
        <NavbarToggler aria-label="Menu" onClick={toggleMenu}/>
        <Brand/>
        <Collapse isOpen={menuOpen} navbar>
          <Nav id="header-tabs" className="ml-auto" navbar>
            <Home/>
            {props.isAuthenticated && (
              <>
                <WatchlistsMenu/>
                <PortfoliosMenu/>
                <News />
              </>
            )}
            {props.isAuthenticated && props.isAdmin && <EntitiesMenu/>}
            {props.isAuthenticated && props.isAdmin && (
              <AdminMenu showOpenAPI={props.isOpenAPIEnabled} showDatabase={!props.isInProduction}/>
            )}
            <AccountMenu isAuthenticated={props.isAuthenticated} account={props.account}/>
          </Nav>
        </Collapse>
      </Navbar>
    </div>
  );
};

export default Header;
