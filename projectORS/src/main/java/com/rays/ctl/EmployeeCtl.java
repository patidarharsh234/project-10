package com.rays.ctl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseCtl;
import com.rays.common.DropdownList;
import com.rays.common.ORSResponse;
import com.rays.common.attachment.AttachmentDTO;
import com.rays.dto.EmployeeDto;
import com.rays.dto.PositionDTO;
import com.rays.dto.RoleDTO;
import com.rays.dto.UserDTO;
import com.rays.form.EmployeeForm;
import com.rays.service.EmployeeServiceInt;

public class EmployeeCtl extends BaseCtl<EmployeeForm, EmployeeDto,EmployeeServiceInt>{
	
//	@GetMapping("/preload")
//	public ORSResponse preload() {
//		System.out.println("Inside UserCtl preload.........");
//		ORSResponse res = new ORSResponse(true);
//		PositionDTO dto = new PositionDTO();
//		dto.setStatus("noteBusy");
//		List<DropdownList> list = roleService.search(dto, userContext);
//		res.addResult("roleList", list);
//		return res;
//	}

	
	
//	/**
//	 * Uploads profile picture of given user id
//	 * 
//	 * @param userId
//	 * @param file
//	 * @param req
//	 * @return
//	 */
//	@PostMapping("/profilePic/{userId}")
//	public ORSResponse uploadPic(@PathVariable Long userId, @RequestParam("file") MultipartFile file,
//			HttpServletRequest req) {
//		ORSResponse res = new ORSResponse();
//		try {
//			UserDTO userDTO = baseService.findById(userId, userContext);
//
//			AttachmentDTO doc = new AttachmentDTO(file);
//
//			doc.setDescription("Profile picture");
//
//			doc.setPath(req.getServletPath());
//			doc.setUserId(userId);
//
//			if (userDTO.getImageId() != null && userDTO.getImageId() > 0) {
//				doc.setId(userDTO.getImageId());
//			}
//			Long imageId = attachmentService.save(doc, userContext);
//			// Update new image id
//			if (userDTO.getImageId() == null || userDTO.getImageId() == 0) {
//				userDTO.setImageId(imageId);
//				baseService.update(userDTO, userContext);
//			}
//
//			res.setSuccess(true);
//			res.addResult("imageId", imageId);
//			res.addMessage("IMAGE  Add=User note contains ImageId  : AND : User me set(ImageID) .... OR|"
//					+ "IMAGE  Updated=User contains ImageId . " + "imageID=" + imageId);
//
//		} catch (Exception e) {
//			res.setSuccess(false);
//			res.addMessage("image UploadPic Exception : " + e.getMessage());
//		}
//		return res;
//	}
//
//	/**
//	 * Downloads profile picture of given user id
//	 * 
//	 * @param userId
//	 * @param response
//	 */
//	@GetMapping("/profilePic/{userId}")
//	public @ResponseBody void downloadPic(@PathVariable Long userId, HttpServletResponse response) {
//
//		UserDTO userDTO = baseService.findById(userId, userContext);
//		try {
//			if (userDTO.getImageId() != null && userDTO.getImageId() > 0) {
//				AttachmentDTO attachmentDTO = attachmentService.findById(userDTO.getImageId(), userContext);
//
//				if (attachmentDTO != null) {
//					response.setContentType(attachmentDTO.getType());
//					OutputStream out = response.getOutputStream();
//					out.write(attachmentDTO.getDoc());
//					out.close();
//				} else {
//					response.getWriter().write("ERROR: File not found");
//				}
//
//			} else {
//				response.getWriter().write("ERROR:User Note Contean Image Id..!!");
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}


}
