package com.bunsaned3thinking.mogu.complaint.repository.component;

import java.util.List;
import java.util.Optional;

import com.bunsaned3thinking.mogu.complaint.entity.Complaint;
import com.bunsaned3thinking.mogu.complaint.entity.ComplaintImage;

public interface ComplaintComponentRepository {

	void saveAllComplaintImages(List<ComplaintImage> complaintImages);

	Complaint saveComplaint(Complaint complaint);

	Optional<Complaint> findByIdComplaint(Long id);
}
