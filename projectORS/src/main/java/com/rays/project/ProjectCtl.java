package com.rays.project;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rays.common.BaseCtl;
import com.rays.common.DropdownList;
import com.rays.common.ORSResponse;
import com.rays.common.projectattachment.ProjectAttachmentDTO;
import com.rays.common.projectattachment.ProjectAttachmentServiceInt;
import com.rays.dto.ProjectRoleDTO;
import com.rays.service.ProjectRoleServiceInt;

@RestController
@RequestMapping(value = "Project")
public class ProjectCtl extends BaseCtl<ProjectForm, ProjectDTO, ProjectServiceInt> {

	@Autowired
	public ProjectAttachmentServiceInt projectAttachmentServiceInt;

	@Autowired
	public ProjectRoleServiceInt projectRoleSrvice;

	@GetMapping("/preload")

	public ORSResponse preload() {

		ORSResponse res = new ORSResponse(true);
		
		ProjectRoleDTO dto=new ProjectRoleDTO();
		List<DropdownList> projectRoleList = projectRoleSrvice.search(dto, userContext);
		res.addResult("projectRoleList", projectRoleList);

		return res;
	}

	@GetMapping("/image/{projectId}")
	public void downloadImage(@PathVariable Long projectId, HttpServletResponse response) throws IOException {

		ProjectDTO dto = baseService.findById(projectId, userContext);

		if (dto.getImage() != null && dto.getImage() > 0) {
			ProjectAttachmentDTO attachmentDto = projectAttachmentServiceInt.findById(dto.getImage(),
					userContext);

			response.setContentType(attachmentDto.getType());

			response.getOutputStream().write(attachmentDto.getDoc());

		} else {

			response.getWriter().write("PROJECT IMAGE ID(IMAGE) IS NOTE THERE...!!");
		}

	}

	@PostMapping("/Image/{projectId}")
	public ORSResponse UploadImage(@PathVariable Long projectId, @RequestParam("file") MultipartFile file)
			throws IOException {
		ORSResponse res = new ORSResponse(true);
		ProjectDTO dto = baseService.findById(projectId, userContext);

		ProjectAttachmentDTO projectAttachmentDTO = new ProjectAttachmentDTO(file);
		projectAttachmentDTO.setDescription("projectImage");
		projectAttachmentDTO.setProjectId(projectId);

		// image update ager(projectDTo)me AttachmentId ho to
		if(dto.getImage() !=null && dto.getImage()>0) {
			projectAttachmentDTO.setId(dto.getImage());
			res.addMessage(
					"Image Updated ===" + dto.getImage() + "  on this projectId==" + projectId);
		}

		Long Image = projectAttachmentServiceInt.save(projectAttachmentDTO, userContext);

		

		// imageid set in projectDTO me (AttachmentId) set
		if(dto.getImage()==null || dto.getImage()==0) {
			dto.setImage(Image);
			baseService.update(dto, userContext);
			res.addMessage("Image Add==" + Image + "   on this projectId==" + projectId);
		}


		return res;
	}

}
