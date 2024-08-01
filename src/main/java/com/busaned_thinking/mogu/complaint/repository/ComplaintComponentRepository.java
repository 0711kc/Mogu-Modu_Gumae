package com.busaned_thinking.mogu.complaint.repository;

import java.util.List;

import com.busaned_thinking.mogu.complaint.entity.Complaint;
import com.busaned_thinking.mogu.complaint.entity.ComplaintImage;

public interface ComplaintComponentRepository {

	void saveAllComplaintImages(List<ComplaintImage> complaintImages);

	Complaint saveComplaint(Complaint complaint);

	Complaint findByIdComplaint(Long id);
}
