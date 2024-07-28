package com.busaned_thinking.mogu.post.entity;

import java.util.ArrayList;

import com.busaned_thinking.mogu.ask.entity.Ask;
import com.busaned_thinking.mogu.location.entity.EmdArea;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany
	private ArrayList<Ask> asks = new ArrayList<>();

	@OneToOne
	// private EmdArea emdArea = new EmdArea();
	private EmdArea emdArea;

	@Size(max = 10)
	@Column(length = 10)
	private String category;

	@OneToMany
	private ArrayList<Heart> hearts = new ArrayList<>();

	@Column()
	private boolean isHidden;

	@OneToOne
	private PostDetail postDetail = new PostDetail();

	@Size(max = 10)
	@Column(length = 10)
	private String purchaseRoute;

	@OneToMany
	private ArrayList<Report> reports = new ArrayList<>();

	@Size(max = 10)
	@Column(length = 10)
	private String state;

	@Size(max = 50)
	@Column(length = 50)
	private String title;

}
