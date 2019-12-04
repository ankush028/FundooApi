package com.bridgelabz.fundoonote.labeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgelabz.fundoonote.label.model.Label;
import com.bridgelabz.fundoonote.label.repository.LabelRepository;
import com.bridgelabz.fundoonote.label.services.LabelServicesimpli;
@RunWith(SpringRunner.class)
@SpringBootTest
public class LabelServiceTest {

	@InjectMocks
	LabelServicesimpli labelservice; 
	
	@Mock
	LabelRepository labelRepo;
	
	String email ="akag0284@gmail.com";
	
	@Test
	public void getAllLabel()
	{
		Label lable = new Label();
		lable.setLabeltitle("abcde");
		List<Label> listOfLabel = new ArrayList<>();
		listOfLabel.add(lable);
		Mockito.when(labelRepo.findAll()).thenReturn(listOfLabel);
		List<Label> list = labelservice.getAlllabel();
		assertEquals("abcde",list.get(0).getLabeltitle());
			
	}
	@Test
	public void sortedByTitle() {
		Label label = new Label();
		label.setLabeltitle("abcde");
		List<Label> listofLabel = new ArrayList<>();
		listofLabel.add(label);
		
		Mockito.when(labelRepo.findAll().stream()
				.sorted(Comparator.comparing(Label::getLabeltitle)).collect(Collectors.toList()))
		.thenReturn(listofLabel);
		List<Label> list = labelservice.sortedByTitle();
		assertEquals("abcde",list.get(0).getLabeltitle());		
	}
	
	
}
