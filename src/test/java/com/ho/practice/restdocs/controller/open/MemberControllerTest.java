package com.ho.practice.restdocs.controller.open;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
//import static org.springframework.restdocs.snippet.Attributes.attributes;
//import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ho.practice.restdocs.DocumentFormatGenerator;
import com.ho.practice.restdocs.Member;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	private RestDocumentationResultHandler document;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
    private ObjectMapper objectMapper;
	
	@Autowired
	private WebApplicationContext context;

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
    public void testAddMember() throws Exception {
        //given
		Member member = new Member();
		member.setId("1");
		member.setName("A");
		member.setAge(11);
		
        //when
        ResultActions actions = mockMvc.perform(post("/open/member")
	                .contentType(MediaType.APPLICATION_JSON_UTF8)
	                .content(objectMapper.writeValueAsString(member))
                )
                .andDo(print());
        
        //then
        actions
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(this.document.document(
                		requestFields(
//                				attributes(key("title").value("회원 추가 API")),
                				fieldWithPath("id").description("회원 ID"),
                				fieldWithPath("name").description("회원 이름"),
                				fieldWithPath("age").description("회원 나이").attributes(DocumentFormatGenerator.getDateFormat())
                		)
//                		links(
//                				linkWithRel("id").description("회원 ID"),
//                				linkWithRel("name").description("회원 이름"),
//                				linkWithRel("age").description("회원 나이")
//                		)
                		)
                )
                ;
    }
	
	@Test
	public void testGetMember() throws Exception {
		//given
		
		//when
		final ResultActions actions = mockMvc.perform(get("/open/member/1")
														.header("remoteAddr1", "remote1")
														.header("remoteAddr2", "remote2")
														.header("remoteAddr3", "remote3")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andDo(print());
		
		//then
		actions
			.andExpect(status().isOk())
			.andExpect(content().json(objectMapper.writeValueAsString(new Member("1", "A", 11))))
			.andDo(this.document.document(
					requestHeaders(
							headerWithName("remoteAddr1").description("Remote Address 1"),
							headerWithName("remoteAddr2").description("Remote Address 2"),
							headerWithName("remoteAddr3").description("Remote Address 3")
					),
            		responseFields(
            				fieldWithPath("id").description("회원 ID"),
            				fieldWithPath("name").description("회원 이름"),
            				fieldWithPath("age").description("회원 나이")
            		)
					)
			)
		;
	}
	
	@Test
	public void testSearchMember() throws Exception {
		//given
		
		//when
		final ResultActions actions = mockMvc.perform(get("/open/member")
														.param("q", "1")
														.param("offset", "1")
														.param("limit", "10")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
				)
				.andDo(print());
		
		//then
		actions
			.andExpect(status().isOk())
			.andDo(this.document.document(
					requestParameters(
//							attributes(key("title").value("회원 조회 API")),
							parameterWithName("q").description("조회 쿼리").attributes(DocumentFormatGenerator.getQueryFormat()),
							parameterWithName("offset").description("조회 시작점"),
							parameterWithName("limit").description("조회 수")
					),
            		responseFields(
            				fieldWithPath("[].id").description("회원 ID"),
            				fieldWithPath("[].name").description("회원 이름"),
            				fieldWithPath("[].age").description("회원 나이")
            		)
					)
			)
		;
	}
	
}
