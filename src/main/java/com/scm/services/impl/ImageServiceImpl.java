package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	private Cloudinary cloudinary;
	

	public ImageServiceImpl(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}


	@Override
	public String uploadImage(MultipartFile contactImage) {
		
		//code likhana hai jo image ko upload kar rha ho
		
		String filename = UUID.randomUUID().toString();
		
		try {
			byte[] data = new byte[contactImage.getInputStream().available()];
			contactImage.getInputStream().read(data);
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
					"public_id",contactImage.getOriginalFilename()
					
					
					));
			
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		
		
		return this.getUrlFromPublicId(filename);
	}


	@Override
	public String getUrlFromPublicId(String publicId) {
		// TODO Auto-generated method stub
		return cloudinary
				.url()
				.transformation(
						new Transformation<>()
						.width(500)
						.height(500)
						.crop("fill"))
				.generate(publicId);
	}

}
