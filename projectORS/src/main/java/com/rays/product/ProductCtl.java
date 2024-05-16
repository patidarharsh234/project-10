package com.rays.product;

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
import com.rays.dto.ProjectRoleDTO;
import com.rays.productattachment.ProductAttachmentDTO;
import com.rays.productattachment.ProductAttachmentServiceInt;
import com.rays.productpreload.PaymentModePreloadDTO;
import com.rays.productpreload.PaymentModePreloadServiceInt;
import com.rays.project.ProjectDTO;


@RestController
@RequestMapping(value = "/product") //cahnge
public class ProductCtl extends BaseCtl<ProductForm, ProductDTO, ProductServiceInt> {
	
@Autowired
protected PaymentModePreloadServiceInt paymentModePreloadInt; //chenges

@Autowired
protected ProductAttachmentServiceInt productAttachmentServiceInt; //chenges
	
	
	@GetMapping("/preload")

	public ORSResponse preload() {

		ORSResponse res = new ORSResponse(true);
		
		PaymentModePreloadDTO dto=new PaymentModePreloadDTO(); //chenges
		List<DropdownList> payment = paymentModePreloadInt.search(dto, userContext);//changes
		res.addResult("paymentModePreload", payment);//changes

		return res;
	}

	@GetMapping("/Image/{Id}")
	public void downloadImage(@PathVariable Long Id, HttpServletResponse response) throws IOException {

		ProductDTO dto = baseService.findById(Id, userContext);//changes

		if (dto.getImageId() != null && dto.getImageId() > 0) {
			
			
			ProductAttachmentDTO attachmentDto = productAttachmentServiceInt.findById(dto.getImageId(),userContext);  //changes
					

			response.setContentType(attachmentDto.getType());

			response.getOutputStream().write(attachmentDto.getDoc());

		} else {

			response.getWriter().write("PROJECT IMAGE ID(IMAGE) IS NOTE THERE...!!");
		}

	}

	@PostMapping("/Image/{Id}")
	public ORSResponse UploadImage(@PathVariable Long Id, @RequestParam("file") MultipartFile file)
			throws IOException {
		ORSResponse res = new ORSResponse(true);
		
		
		ProductDTO dto = baseService.findById(Id, userContext);// chenges

		ProductAttachmentDTO AttachmentDTO = new ProductAttachmentDTO(file); //change
		
		AttachmentDTO.setDescription("Images");
		AttachmentDTO.setProductId(Id); //change

		// image update ager(projectDTo)me AttachmentId ho to
		if(dto.getImageId() !=null && dto.getImageId()>0) {     //change
			AttachmentDTO.setId(dto.getImageId());
			
			res.addMessage("Image Updated ===" + dto.getImageId() + "  on this Id==" + Id);
					
		}

		Long Image = productAttachmentServiceInt.save(AttachmentDTO, userContext);

		

		// imageid set in projectDTO me (AttachmentId) set
		if(dto.getImageId()==null || dto.getImageId()==0) {
			dto.setImageId(Image);                              //changes
			baseService.update(dto, userContext);               //update
			res.addMessage("Image Add==" + Image + "   on this Id==" + Id);
		}


		return res;
	}

}
