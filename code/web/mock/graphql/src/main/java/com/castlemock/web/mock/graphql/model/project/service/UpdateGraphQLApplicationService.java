/*
 * Copyright 2018 Karl Dahlgren
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

package com.castlemock.web.mock.graphql.model.project.service;

import com.castlemock.core.basis.model.Service;
import com.castlemock.core.basis.model.ServiceResult;
import com.castlemock.core.basis.model.ServiceTask;
import com.castlemock.core.mock.graphql.model.project.dto.GraphQLApplicationDto;
import com.castlemock.core.mock.graphql.model.project.service.message.input.UpdateGraphQLApplicationInput;
import com.castlemock.core.mock.graphql.model.project.service.message.output.UpdateGraphQLApplicationOutput;
import com.castlemock.web.mock.graphql.model.project.repository.GraphQLApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Karl Dahlgren
 * @since 1.19
 */
@org.springframework.stereotype.Service
public class UpdateGraphQLApplicationService extends AbstractGraphQLProjectService implements Service<UpdateGraphQLApplicationInput, UpdateGraphQLApplicationOutput> {

    /**
     * The process message is responsible for processing an incoming serviceTask and generate
     * a response based on the incoming serviceTask input
     * @param serviceTask The serviceTask that will be processed by the service
     * @return A result based on the processed incoming serviceTask
     * @see ServiceTask
     * @see ServiceResult
     */
    @Override
    public ServiceResult<UpdateGraphQLApplicationOutput> process(final ServiceTask<UpdateGraphQLApplicationInput> serviceTask) {
        final UpdateGraphQLApplicationInput input = serviceTask.getInput();
        final GraphQLApplicationDto updatedGraphQLApplication = applicationRepository.save(input.getGraphQLApplication());
        return createServiceResult(new UpdateGraphQLApplicationOutput(updatedGraphQLApplication));
    }
}
