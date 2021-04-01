import React from "react";
import {Button, Col} from "reactstrap";
import { AvForm, AvField } from 'availity-reactstrap-validation';
import {IUser} from "app/shared/model/user.model";

interface IUserUpdateProps {
  account: IUser;
  handleValidSubmit: (event, values) => void;
}

export const UserUpdate: React.FC<IUserUpdateProps> = (props) => {
  return (
    <>
      <h2 id="user-title">Personal Info</h2>
      <AvForm id="user-form" onValidSubmit={props.handleValidSubmit}>
        {/* First name */}
        <AvField
          className="form-control"
          name="firstName"
          label="First Name"
          id="firstName"
          placeholder="Your first name"
          validate={{
            required: { value: true, errorMessage: 'Your first name is required.' },
            minLength: { value: 1, errorMessage: 'Your first name is required to be at least 1 character' },
            maxLength: { value: 50, errorMessage: 'Your first name cannot be longer than 50 characters' },
          }}
          value={props.account.firstName}
          data-cy="firstname"
        />
        {/* Last name */}
        <AvField
          className="form-control"
          name="lastName"
          label="Last Name"
          id="lastName"
          placeholder="Your last name"
          validate={{
            required: { value: true, errorMessage: 'Your last name is required.' },
            minLength: { value: 1, errorMessage: 'Your last name is required to be at least 1 character' },
            maxLength: { value: 50, errorMessage: 'Your last name cannot be longer than 50 characters' },
          }}
          value={props.account.lastName}
          data-cy="lastname"
        />
        {/* Email */}
        <AvField
          name="email"
          label="Email"
          placeholder={'Your email'}
          type="email"
          validate={{
            required: { value: true, errorMessage: 'Your email is required.' },
            minLength: { value: 5, errorMessage: 'Your email is required to be at least 5 characters.' },
            maxLength: { value: 254, errorMessage: 'Your email cannot be longer than 50 characters.' },
          }}
          value={props.account.email}
          data-cy="email"
        />
        <Button color="primary" type="submit" data-cy="submit">
          Save
        </Button>
      </AvForm>
    </>
  );
}

export default UserUpdate;
