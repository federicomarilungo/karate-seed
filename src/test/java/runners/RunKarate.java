package runners;

import com.intuit.karate.KarateOptions;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.junit4.Karate;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(Karate.class) //Comentar esta linea para tener reportes de cucumber, descomentar para tener reportes de karate
@KarateOptions(features = "src/test/resources/feature", tags={"~@Ignore"})
public class RunKarate extends Run {
}