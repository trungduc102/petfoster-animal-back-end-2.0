package com.poly.petfoster.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.petfoster.entity.social.Comments;
import com.poly.petfoster.entity.social.LikedComments;
import com.poly.petfoster.entity.social.Likes;
import com.poly.petfoster.entity.social.Posts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "Users")
public class User {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "user_id")
	private String id;

	private String username;
	@Nationalized
	private String fullname;

	private Date birthday;

	private Boolean gender;

	private String phone;

	// private String address;

	private String avatar;

	private String email;

	@JsonIgnore
	private String password;

	// private String role;

	@CreationTimestamp
	private Date createAt;

	@JsonIgnore
	private Boolean isActive;

	@JsonIgnore
	private Boolean isEmailVerified;

	@JsonIgnore
	private String token;

	@JsonIgnore
	@CreationTimestamp
	private Date tokenCreateAt;

	@JsonIgnore
	@Column(name = "uuid")
	private String uuid;

	@Column(name = "provider")
	private String provider;

	@Nationalized
	@Column(name = "display_name")
	private String displayName;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Adopt> adopts;

	// @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	// @JsonIgnore
	// private List<ShippingInfo> shippingInfos;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Favorite> favorites;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Addresses> addresses;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Orders> orders;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Authorities> authorities;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Review> reviews;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<SearchHistory> searchHistories;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<RecentView> recentViews;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Posts> posts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Likes> likes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Comments> comments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<LikedComments> likedComments;

}
