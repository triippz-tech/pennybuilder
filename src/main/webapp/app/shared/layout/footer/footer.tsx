import './footer.scss';

import React from 'react';

import {Col, Container, Row} from 'reactstrap';

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
                <a href="#">Home</a>
              </li>
              <li>
                <a href="#">Terms</a>
              </li>
              <li>
                <a href="#">Privacy Policy</a>
              </li>
              <li>
                <a href="#">Contact</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </Container>
  </div>
);

export default Footer;
