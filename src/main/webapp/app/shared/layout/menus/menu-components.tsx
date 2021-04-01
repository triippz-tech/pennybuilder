import React from 'react';

import {UncontrolledDropdown, DropdownToggle, DropdownMenu} from 'reactstrap';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import LazyLoad from 'react-lazyload';

export const NavDropdown = props => (
  <UncontrolledDropdown nav inNavbar id={props.id} data-cy={props['data-cy']}>
    <DropdownToggle nav caret className="d-flex align-items-center">
      <FontAwesomeIcon icon={props.icon}/>
      <span>{props.name}</span>
    </DropdownToggle>
    <DropdownMenu right style={props.style}>
      {props.children}
    </DropdownMenu>
  </UncontrolledDropdown>
);

export const NavDropdownAvatar = props => (
  <UncontrolledDropdown nav inNavbar id={props.id} data-cy={props['data-cy']}>
    <DropdownToggle nav caret className="d-flex align-items-center">
      {props.account && props.account.userProfile
        ? (
          <LazyLoad>
            <img className="rounded-circle z-depth-0"
                 style={{height: "35px", padding: 0}}
                 alt={"User Image"}
                 src={props.account.userProfile.profilePicture ? props.account.userProfile.profilePicture : "content/images/trollhead.png"} // use normal <img> attributes as props
            />
          </LazyLoad>
        )
        : (
          <LazyLoad>
            <img className="rounded-circle z-depth-0"
                 style={{height: "35px", padding: 0}}
                 alt={"User Image"}
                 src={"content/images/trollhead.png"}
            />
          </LazyLoad>
        )
      }


    </DropdownToggle>
    <DropdownMenu right style={props.style}>
      {props.children}
    </DropdownMenu>
  </UncontrolledDropdown>
);
