package com.axway.apim.test.serviceprofile;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.axway.apim.lib.AppException;
import com.axway.apim.test.ImportTestAction;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.dsl.testng.TestNGCitrusTestRunner;
import com.consol.citrus.functions.core.RandomNumberFunction;
import com.consol.citrus.message.MessageType;

@Test
public class ImportAndChangeBackendBasepathYamlTestIT extends TestNGCitrusTestRunner {

	private ImportTestAction swaggerImport;
	
	@CitrusTest
	@Test @Parameters("context")
	public void run(@Optional @CitrusResource TestContext context) throws IOException, AppException {
		swaggerImport = new ImportTestAction();
		description("Import API with Yaml specification and then afterwards set basepath to same host as declared in Yaml Swagger file");

		variable("apiNumber", RandomNumberFunction.getRandomNumber(3, true));
		variable("apiPath", "/basepath-yaml-test2-${apiNumber}");
		variable("apiName", "Basepath Yaml Test 2 ${apiNumber}");

		echo("####### Importing API: '${apiName}' on path: '${apiPath}' with following settings: #######");
		createVariable(ImportTestAction.API_DEFINITION,  "/com/axway/apim/test/files/security/petstore-single-scheme.yaml");
		createVariable(ImportTestAction.API_CONFIG,  "/com/axway/apim/test/files/serviceprofile/1_no_backend_basepath_test.json");
		createVariable("state", "unpublished");
		createVariable("expectedReturnCode", "0");
		swaggerImport.doExecute(context);
		
		echo("####### No-Change test for '${apiName}' on path: '${apiPath}' #######");
		createVariable(ImportTestAction.API_DEFINITION,  "/com/axway/apim/test/files/security/petstore-single-scheme.yaml");
		createVariable(ImportTestAction.API_CONFIG,  "/com/axway/apim/test/files/serviceprofile/2_backend_basepath_test.json");
		createVariable("backendBasepath", "https://yaml.petstore.swagger.io");
		createVariable("state", "unpublished");
		createVariable("expectedReturnCode", "10");
		swaggerImport.doExecute(context);

		echo("####### Validate API: '${apiName}' on path: '${apiPath}' has the given Base-Path configured. #######");
		http(builder -> builder.client("apiManager").send().get("/proxies").name("api").header("Content-Type", "application/json"));

		http(builder -> builder.client("apiManager").receive().response(HttpStatus.OK).messageType(MessageType.JSON)
				.validate("$.[?(@.path=='${apiPath}')].name", "${apiName}")
				.validate("$.[?(@.path=='${apiPath}')].state", "unpublished")
				.validate("$.[?(@.path=='${apiPath}')].serviceProfiles._default.basePath", "https://yaml.petstore.swagger.io")
				.extractFromPayload("$.[?(@.path=='${apiPath}')].id", "apiId"));
		
	}
}
