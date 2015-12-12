/*
 * Copyright 2015 Karl Dahlgren
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortmocks.web.mock.soap.web.mvc.controller.mockresponse;

import com.fortmocks.core.basis.model.ServiceProcessor;
import com.fortmocks.core.mock.soap.model.project.dto.SoapMockResponseDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapOperationDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapPortDto;
import com.fortmocks.core.mock.soap.model.project.dto.SoapProjectDto;
import com.fortmocks.core.mock.soap.model.project.service.message.input.ReadSoapMockResponseInput;
import com.fortmocks.core.mock.soap.model.project.service.message.input.UpdateSoapMockResponseInput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapMockResponseOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.ReadSoapMockResponsesOutput;
import com.fortmocks.core.mock.soap.model.project.service.message.output.UpdateSoapMockResponseOutput;
import com.fortmocks.web.basis.web.mvc.controller.AbstractController;
import com.fortmocks.web.mock.soap.config.TestApplication;
import com.fortmocks.web.mock.soap.model.project.SoapMockResponseDtoGenerator;
import com.fortmocks.web.mock.soap.model.project.SoapOperationDtoGenerator;
import com.fortmocks.web.mock.soap.model.project.SoapPortDtoGenerator;
import com.fortmocks.web.mock.soap.model.project.SoapProjectDtoGenerator;
import com.fortmocks.web.mock.soap.web.mvc.controller.AbstractSoapControllerTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


/**
 * @author: Karl Dahlgren
 * @since: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
public class SoapMockResponseControllerTest extends AbstractSoapControllerTest {

    private static final String PAGE = "partial/mock/soap/mockresponse/soapMockResponse.jsp";


    @InjectMocks
    private SoapMockResponseController soapMockResponseController;

    @Mock
    private ServiceProcessor serviceProcessor;

    @Override
    protected AbstractController getController() {
        return soapMockResponseController;
    }

    @Test
    public void testGetMockResponse() throws Exception {
        final SoapProjectDto projectDto = SoapProjectDtoGenerator.generateSoapProjectDto();
        final SoapPortDto applicationDto = SoapPortDtoGenerator.generateSoapPortDto();
        final SoapOperationDto soapOperationDto = SoapOperationDtoGenerator.generateSoapOperationDto();
        final SoapMockResponseDto soapMockResponseDto = SoapMockResponseDtoGenerator.generateSoapMockResponseDto();
        when(serviceProcessor.process(any(ReadSoapMockResponseInput.class))).thenReturn(new ReadSoapMockResponseOutput(soapMockResponseDto));
        final MockHttpServletRequestBuilder message = MockMvcRequestBuilders.get(SERVICE_URL + PROJECT + SLASH + projectDto.getId() + SLASH + PORT + SLASH + applicationDto.getId() + SLASH + OPERATION + SLASH + soapOperationDto.getId() + SLASH + RESPONSE + SLASH + soapMockResponseDto.getId());
        mockMvc.perform(message)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(8))
                .andExpect(MockMvcResultMatchers.forwardedUrl(INDEX))
                .andExpect(MockMvcResultMatchers.model().attribute(PARTIAL, PAGE))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PROJECT_ID, projectDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_PORT_ID, applicationDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_OPERATION_ID, soapOperationDto.getId()))
                .andExpect(MockMvcResultMatchers.model().attribute(SOAP_MOCK_RESPONSE, soapMockResponseDto));
    }

}
