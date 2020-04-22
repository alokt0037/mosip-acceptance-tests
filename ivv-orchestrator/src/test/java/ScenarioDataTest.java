import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mosip.ivv.core.dtos.ParserInputDTO;
import io.mosip.ivv.core.dtos.Scenario;
import io.mosip.ivv.core.utils.Utils;
import io.mosip.ivv.dg.DataGenerator;
import io.mosip.ivv.parser.Parser;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

public class ScenarioDataTest {
    private Parser parser;

    @Before
    public void init() {
        String configFile = Paths.get(System.getProperty("user.dir"),"..", "ivv-orchestrator","config.properties").normalize().toString();
        Properties properties = Utils.getProperties(configFile);
        ParserInputDTO parserInputDTO = new ParserInputDTO();
        parserInputDTO.setDocumentsFolder(Paths.get(configFile, "..", properties.getProperty("ivv.path.documents.folder")).normalize().toString());
        parserInputDTO.setBiometricsFolder(Paths.get(configFile, "..", properties.getProperty("ivv.path.biometrics.folder")).normalize().toString());
        parserInputDTO.setPersonaSheet(Paths.get(configFile, "..", properties.getProperty("ivv.path.persona.sheet")).normalize().toString());
        parserInputDTO.setIdObjectSchema(Paths.get(configFile, "..", properties.getProperty("ivv.path.idobject")).normalize().toString());
        parserInputDTO.setDocumentsSheet(Paths.get(configFile, "..", properties.getProperty("ivv.path.documents.sheet")).normalize().toString());
        parserInputDTO.setBiometricsSheet(Paths.get(configFile, "..", properties.getProperty("ivv.path.biometrics.sheet")).normalize().toString());
        parserInputDTO.setGlobalsSheet(Paths.get(configFile, "..", properties.getProperty("ivv.path.globals.sheet")).normalize().toString());
        parserInputDTO.setConfigsSheet(Paths.get(configFile, "..", properties.getProperty("ivv.path.configs.sheet")).normalize().toString());
        parser = new Parser(parserInputDTO);
    }

    @Test
    public void scenarioData(){
        ArrayList<Scenario> scenariosToRun = parser.getScenarios();
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scenariosToRun);
            System.out.println(jsonInString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
