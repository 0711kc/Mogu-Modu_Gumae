package com.bunsaned3thinking.mogu.user.controller.dto.request;

import com.bunsaned3thinking.mogu.common.util.LocationUtil;
import com.bunsaned3thinking.mogu.user.entity.Role;
import com.bunsaned3thinking.mogu.user.entity.User;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserRequest {

	@NotBlank(message = "아이디를 입력해주세요.")
	@Size(max = 12)
	private String userId;

	@NotBlank(message = "비밀번호를 입력해주세요.")
	@Size(max = 16)
	private String password;

	@NotBlank(message = "이름을 입력해주세요.")
	@Size(max = 12)
	private String name;

	@NotBlank(message = "별명을 입력해주세요.")
	@Size(max = 12)
	private String nickname;

	@NotBlank(message = "전화번호를 입력해주세요.")
	@Size(max = 11)
	private String phone;

	@NotBlank(message = "이메일을 입력해주세요.")
	@Size(max = 30)
	private String email;

	@NotNull(message = "권한을 입력해주세요.")
	private Role role;

	@NotNull(message = "경도를 입력해주세요.")
	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	private Double longitude;

	@NotNull(message = "위도를 입력해주세요.")
	@DecimalMin("-90.0")
	@DecimalMax("90.0")
	private Double latitude;

	public User toEntity() {
		return User.builder()
			.userId(userId)
			.password(password)
			.name(name)
			.nickname(nickname)
			.phone(phone)
			.email(email)
			.role(role)
			.location(LocationUtil.getPoint(longitude, latitude))
			.build();
	}
}
