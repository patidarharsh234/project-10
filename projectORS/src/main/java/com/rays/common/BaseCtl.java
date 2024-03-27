package com.rays.common;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.rays.dto.MarksheetDTO;
import com.rays.dto.UserDTO;

/**
 * Base controller class contains get, search, save, delete REST APIs
 * 
 * @author Harsh Patidar
 */
public abstract class BaseCtl<F extends BaseForm, T extends BaseDTO, S extends BaseServiceInt<T>> {

	/**
	 * Form operations
	 */
	protected static final String OP_SAVE = "Save";
	protected static final String OP_NEW = "New";
	protected static final String OP_DELETE = "Delete";
	protected static final String OP_CANCEL = "Cancel";
	protected static final String OP_ERROR = "Error";
	protected static final String OP_NEXT = "Next";
	protected static final String OP_PREVIOUS = "Previous";
	protected static final String OP_LOGOUT = "Logout";
	protected static final String OP_GO = "Go";
	protected static final String OP_GET = "Get";

	@Autowired
	protected S baseService;

	@Value("${page.size}")
	private int pageSize = 0;

	/**
	 * Contains context of logged-in user
	 */
	protected UserContext userContext = null;

	/**
	 * Get user context from session
	 * 
	 * @param session
	 */
//Automaticaly methode ko run krva deta he @modelAttribute
	@ModelAttribute
	public void setUserContext(HttpSession session) {
		System.out.println("inside setUserContext --");
		userContext = (UserContext) session.getAttribute("userContext");
		if (userContext == null) {
			UserDTO dto = new UserDTO();
			dto.setLoginId("root@sunilos.com");
			dto.setFirstName("demo firstName");
			dto.setLastName("demo lastName");
			dto.setOrgId(0L);
			dto.setRoleId(1L);
			dto.setOrgName("root");
			userContext = new UserContext(dto);
		}

	}

	/**
	 * Default get mapping
	 * 
	 * @return
	 */
	@GetMapping
	public ORSResponse get() {
		ORSResponse res = new ORSResponse(true);
		res.addData("I am okay " + this.getClass() + " --" + new Date());
		return res;
	}

	/**
	 * Get entity by primary key ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("get/{id}")
	public ORSResponse get(@PathVariable long id) {
		ORSResponse res = new ORSResponse(true);
		T dto = baseService.findById(id, userContext);
		if (dto != null) {
			res.addData(dto);
		} else {

			res.setSuccess(false);
			res.addMessage("user is note exsist..!!");
			// res.addData(dto); //my way directly.
		}
		return res;
	}

	@PostMapping("deleteMany/{ids}")
	public ORSResponse deleteMany(@PathVariable String[] ids, @RequestParam("pageNo") String pageNo,
			@RequestBody F form) {
		ORSResponse res = new ORSResponse(true);
		try {
			for (String id : ids) {
				baseService.delete(Long.parseLong(id), userContext);

			}
			T dto = (T) form.getDto();

//			for (T id : list) {
//				System.out.println("Records  :: " + id.toString());				
//			}

			res.addMessage("Records Deleted Successfully");

			List<T> list = baseService.search(dto, Integer.parseInt(pageNo), pageSize, userContext);

			if (list.size() > 0 && list != null) {
				List<T> nextList = baseService.search(dto, Integer.parseInt(pageNo + 1), pageSize, userContext);
				res.addData(list);
				res.addResult("nextList", nextList.size());
			}else {
				res.setSuccess(false);
				res.addResult("searchParamMessage", "No RecouerdFound In SearchParam..!!!");
			}

		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
		}
		return res;
	}

	@RequestMapping(value = "/search/{pageNo}", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody F form, @PathVariable int pageNo) {
//mean method use in our project.

		// 0 is first page index
		pageNo = (pageNo < 0) ? 0 : pageNo;

		T dto = (T) form.getDto();

		ORSResponse res = new ORSResponse(true);
		List list = baseService.search(dto, pageNo, pageSize, userContext);

		if (list.size() > 0 && list != null) {
			List nextList = baseService.search(dto, (pageNo + 1), pageSize, userContext);
			res.addData(list);
			res.addResult("nextList", nextList.size());

		} else {
			res.setSuccess(false);
			res.addMessage("No record found...");
		}
		return res;
	}

	@PostMapping("/save")
	public ORSResponse save( @RequestBody @Valid F form, BindingResult bindingResult) {
		ORSResponse res = validate(bindingResult);
		T dto = (T) form.getDto();
		if (!res.isSuccess()) {
			
			return res;
		}

		try {
			
			T loginIdCheck = baseService.findByUniqueKey(dto.getUniqueKey(), dto.getUniqueValue(), userContext);

			// Update Filed
			if (dto.getId() != null && dto.getId() > 0) {

				if (loginIdCheck != null && loginIdCheck.getId() != dto.getId()) {
					res.addMessage(dto.getLabel() + " already Exist");
					res.setSuccess(false);
				} else {
					baseService.update(dto, userContext);
					res.addMessage("Data is Updated..");
				}
			} else {

				// Add Filed
				if (dto.getUniqueKey() != null && !dto.getUniqueKey().equals("")) {
					T existDto = (T) baseService.findByUniqueKey(dto.getUniqueKey(), dto.getUniqueValue(), userContext);

					if (existDto != null) {
						res.addMessage(dto.getLabel() + " already exist");
						res.setSuccess(false);
					} else {
						baseService.add(dto, userContext);
						res.addMessage("Data is saved....");
					}
				}

			}
			
			//or
			/*
			 * if (dto.getId() != null && dto.getId() > 0) {
			 * 
			 * if (loginIdCheck != null && loginIdCheck.getId() != dto.getId()) {
			 * res.addMessage(dto.getLabel() + " already Exist"); res.setSuccess(false);
			 * return res; } else { res.addMessage("Data is Updated.."); } } else {
			 * 
			 * // Add Filed if (loginIdCheck != null) { res.addMessage(dto.getLabel() +
			 * " already exist"); res.setSuccess(false); return res; } else {
			 * res.addMessage("Data is saved...."); } } baseService.save(dto, userContext);
			 */
			
			
			

			res.addData(dto.getId());
		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Search entities by form attributes
	 * 
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/search", method = { RequestMethod.GET, RequestMethod.POST })
	public ORSResponse search(@RequestBody F form) {

		// Calculate next page number
		String operation = form.getOperation();
		int pageNo = form.getPageNo();

		if (OP_NEXT.equals(operation)) {
			pageNo++;
		} else if (OP_PREVIOUS.equals(operation)) {
			pageNo--;
		}

		form.setPageNo(pageNo);
		T dto = (T) form.getDto();
		ORSResponse res = new ORSResponse(true);
		res.addData(baseService.search(dto, pageNo, pageSize, userContext));
		return res;
	}

	/**
	 * Delete entity by primary key ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("delete/{id}")
	public ORSResponse delete(@PathVariable long id) {
		ORSResponse res = new ORSResponse(true);
		try {
			T dto = baseService.delete(id, userContext);
			res.addData(dto);
		} catch (Exception e) {
			res.setSuccess(false);
			res.addMessage(e.getMessage());
		}
		return res;
	}

	/**
	 * Gets input error messages and put into REST response
	 * 
	 * @param bindingResult
	 * @return
	 */
	public ORSResponse validate(BindingResult bindingResult) {
		ORSResponse res = new ORSResponse(true);
		System.out.println("inside the validate method of baseCtl");
		if (bindingResult.hasErrors()) {

			res.setSuccess(false);

			Map<String, String> errors = new HashMap<String, String>();

			List<FieldError> list = bindingResult.getFieldErrors();
			// Lambda expression Java 8 feature
			list.forEach(e -> {
				errors.put(e.getField(), e.getDefaultMessage());
			});
			res.addInputErrors(errors);
		}
		return res;

	}

}
