package com.triippztech.pennybuilder.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserSetting.
 */
@Entity
@Table(name = "user_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "receive_email", nullable = false)
    private Boolean receiveEmail;

    @NotNull
    @Column(name = "private_profile", nullable = false)
    private Boolean privateProfile;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToOne
    @JsonIgnoreProperties(value = { "userSettings" }, allowSetters = true)
    @JoinColumn(unique = true)
    private User user;

    public UserSetting() {
    }

    public UserSetting(User user) {
        this.receiveEmail = true;
        this.privateProfile = false;
        this.createdDate = ZonedDateTime.now();
        this.updatedDate = ZonedDateTime.now();

        this.user = user;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSetting id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getReceiveEmail() {
        return this.receiveEmail;
    }

    public UserSetting receiveEmail(Boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
        return this;
    }

    public void setReceiveEmail(Boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    public Boolean getPrivateProfile() {
        return this.privateProfile;
    }

    public UserSetting privateProfile(Boolean privateProfile) {
        this.privateProfile = privateProfile;
        return this;
    }

    public void setPrivateProfile(Boolean privateProfile) {
        this.privateProfile = privateProfile;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public UserSetting phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public UserSetting createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public UserSetting updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return this.user;
    }

    public UserSetting user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSetting)) {
            return false;
        }
        return id != null && id.equals(((UserSetting) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSetting{" +
            "id=" + getId() +
            ", receiveEmail='" + getReceiveEmail() + "'" +
            ", privateProfile='" + getPrivateProfile() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
