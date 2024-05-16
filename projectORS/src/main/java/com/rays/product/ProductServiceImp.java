package com.rays.product;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.rays.common.BaseServiceImpl;
import com.rays.project.ProjectDAOInt;
import com.rays.project.ProjectDTO;


@Service
@Transactional
public class ProductServiceImp extends BaseServiceImpl<ProductDTO, ProductDAOint>  implements ProductServiceInt{

}
