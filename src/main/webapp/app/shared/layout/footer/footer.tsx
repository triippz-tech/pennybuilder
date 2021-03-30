import './footer.scss';

import React from 'react';

import {Col, Row} from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <div className="container-fluid">
      <Row>
        <Col md="6" lg="5" className="text-center mb-4 mb-md-0">
          <h6 className="mb-0 white-text text-center">
            &copy; {new Date().getFullYear()} Copyright:{" "}
            <a href="https://pennybuilder.io">
              PennyBuilder
            </a>
          </h6>
        </Col>
      </Row>
    </div>
  </div>
);

export default Footer;
