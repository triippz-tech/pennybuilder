import React from "react";
import {Button, Card, CardBody, CardImg, CardText, CardTitle, Col} from "reactstrap";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


export const UserCard = ({ username, headline, twitter, facebook, tradingView, profilePicture=undefined }) => (
  <Card>
    <CardImg
      hover
      overlay='white-light'
      className='card-img-top'
      src={profilePicture ? profilePicture : "content/images/trollhead.png"}
      alt='man'/>
    <CardBody cascade className='text-center'>
      <CardTitle className='card-title'>
        <strong>{username}</strong>
      </CardTitle>

      <CardText>
        {headline}
      </CardText>

      <Col md='12' className='d-flex justify-content-center'>

        {facebook && (
          <a href={facebook} target='_blank' className="btn-circle facebook-color" color="primary" rel="noreferrer">
            <FontAwesomeIcon icon={['fab', 'facebook-f']} size="lg"/>
          </a>
        )}

        {twitter && (
          <a href={twitter} target='_blank' className="btn-circle twitter-color" color="primary" rel="noreferrer">
            <FontAwesomeIcon icon={['fab', 'twitter']} size="lg" color="white"/>
          </a>
        )}

        {tradingView && (
          <a href={tradingView} target='_blank' className="btn-circle tradingview-color" color="primary" rel="noreferrer">
            <FontAwesomeIcon icon={"chart-line"} size="lg"/>
          </a>
        )}

      </Col>
    </CardBody>
  </Card>
);

export default UserCard;
