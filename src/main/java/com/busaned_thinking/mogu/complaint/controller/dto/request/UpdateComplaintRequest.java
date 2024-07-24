package com.busaned_thinking.mogu.complaint.controller.dto.request;

import com.busaned_thinking.mogu.complaint.entity.Complaint;

import jakarta.validation.constraints.Size;
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
public class UpdateComplaintRequest {

	@Size(max = 500)
	private String answer;
	
	private Short type;

	public static UpdateComplaintRequest from(Complaint complaint) {
		return UpdateComplaintRequest.builder().answer(complaint.getAnswer()).type(complaint.getType()).build();
	}
}
