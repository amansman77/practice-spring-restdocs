package com.ho.practice.restdocs.controller.open;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	private RestDocumentationResultHandler document;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	private static final String serviceUrl ="/file";

	@Before
	public void setUp() {
		this.document = document(
                "{class-name}/{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        );
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation))
				.alwaysDo(document)
				.build();
	}
	
	@Test
    public void testSaveFile() throws Exception {
        // given
        MockMultipartFile file =
            new MockMultipartFile(
                    "myFile",
                    "sample.jpg",
                    MediaType.MULTIPART_FORM_DATA_VALUE,
                    "image-data".getBytes(StandardCharsets.UTF_8));

        // when
        ResultActions actions = mockMvc.perform(
                multipart(serviceUrl).file(file)
            )
            .andDo(print());

        //then
        actions
            .andExpect(status().isOk())
			.andExpect(content().string("success"))
			.andDo(this.document.document(
                )
            )
            ;
    }
	
}
