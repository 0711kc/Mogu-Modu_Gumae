package com.bunsaned3thinking.mogu.user.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UpdateUserPasswordRequest {
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*?_]).{8,16}$",
		message = "비밀번호는 8글자 이상 16글자 이하로 입력해주세요. "
			+ "영문자, 숫자, 특수문자가 최소 하나씩 들어가야 됩니다.")
	private String password;
}
