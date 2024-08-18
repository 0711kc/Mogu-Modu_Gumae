package com.bunsaned3thinking.mogu.complaint.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;
import com.bunsaned3thinking.mogu.user.entity.User;

public interface ComplaintComponentRepository {

	List<ComplaintImage> saveAllComplaintImages(List<ComplaintImage> complaintImages);

	Complaint saveComplaint(Complaint complaint);

	Optional<Complaint> findComplaintById(Long id);

	Optional<User> findUserByUserId(String userId);

	List<Complaint> findAllComplaint();

	List<Complaint> findAllComplaintByUserId(String userId);
}
