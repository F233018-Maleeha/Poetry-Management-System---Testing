import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeTags;

@Suite
@SelectPackages({"DataAccessLayer"})
@IncludeTags("integration")
public class IntegrationTestSuite {
    // Runs only integration tests
}