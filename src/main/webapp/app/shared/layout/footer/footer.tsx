import './footer.scss';

import React from 'react';

import {Col, Container, NavLink, Row} from 'reactstrap';
import { Link } from 'react-router-dom';

const Footer = props => (
  <div className="footer copyright page-content">
    <Container>
      <div className="row">
        <div className="col-md-6">
          <span>Copyright © 2021, All Right Reserved PennyBuilder</span>
        </div>
        <div className="col-md-6">
          <div className="copyright-menu">
            <ul>
              <li>
                <Link to="/">Home</Link>
              </li>
              <li>
                <Link to="/terms">Terms</Link>
              </li>
              <li>
                <Link to="/privacy-policy">Privacy Policy</Link>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </Container>
  </div>
);

export default Footer;
