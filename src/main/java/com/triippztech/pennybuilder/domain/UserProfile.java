package com.triippztech.pennybuilder.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A UserProfile.
 */
@Entity
@Table(name = "user_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "headline")
    private String headline;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bio")
    private String bio;

    @Column(name = "location")
    private String location;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "trading_view_url")
    private String tradingViewUrl;

    @Column(name = "twitter_url")
    private String twitterUrl;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "bith_date")
    private LocalDate bithDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile id(Long id) {
        this.id = id;
        return this;
    }

    public String getHeadline() {
        return this.headline;
    }

    public UserProfile headline(String headline) {
        this.headline = headline;
        return this;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getBio() {
        return this.bio;
    }

    public UserProfile bio(String bio) {
        this.bio = bio;
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return this.location;
    }

    public UserProfile location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNickname() {
        return this.nickname;
    }

    public UserProfile nickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public UserProfile profilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getTradingViewUrl() {
        return this.tradingViewUrl;
    }

    public UserProfile tradingViewUrl(String tradingViewUrl) {
        this.tradingViewUrl = tradingViewUrl;
        return this;
    }

    public void setTradingViewUrl(String tradingViewUrl) {
        this.tradingViewUrl = tradingViewUrl;
    }

    public String getTwitterUrl() {
        return this.twitterUrl;
    }

    public UserProfile twitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
        return this;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public String getFacebookUrl() {
        return this.facebookUrl;
    }

    public UserProfile facebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
        return this;
    }

    public void setFacebookUrl(String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public LocalDate getBithDate() {
        return this.bithDate;
    }

    public UserProfile bithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
        return this;
    }

    public void setBithDate(LocalDate bithDate) {
        this.bithDate = bithDate;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public UserProfile createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return this.updatedDate;
    }

    public UserProfile updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getUser() {
        return this.user;
    }

    public UserProfile user(User user) {
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
        if (!(o instanceof UserProfile)) {
            return false;
        }
        return id != null && id.equals(((UserProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserProfile{" +
            "id=" + getId() +
            ", headline='" + getHeadline() + "'" +
            ", bio='" + getBio() + "'" +
            ", location='" + getLocation() + "'" +
            ", nickname='" + getNickname() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", tradingViewUrl='" + getTradingViewUrl() + "'" +
            ", twitterUrl='" + getTwitterUrl() + "'" +
            ", facebookUrl='" + getFacebookUrl() + "'" +
            ", bithDate='" + getBithDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
